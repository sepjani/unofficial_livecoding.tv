package com.sepjani.unofficiallivecodingtv.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sepjani.unofficiallivecodingtv.R;
import com.sepjani.unofficiallivecodingtv.VideoOfflineScreenPresenter;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * Created by Valeriy Strucovskiy on 4/24/2016.
 */
public class VideosOfflineFragment extends RxFragment {

    public ListView listView;
    public SwipeRefreshLayout swipeLayout;

    public static Fragment newInstance(Context context) {
        VideosOfflineFragment f = new VideosOfflineFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_livestreams_list, null);
        swipeLayout = (SwipeRefreshLayout) root.findViewById(R.id.layout_home_swipe);
        listView = (ListView) root.findViewById(R.id.listview_home);
        VideoOfflineScreenPresenter presenter = new VideoOfflineScreenPresenter(this);
        swipeLayout.setOnRefreshListener(() -> presenter.updateValues());
        presenter.updateValues();
        return root;
    }
}

