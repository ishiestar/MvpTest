package com.example.mvptest;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ResponseListener<String> {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    WebView webView;
    ProgressBar progressBar;

    OauthLoginProvider loginProvider;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginProvider = new OauthLoginProvider(getSupportFragmentManager(), this);

        webView = (WebView) findViewById(R.id.logged_in_webview);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(LOG_TAG, "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                Log.i(LOG_TAG, "Finished loading URL: " + url);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(LOG_TAG, "Error: " + description);
                Toast.makeText(MainActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
    }

    public void login(View view) {
        progressBar.setVisibility(View.VISIBLE);
        loginProvider.execute("abcd");
        Log.d(LOG_TAG, "loginProvider.execute");
    }

    @Override
    public void onResponse(String data) {
        //TODO load url on webview
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        url = data;
        webView.loadUrl(data);
    }

    @Override
    public void onFailure(Throwable throwable) {
        Snackbar.make(findViewById(R.id.activity_main), throwable.getMessage(), Snackbar.LENGTH_LONG)
                .setAction(android.R.string.ok, v -> loginProvider.execute("abcd"));
    }
}
