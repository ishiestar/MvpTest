package com.example.mvptest;

import android.content.SharedPreferences;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Login Screen
 */
public class MainActivity extends AppCompatActivity implements ResponseListener<Object> {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String SCHEME = "app";
    public static final String CALLBACK_URL = SCHEME + "://x-auth-twitter";

    RelativeLayout rootView;
    WebView webView;
    ProgressBar progressBar;

    OauthLoginProvider loginProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginProvider = new OauthLoginProvider(this);

        rootView = (RelativeLayout) findViewById(R.id.activity_main);
        webView = (WebView) findViewById(R.id.logged_in_webview);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        webView.setWebViewClient(new WebViewClient() {
            //if the URL has our app scheme, do something else rather than load the URL into this WV
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                if (uri.getScheme().equals(SCHEME)) {
                    String oauth_verifier = uri.getQueryParameter("oauth_verifier");
                    loginProvider.execute(oauth_verifier, false);
                    return true;
                }
                return false;
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
                handler.proceed(); //don't know... :/ TODO get explanation from PB
            }
        });
    }

    //when user clicks the app's login button, start loading the Twitter oauth URL into the webview
    public void login(View view) {
        progressBar.setVisibility(View.VISIBLE);
        loginProvider.execute(CALLBACK_URL, true);
    }

    @Override
    public void onResponse(Object data) {
        if (data instanceof String) {
            //this means that it's just a URL, load it into the WV
            Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show();
            webView.loadUrl(data.toString());
        } else {
            //we know this will be a json response, so store the details in shared prefs and open
            //the user profile. TODO change this if emit can return other types later on
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = preferences.edit();
            Log.v(LOG_TAG, data.toString());
            try {
                editor.putString(Constants.KEY_TOKEN, ((JSONObject) data).getString(Constants.KEY_TOKEN));
                editor.putString(Constants.KEY_SECRET, ((JSONObject) data).getString(Constants.KEY_SECRET));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            editor.apply();
            ProfileActivity.start(this);
        }
    }

    @Override
    public void onFailure(Throwable throwable) {
        //make a snackbar that asks the user to call the API again in case the earlier call fails
        Snackbar.make(rootView, throwable.getMessage(), Snackbar.LENGTH_LONG)
                .setAction(android.R.string.ok, v -> loginProvider.execute(CALLBACK_URL, true));
    }
}
