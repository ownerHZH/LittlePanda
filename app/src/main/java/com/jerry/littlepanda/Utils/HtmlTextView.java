package com.jerry.littlepanda.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;

import com.jerry.littlepanda.R;

import java.io.InputStream;

/**
 * Created by jerry.hu on 18/07/17.
 */

public class HtmlTextView extends TextView {
    public static final String TAG = "HtmlTextView";
    public static final boolean DEBUG = false;

    public HtmlTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public HtmlTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HtmlTextView(Context context) {
        super(context);
    }
    /**
     * @param is
     * @return
     */
    static private String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");

        return s.hasNext() ? s.next() : "";
    }

    /**
     * Parses String containing HTML to Android's Spannable format and displays
     * it in this TextView.
     *
     * @param html String containing HTML, for example: "<b>Hello world!</b>"
     */
    public void setHtmlFromString(String html, boolean useLocalDrawables) {
        Html.ImageGetter imgGetter;

        if(useLocalDrawables)
        {
            Drawable defaultDrawable = getResources().getDrawable(R.drawable.pandalogo);
            imgGetter = new HtmlImageGetter(this, "/esun_msg", defaultDrawable);
        }else
        {
            imgGetter=new UrlImageGetter(this,getContext());
        }



        // this uses Android's Html class for basic parsing, and HtmlTagHandler
        //setText(Html.fromHtml(html, imgGetter, null));
        if(html!=null)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setText(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT,imgGetter, null));
            }else {
                setText(Html.fromHtml(html, imgGetter, null));
            }
        }
        // make links work
        //setMovementMethod(LinkMovementMethod.getInstance());
        setTextColor(getResources().getColor(android.R.color.secondary_text_dark_nodisable));
    }

}
