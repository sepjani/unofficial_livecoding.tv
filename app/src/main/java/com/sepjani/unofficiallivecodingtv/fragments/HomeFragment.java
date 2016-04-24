package com.sepjani.unofficiallivecodingtv.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sepjani.unofficiallivecodingtv.R;

/**
 * Created by Valeriy Strucovskiy on 4/24/2016.
 */
public class HomeFragment extends Fragment {

    public static final String URL = "https://www.livecoding.tv";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, null);
        WebView webView = (WebView) root.findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(URL);
        return root;
    }
}
