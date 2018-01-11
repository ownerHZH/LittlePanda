package com.jerry.littlepanda.mytool;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jerry.littlepanda.APPAplication;
import com.jerry.littlepanda.R;
import com.jerry.littlepanda.Utils.Constants;
import com.jerry.littlepanda.Utils.DeviceTool;
import com.jerry.littlepanda.ireader.ui.base.BaseActivity;
import com.jerry.littlepanda.readrss.BrowserActivity;
import com.jerry.littlepanda.readrss.X5WebView;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;
import com.xiaomi.mistatistic.sdk.MiStatInterface;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class JsonActivity extends BaseActivity {

    private EditText editText;
    private Button validBtn,copyBtn;
    private TextView notiTextView;

    @Override
    protected int getContentId() {
        return R.layout.activity_json;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setEnableSliding(true);//允许左滑退出
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        try {
            if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
                getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }
        ActionBar mActionBar=getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("JSON");
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        editText=(EditText) findViewById(R.id.editText);
        validBtn=(Button) findViewById(R.id.validbutton);
        copyBtn=(Button) findViewById(R.id.copybutton);
        notiTextView=(TextView) findViewById(R.id.noti);
        copyBtn.setClickable(false);
        copyBtn.setEnabled(false);
    }

    @Override
    protected void initClick() {
        super.initClick();
        validBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formatJsonString();
            }
        });

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipBoard();
                //cm.setText(orderDetailsTvOrderNumber.getText().toString());
                //Toast.makeText((Activity)context,"已复制到剪切板，快去粘贴吧~",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //格式化json数据操作
    private void formatJsonString() {
        String tempStr=editText.getText().toString().trim();
        if(null!=tempStr&&!tempStr.equals(""))
        {
            if(DeviceTool.getDeviceToolInstance().isGoodJson(tempStr))
            {
                notiTextView.setText("");
                String formatedJson=DeviceTool.getDeviceToolInstance().stringToJSON(tempStr);
                if(null!=formatedJson&&!formatedJson.equals(""))
                {
                    editText.setText(formatedJson);
                    copyBtn.setClickable(true);
                    copyBtn.setEnabled(true);
                }
            }else
            {
                notiTextView.setText("Json格式不正确！！！");
            }
        }
    }

    //复制操作
    private void copyToClipBoard() {
        ClipboardManager cm =(ClipboardManager)JsonActivity.this.getSystemService(JsonActivity.this.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Desc", editText.getText());
        cm.setPrimaryClip(mClipData);
        notiTextView.setText("已复制到剪切板，快去粘贴吧~");
    }

    @Override
    protected void processLogic() {
        super.processLogic();
    }

    /**
     * 跳转到这个Activity
     * @param mActivity
     */
    public static void jumpToJsonActivity(Activity mActivity)
    {
        Intent intent = new Intent(mActivity, JsonActivity.class);
        //intent.setData(Uri.parse(item.getUrl()==null?"":item.getUrl()));
        //intent.putExtra("url", item.getLink());
        //intent.putExtra("desc", item.getDescription());
        //intent.putExtra("source", item.getSource());
        mActivity.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MiStatInterface.recordPageStart(JsonActivity.this, "JsonActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        MiStatInterface.recordPageEnd();
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
