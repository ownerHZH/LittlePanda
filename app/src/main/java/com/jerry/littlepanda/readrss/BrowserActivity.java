package com.jerry.littlepanda.readrss;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jerry.littlepanda.APPAplication;
import com.jerry.littlepanda.R;
import com.jerry.littlepanda.Utils.ADFilterTool;
import com.jerry.littlepanda.Utils.Constants;
import com.jerry.littlepanda.ireader.ui.base.BaseActivity;
import com.tencent.smtt.export.external.interfaces.ClientCertRequest;
import com.tencent.smtt.export.external.interfaces.HttpAuthHandler;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.CustomViewCallback;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class BrowserActivity extends BaseActivity {//
	/**
	 * 作为一个浏览器的示例展示出来，采用android+web的模式
	 */
	private X5WebView mWebView;
	private ViewGroup mViewParent;

	private static final String mHomeUrl = "www.baidu.com";
	private static final String TAG = "BrowserActivity";

	private ProgressBar mPageLoadingProgressBar = null;

	private URL mIntentUrl;
	private String mDesc,mSource;

	@Override
	protected int getContentId() {
		return R.layout.activity_web_view;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Log.e("BrowserActivity","onCreate=");
		/*set it to be no title*/
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*set it to be full screen*/
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setEnableSliding(true);//允许左滑退出
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);

		Intent intent = getIntent();
		if (intent != null) {
			try {
				mIntentUrl = new URL(intent.getStringExtra("url"));
				//Log.e("BrowserActivity","onCreate="+mIntentUrl.toString());
				mDesc = intent.getStringExtra("desc");
				//Log.e("BrowserActivity","onCreate="+mDesc);
				mSource=intent.getStringExtra("source");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
                e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//
		try {
			if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
				getWindow()
						.setFlags(
								android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
								android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
			}
		} catch (Exception e) {
		}


		//setContentView(R.layout.activity_web_view);

		ActionBar mActionBar=getSupportActionBar();
		mActionBar.setHomeButtonEnabled(true);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle(mSource);

		mViewParent = (ViewGroup) findViewById(R.id.webView1);

		initBtnListenser();

		//initContent();

		mTestHandler.sendEmptyMessageDelayed(MSG_INIT_UI, 1);

	}

	//解析网页内容
	private void initContent() {

		if (mPageLoadingProgressBar.getVisibility() == GONE)
			mPageLoadingProgressBar.setVisibility(VISIBLE);
		mPageLoadingProgressBar.setProgress(5);

		//String formatedHtml="<!DOCTYPE html><html><head><meta http-equiv=\"Content-type\"content=\"text/html; charset=utf-8\"><meta name=\"viewport\"content=\"user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, minimal-ui\"><meta name=\"apple-mobile-web-app-capable\"content=\"yes\"><meta name=\"apple-touch-fullscreen\"content=\"yes\"><meta name=\"apple-mobile-web-app-status-bar-style\"content=\"black\"><meta name=\"format-detection\"content=\"telephone=no\"><script src=\"//static.yidianzixun.com/lib/webmonitor/monitor.min.js\"></script><script>webmonitor.init(\"article_mobile\",{query1:\"\",query2:\"\",query3:\"\",query4:\"\",query5:\"0\",query6:\"news\",query7:\"488136546\"});webmonitor.cssStart();</script><link rel=\"stylesheet\"href=\"//static.yidianzixun.com/modules/build/mobile/article-bff5a09e37.all.css\"type=\"text/css\"><script>webmonitor.cssEnd();</script></head><body class=\"s-yidian style-0 page-news\"><div class=\"container\"><h3>{{title}}</h3><div class=\"meta js-meta\"><img src=\"http://si1.go2yd.com/get-image/067YaRrqrWS\"class=\"imedia-logo\"><div class=\"imedia-p\"><div class=\"source imedia\">{{source}}</div><div class=\"img imedia\"></div></div><div class=\"imedia-p\"><div class=\"date\">{{date}}</div></div></div><article id=\"js-article\"><div id=\"js-content\"class=\"content\">{{content}}</div></article></div></body></html>";
		String formatedHtml="<!DOCTYPE html><html><head><meta http-equiv=\"Content-type\"content=\"text/html; charset=utf-8\"><meta name=\"viewport\"content=\"user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width, minimal-ui\"><meta name=\"apple-mobile-web-app-capable\"content=\"yes\"><meta name=\"apple-touch-fullscreen\"content=\"yes\"><meta name=\"apple-mobile-web-app-status-bar-style\"content=\"black\"><meta name=\"format-detection\"content=\"telephone=no\"><script src=\"//static.yidianzixun.com/lib/webmonitor/monitor.min.js\"></script><script>webmonitor.init(\"article_mobile\",{query1:\"\",query2:\"\",query3:\"\",query4:\"\",query5:\"0\",query6:\"news\",query7:\"488136546\"});webmonitor.cssStart();</script><link rel=\"stylesheet\"href=\"https://static.yidianzixun.com/modules/build/mobile/article-bff5a09e37.all.css\"type=\"text/css\"><script>webmonitor.cssEnd();</script></head><body class=\"s-yidian style-0 page-news\"><div class=\"container\"><article id=\"js-article\"><div id=\"js-content\"class=\"content\">{{content}}</div></article></div></body></html>";
		//Log.e("BrowserActivity","url="+mIntentUrl);
		//Log.e("BrowserActivity","desc="+mDesc);
		if(null!=mIntentUrl&&mIntentUrl.toString().startsWith(Constants.YIDIANZIXUNBASEURL))
		{
			new AsyncTask<Void, Void, String>() {
				@Override
				protected String doInBackground(Void... voids) {
					String html = "";
					try {
						Document document = Jsoup.connect(mIntentUrl.toString()).timeout(5000).get();
						//Log.e("BrowserActivity",mIntentUrl.toString());
						//Log.e("BrowserActivity",document.toString());

						Elements elements=document.select("div.left-wrapper");

						Elements videoEle=elements.select("div.video-wrapper");
						if(null!=videoEle&&videoEle.toString()!="")
						{
							//是视频
							html="";
						}else
						{
							elements.select("div.float-right.share").remove();
							elements.select("div.interact").remove();
							elements.select("div.comments").remove();
							elements.select("p.yidian-wm-copyright-bottom").remove();
							//1. 获取 left-wrapper 2. remove div.float-right.share 3. remove div.interact div.comments

							//非视频
							//设置图片
							if(null!=elements&&elements.toString()!=""){
								//Log.e("BrowserActivity=",elements.toString()+"=");
								String str=elements.toString().replace("<img", "<img height=\"auto\"; width=\"100%\"");
								html = formatedHtml.replace("{{content}}",str);
							}
						}


					} catch (IOException e) {
						e.printStackTrace();
					}

					return html;
				}

				@Override
				protected void onPostExecute(String html) {
					if(null!=html&&html!="")
					{
						String mime = "text/html; charset=utf-8";
						String encoding = "utf-8";

						mWebView.loadData(html, mime, encoding);
					}else
					{
						mWebView.loadUrl(mIntentUrl.toString());
					}
				}
			}.execute();

		}else if(null!=mIntentUrl&&mIntentUrl.toString().equals("http://www.youji.com/")){
			String mime = "text/html; charset=utf-8";
			String encoding = "utf-8";
			String html = formatedHtml.replace("{{content}}",mDesc);
			mWebView.loadData(html, mime, encoding);
		}else
		{
			Log.e("BrowserActivity","else");
			if(null!=mIntentUrl){
				mWebView.loadUrl(mIntentUrl.toString());
			}else{
				mWebView.loadUrl("www.baidu.com");
			}

		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
//                finish();
				onBackPressed();
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void initProgressBar() {
		mPageLoadingProgressBar = (ProgressBar) findViewById(R.id.progressBar1);// new
																				// ProgressBar(getApplicationContext(),
																				// null// android.R.attr.progressBarStyleHorizontal);
		mPageLoadingProgressBar.setMax(100);
		mPageLoadingProgressBar.setProgressDrawable(this.getResources()
				.getDrawable(R.drawable.color_progressbar));
		/*
		back=(ImageView)findViewById(R.id.ticket_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				//finish();
				onBackPressed();
			}
		});
		*/
	}

	private void init() {

		mWebView = new X5WebView(APPAplication.getContext(), null);

		mViewParent.addView(mWebView, new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.FILL_PARENT));

		initProgressBar();

		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}

			@Override
			public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
				super.onPageStarted(webView, s, bitmap);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}
		});

		mWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
					JsResult arg3) {
				return super.onJsConfirm(arg0, arg1, arg2, arg3);
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					mPageLoadingProgressBar.setVisibility(GONE);
				} else {
					if (mPageLoadingProgressBar.getVisibility() == GONE)
						mPageLoadingProgressBar.setVisibility(VISIBLE);
					mPageLoadingProgressBar.setProgress(newProgress);
				}
				super.onProgressChanged(view, newProgress);
			}

			/*
			View myVideoView;
			View myNormalView;
			CustomViewCallback callback;
			*/

			// /////////////////////////////////////////////////////////
			//
			/**
			 * 全屏播放配置
			 */
			@Override
			public void onShowCustomView(View view,
					CustomViewCallback customViewCallback) {
				/*
				FrameLayout normalView = (FrameLayout) findViewById(R.id.web_filechooser);
				ViewGroup viewGroup = (ViewGroup) normalView.getParent();
				viewGroup.removeView(normalView);
				viewGroup.addView(view);
				myVideoView = view;
				myNormalView = normalView;
				callback = customViewCallback;
				*/
			}

			@Override
			public void onHideCustomView() {
				/*
				if (callback != null) {
					callback.onCustomViewHidden();
					callback = null;
				}
				if (myVideoView != null) {
					ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
					viewGroup.removeView(myVideoView);
					viewGroup.addView(myNormalView);
				}*/
			}

			@Override
			public boolean onJsAlert(WebView arg0, String arg1, String arg2,
					JsResult arg3) {
				/**
				 * 这里写入你自定义的window alert
				 */
				return super.onJsAlert(null, arg1, arg2, arg3);
			}
		});

		mWebView.setDownloadListener(new DownloadListener() {

			@Override
			public void onDownloadStart(String arg0, String arg1, String arg2,
					String arg3, long arg4) {
				TbsLog.d(TAG, "url: " + arg0);
				new AlertDialog.Builder(BrowserActivity.this)
						.setTitle("allow to download？")
						.setPositiveButton("yes",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Toast.makeText(
												BrowserActivity.this,
												"fake message: i'll download...",
												Toast.LENGTH_SHORT).show();
									}
								})
						.setNegativeButton("no",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										Toast.makeText(
												BrowserActivity.this,
												"fake message: refuse download...",
												Toast.LENGTH_SHORT).show();
									}
								})
						.setOnCancelListener(
								new DialogInterface.OnCancelListener() {

									@Override
									public void onCancel(DialogInterface dialog) {
										// TODO Auto-generated method stub
										Toast.makeText(
												BrowserActivity.this,
												"fake message: refuse download...",
												Toast.LENGTH_SHORT).show();
									}
								}).show();
			}
		});

		WebSettings webSetting = mWebView.getSettings();

		//mWebView.removeJavascriptInterface("searchBoxJavaBridge_");

		//getIntent().putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
		//mWebView.addJavascriptInterface(new SonicJavaScriptInterface(sonicSessionClient, intent), "sonic");

		webSetting.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webSetting.setSupportZoom(true);
		webSetting.setBuiltInZoomControls(true);
		webSetting.setUseWideViewPort(true);
		webSetting.setSupportMultipleWindows(false);
		webSetting.setAllowFileAccess(true);//资源加载超时操作

		webSetting.setAppCacheEnabled(true);
		webSetting.setLoadWithOverviewMode(true);
		// webSetting.setDatabaseEnabled(true);
		webSetting.setDomStorageEnabled(true);
		webSetting.setJavaScriptEnabled(true);
		webSetting.setGeolocationEnabled(true);
		webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
		webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
		webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
		webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
				.getPath());
		// webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
		webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
		// webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
		// webSetting.setPreFectch(true);
		long time = System.currentTimeMillis();


		/*
		if (null!=mDesc && mDesc.contains(Constants.APIHOST)) {
			//mWebView.loadUrl(mHomeUrl);
			mWebView.loadUrl(mIntentUrl.toString());
			//mWebView.loadData(mDesc, "text/html; charset=UTF-8", null);
		} else {
			mWebView.loadUrl(mIntentUrl.toString());
		}*/
		initContent();

		TbsLog.d("time-cost", "cost time: "
				+ (System.currentTimeMillis() - time));
		CookieSyncManager.createInstance(this);
		CookieSyncManager.getInstance().sync();
	}

	private void initBtnListenser() {


	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	}

	@Override
	protected void onNewIntent(Intent intent) {
		if (intent == null || mWebView == null || intent.getData() == null)
			return;
		Log.e("BrowserActivity","onNewIntent="+intent.getData().toString());
		mWebView.loadUrl(intent.getData().toString());
	}

	@Override
	protected void onDestroy() {

		if(mWebView != null) {
			mWebView.getSettings().setBuiltInZoomControls(true);
			mWebView.removeAllViews();
			mWebView.setVisibility(GONE);
			long timeout = ViewConfiguration.getZoomControlsTimeout();//timeout ==3000
			mTestHandler.sendEmptyMessageAtTime(MSG_DESTROY_UI,timeout);
		}
		super.onDestroy();

		System.gc();
	}

	public static final int MSG_INIT_UI = 1;
	public static final int MSG_DESTROY_UI = 2;
	private Handler mTestHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_INIT_UI:
				init();
				break;
			case MSG_DESTROY_UI:
				BrowserActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(null!=mWebView)
						{
							mWebView.destroy();
						}
						if (mTestHandler != null)
							mTestHandler.removeCallbacksAndMessages(null);
					}
				});
				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onPause() {
		if(null!=mWebView)
		{
			mWebView.onPause();
		}
		super.onPause();
	}

	@Override
	protected void onStart() {
		super.onStart();
		MiStatInterface.recordPageStart(BrowserActivity.this, "BrowserActivity");
	}

	@Override
	protected void onResume() {
		super.onResume();
		MiStatInterface.recordPageEnd();
	}


	@Override
	public void onBackPressed() {
		finish();
		//super.onBackPressed();
	}
}
