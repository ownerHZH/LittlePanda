package com.jerry.littlepanda;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Process;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;

import com.jerry.littlepanda.Utils.Constants;
import com.jerry.littlepanda.crash.CrashHandler;
import com.jerry.littlepanda.ireader.service.DownloadService;
import com.jerry.littlepanda.location.LocationService;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.smtt.sdk.QbSdk;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.xiaomi.mistatistic.sdk.MiStatInterface;
import com.xiaomi.mistatistic.sdk.URLStatsRecorder;
import com.xiaomi.mistatistic.sdk.controller.HttpEventFilter;
import com.xiaomi.mistatistic.sdk.data.HttpEvent;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class APPAplication extends Application {


	//Volley的全局请求队列
	//public static RequestQueue sRequestQueue;

	private static Context sInstance;
	private static DisplayImageOptions options;
	public LocationService locationService;
	/**
	 * @return Volley全局请求队列

	public static RequestQueue getRequestQueue() {
		return sRequestQueue;
	}*/

	//线程池
	//public static final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		sInstance = this;
		//搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
		
		QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
			
			@Override
			public void onViewInitFinished(boolean arg0) {
				// TODO Auto-generated method stub
				//x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
				//Log.d("app", " onViewInitFinished is " + arg0);
			}
			
			@Override
			public void onCoreInitFinished() {
				// TODO Auto-generated method stub
			}
		};
		//x5内核初始化接口
		QbSdk.initX5Environment(getApplicationContext(),  cb);

		//ImageLoader初始化
		int maxMemorySize = (int) (Runtime.getRuntime().maxMemory());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(maxMemorySize / 10)// 内存缓存大小默认是：app可用内存的1/8,这里设为1/10
				.build();
		ImageLoader.getInstance().init(config);

		//显示图片的配置
		options = new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.showImageOnLoading(R.drawable.loading_wait) //设置图片在下载期间显示的图片
				.showImageForEmptyUri(R.drawable.loading_wait)//设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.loading_wait)  //设置图片加载/解码过程中错误时候显示的图片
				.resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
				.build();

		//实例化Volley全局请求队列
		//sRequestQueue = Volley.newRequestQueue(getApplicationContext());

		// 注册push服务，注册成功后会向DemoMessageReceiver发送广播
		// 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
		if (true) {
			MiStatInterface.initialize(this, Constants.XIAOMI_PUSH_APP_ID, Constants.XIAOMI_PUSH_APP_KEY, "default channel");
			MiPushClient.registerPush(this, Constants.XIAOMI_PUSH_APP_ID, Constants.XIAOMI_PUSH_APP_KEY);
		}

		MiStatInterface.setUploadPolicy(
				MiStatInterface.UPLOAD_POLICY_WHILE_INITIALIZE, 0);
		//MiStatInterface.triggerUploadManually();
		MiStatInterface.enableLog();
		// enable exception catcher.
		MiStatInterface.enableExceptionCatcher(true);
		// enable network monitor
		URLStatsRecorder.enableAutoRecord();
		URLStatsRecorder.setEventFilter(new HttpEventFilter() {

			@Override
			public HttpEvent onEvent(HttpEvent event) {
				/*
				try {
					Log.d("MI_STAT", event.getUrl() + " result =" + event.toJSON());
				} catch (JSONException e) {
					e.printStackTrace();
				}*/
				// returns null if you want to drop this event.
				// you can modify it here too.
				return event;
			}
		});

		startService(new Intent(getContext(), DownloadService.class));

		//Log.d("MI_STAT", MiStatInterface.getDeviceID(this) + " is the device.");
		//initCrashHandler();//初始化崩溃收集 需要重写收集的文件上传
		if (LeakCanary.isInAnalyzerProcess(this)) {
			return;
		}
		LeakCanary.install(this);

		locationService = new LocationService(getApplicationContext());
		//mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
		//SDKInitializer.initialize(getApplicationContext());

	}

	public static Context getContext(){
		return sInstance;
	}

	private boolean shouldInit() {
		ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
		List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		String mainProcessName = getPackageName();
		int myPid = Process.myPid();
		for (ActivityManager.RunningAppProcessInfo info : processInfos) {
			if (info.pid == myPid && mainProcessName.equals(info.processName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 初始化程序崩溃捕捉处理
	 */
	protected void initCrashHandler() {
		if(!Constants.isDebug)
		{
			CrashHandler handler = CrashHandler.getInstance();
			handler.init(getApplicationContext());
			Thread.setDefaultUncaughtExceptionHandler(handler);
		}
	}

	public static void setImageValue(ImageView img, String value) {
		//处理下拉图片错位
		img.setTag(value);
		if(value.startsWith("data:image/png;base64,")){
			value=value.replaceFirst("data:image/png;base64,","");
			byte[] decodedString = android.util.Base64.decode(value, android.util.Base64.DEFAULT);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 8;
			options.inPreferredConfig = Bitmap.Config.RGB_565;
			options.inPurgeable = true;
			options.inInputShareable = true;
			Bitmap bitMap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length,options);
			img.setImageBitmap(bitMap);
		} else{
			if(value.startsWith("//"))
			{
				value=value.replaceFirst("//","http://");
			}
			ImageAware imageAware = new ImageViewAware(img, false);
			ImageLoader.getInstance().displayImage(value,imageAware,options,new SimpleImageLoadingListener(){
				@Override
				public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
					if (!imageUri.equals(view.getTag().toString())) {
						// 设置默认图片
						((ImageView)view).setImageResource(R.drawable.loading_wait);
					}else
					{
						((ImageView)view).setImageBitmap(loadedImage);
					}
				}
			});
		}

	}

	public static void showShare(final Activity activity) {

	}

	/** 显示分享*/
	public static void showShare(final Activity activity, final String shareUrl) {
	}

	/** 检查更新*/
	public static void checkUpdate(Activity activity) {

	}

	/** 首个activity启动调用*/
	public static void activityStartMain(Activity activity) {

	}

	/** 每个activity生命周期里的onCreate*/
	public static void activityCreateStatistics(Activity activity) {

	}

	/** 每个activity生命周期里的onResume*/
	public static void activityResumeStatistics(Activity activity) {

	}

	/** 每个activity生命周期里的onPause*/
	public static void activityPauseStatistics(Activity activity) {

	}

	/** 事件统计*/
	public static void eventStatistics(Context context, String event) {

	}

	/** 事件统计*/
	public static void eventStatistics(Context context, String event, String tag) {

	}

}
