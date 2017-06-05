package com.hkm.ezwebview.webviewclients;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hkm.ezwebview.webviewleakfix.PreventLeakClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hesk on 3/5/15.
 * So this Client will actually load the content directly from the URL
 */
public abstract class HBCart extends WebViewClient {
    private String loginCookie;
    private final Context mContext;
    private boolean redirect = false;
    private boolean loadingFinished = true;
    private final WebView mWebView;
    public static String TAG = "hbcartclient";

    private final ArrayList<String> allowing = new ArrayList<>();
    private final ArrayList<String> startfrom = new ArrayList<>();

    public HBCart(final Activity context, final WebView fmWebView) {
        super();
        mContext = context;
        mWebView = fmWebView;
        WebSettings settings = fmWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        fmWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        settings.setSaveFormData(true);
    }

    public void defineBoundaries(
            final List<String> allow_browsing,
            final List<String> start_from_intent) {
        this.allowing.clear();
        this.allowing.addAll(allow_browsing);
        this.startfrom.clear();
        this.startfrom.addAll(start_from_intent);
    }

    protected abstract void triggerNative(final Uri trigger_url);

    protected abstract boolean interceptUrl(WebView view, String url);

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        //    Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
        //  Tool.trace();
        Log.d(TAG, "errorCode: " + errorCode);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
        if (!loadingFinished) {
            redirect = true;
        }
        boolean h = interceptUrl(view, urlNewString);
        if (!h) {
            loadingFinished = false;
            //view.loadUrl(urlNewString);
        }
        return h;
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        loadingFinished = false;
        //SHOW LOADING IF IT ISNT ALREADY VISIBLE
        super.onPageStarted(view, url, favicon);
        //  CustomLogger.showLog("Beta", "onPage Started url is" + url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (!redirect) {
            loadingFinished = true;
        }
        if (loadingFinished && !redirect) {
            //HIDE LOADING IT HAS FINISHED
        } else {
            redirect = false;
        }
        super.onPageFinished(view, url);

    }

}
