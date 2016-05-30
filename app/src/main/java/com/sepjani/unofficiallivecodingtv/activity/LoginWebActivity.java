package com.sepjani.unofficiallivecodingtv.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sepjani.unofficiallivecodingtv.LivecodingApplication;
import com.sepjani.unofficiallivecodingtv.PreferenceFields;
import com.sepjani.unofficiallivecodingtv.R;
import com.sepjani.unofficiallivecodingtv.busevents.HelloEvent;

import org.greenrobot.eventbus.EventBus;

public class LoginWebActivity extends AppCompatActivity {

    private String clientID = "T2RIQHIYvz9Hy9K3vMoQTSE2koipEVF8WWuSrLsx";
    private String clientSecret = "kiKep8ikmPcq94prX2vySltUcMkOwEnkIMYzJIXg82LsXYz3VgnKmPBFJuObV7ZscBCn3zZuPyoipOwKsKplgoo9jkhVgqIoHjSd3uSmCzGar8pqNLwYnQGuzBQ7Ett8";

    public static final String URL = "https://www.livecoding.tv/o/authorize/" +
            "?scope=read+read:viewer+read:user+read:channel+chat&state=46c3c39d-512a-4bd0-8d59-63d7c9732122" +
            "&response_type=token" +
            "&client_id=T2RIQHIYvz9Hy9K3vMoQTSE2koipEVF8WWuSrLsx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_web);
        getSupportActionBar().hide();
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.contains("access_token")) {

                    String[] params = url.split("#")[1].split("&");
                    String token = params[0];
                    String type = params[1];
                    long expiresin = Long.parseLong(params[3].split("=")[1]);
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LivecodingApplication.getAppContext());
                    prefs.edit()
                            .putString(PreferenceFields.API_TOKEN, token.split("=")[1])
                            .putString(PreferenceFields.API_TOKEN_TYPE, type.split("=")[1])
                            .putLong(PreferenceFields.API_EXPIRE, System.currentTimeMillis() + expiresin)
                            .commit();
                    LoginWebActivity.this.finish();
                    EventBus.getDefault().post(new HelloEvent("RIGHT!"));
                }
                return false;
            }
        });
        webView.loadUrl(URL);
    }


}
