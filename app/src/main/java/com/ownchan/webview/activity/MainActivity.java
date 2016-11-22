package com.ownchan.webview.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.ownchan.webview.R;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnTouchListener {

    private String url = "http://news.sina.com.cn/china/xlxw/2016-10-23/doc-ifxwztru6946123.shtml";

    private WebView webview;
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webview = (WebView) findViewById(R.id.web_view);

        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webview.loadUrl(url);//登陆一个有图片的URL
        webview.getSettings().setJavaScriptEnabled(true); //支持JS
        webview.addJavascriptInterface(new JsInterface(this), "imageClick"); //JS交互
        webview.setOnTouchListener(this);
    }


    class JsInterface{
        Context context;
        public JsInterface(Context context){
            this.context = context;
        }

        //查看图片url
        @JavascriptInterface
        public void click(String url){
            Intent intent = new Intent();
            intent.putExtra("IMG_URL", url);
            intent.setClass(MainActivity.this, ImageShowActivity.class);
            startActivity(intent);
        }
    }

    float x,y;


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //通过wenview的touch来响应web上的图片点击事件
        float density = getResources().getDisplayMetrics().density; //屏幕密度
        float touchX = event.getX() / density;  //必须除以屏幕密度
        float touchY = event.getY() / density;
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            x = touchX;
            y = touchY;
        }

        if(event.getAction() == MotionEvent.ACTION_UP){
            float dx = Math.abs(touchX-x);
            float dy = Math.abs(touchY-y);
            if(dx<10.0/density&&dy<10.0/density){
                clickImage(touchX,touchY);
            }
        }
        return false;
    }

    private void clickImage(float touchX, float touchY) {
        //通过触控的位置来获取图片URL
        String js = "javascript:(function(){" +
                "var  obj=document.elementFromPoint("+touchX+","+touchY+");"
                +"if(obj.src!=null){"+ " window.imageClick.click(obj.src);}" +
                "})()";
        webview.loadUrl(js);
    }
}
