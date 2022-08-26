package com.example.web;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    LinearLayout sucess,nointernet;
    WebView webView;
    ProgressBar progressBar;
    Button Reload;
    SwipeRefreshLayout swipeRefreshLayout;
    String USER_AGENT_ = "Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout=findViewById(R.id.reload);
        webView=findViewById(R.id.web);
        progressBar=findViewById(R.id.progress);
        sucess=findViewById(R.id.main);
        Reload=findViewById(R.id.reloa);
        nointernet=findViewById(R.id.nointer);
        webView.getSettings().setUserAgentString(USER_AGENT_);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setAllowFileAccess(true);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://taja-jinis.web.app/home?fbclid=IwAR0ldJ0YiorXL_ws6beMlSI0fB432eBl9SbfOjkdG4RxNACZ5IjbZyh0aKQ");
     /*   Reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chacknetwork();
            }
        });*/
        webView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(webView.getScrollY()==0){
                    swipeRefreshLayout.setEnabled(true);
                }
                else{
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webView.reload();
            }
        });
        webView.setWebViewClient(new WebViewClient(){
 
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                sucess.setVisibility(View.GONE);
                nointernet.setVisibility(View.VISIBLE);
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                progressBar.setVisibility(View.VISIBLE);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) webView.goBack();
        else finish();
    }
   /* private void  chacknetwork()
    {
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null)
        {
            if(networkInfo.isConnected())
            {
                sucess.setVisibility(View.VISIBLE);
                nointernet.setVisibility(View.GONE);
                webView.reload();

            }
            else{
                sucess.setVisibility(View.GONE);
                nointernet.setVisibility(View.VISIBLE);

            }


        }
        else {
            sucess.setVisibility(View.GONE);
            nointernet.setVisibility(View.VISIBLE);



        }




    }*/
}