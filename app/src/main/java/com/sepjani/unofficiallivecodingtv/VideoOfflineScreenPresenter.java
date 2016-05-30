package com.sepjani.unofficiallivecodingtv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sepjani.unofficiallivecodingtv.api.RestAPIClient;
import com.sepjani.unofficiallivecodingtv.api.models.LiveVideoModel;
import com.sepjani.unofficiallivecodingtv.api.models.LiveVideoResult;
import com.sepjani.unofficiallivecodingtv.fragments.VideosOfflineFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Valeriy Strucovskiy on 4/24/2016.
 */
public class VideoOfflineScreenPresenter {
    private VideosOfflineFragment fragment;
    private ListAdapter adapter;
    private List<LiveVideoModel> objects = new ArrayList<>();

    public VideoOfflineScreenPresenter(VideosOfflineFragment fragment) {
        this.fragment = fragment;
        adapter = new ListAdapter(fragment.getContext(), objects);
        fragment.listView.setAdapter(adapter);

    }

    public void updateValues() {

        new RestAPIClient()
                .getAPI()
                .getLastVideos()

                .enqueue(new Callback<LiveVideoResult>() {
            @Override
            public void onResponse(Call<LiveVideoResult> call, Response<LiveVideoResult> response) {
                objects.clear();
                objects.addAll(response.body().videos);
                adapter.notifyDataSetInvalidated();
                fragment.swipeLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<LiveVideoResult> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public class ListAdapter extends ArrayAdapter<LiveVideoModel> {
        public ListAdapter(Context context, List<LiveVideoModel> objects) {
            super(context, R.layout.item_video, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            LiveVideoModel videoModel = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_video, null, false);
                viewHolder.name = (TextView) convertView.findViewById(R.id.tv_item_title);
                viewHolder.home = (TextView) convertView.findViewById(R.id.tv_item_description);
                viewHolder.preview = (ImageView) convertView.findViewById(R.id.iv_video_preview);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.name.setText(videoModel.title);
            viewHolder.home.setText(videoModel.description);

            Picasso.with(fragment.getContext())
                    .load(getVideoURL(videoModel.userSlug))
//                    .fit()
//                      .resize(0, viewHolder.preview.getWidth())
                    .into(viewHolder.preview);


            return convertView;
        }

        // View lookup cache
        private class ViewHolder {
            TextView name;
            TextView home;
            ImageView preview;
        }
    }

    public String getVideoURL(String nickname) {
        String url = "https://livecoding.tv/video/livestream/" + nickname + "/thumbnail_250_140/";
        System.out.println(url);
        return url;
    }
}
