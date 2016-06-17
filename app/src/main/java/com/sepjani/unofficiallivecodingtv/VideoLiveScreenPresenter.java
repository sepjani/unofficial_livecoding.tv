package com.sepjani.unofficiallivecodingtv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sepjani.unofficiallivecodingtv.api.RestAPIClient;
import com.sepjani.unofficiallivecodingtv.api.models.VideoOnAirModel;
import com.sepjani.unofficiallivecodingtv.api.models.VideoOnAirSummaryModel;
import com.sepjani.unofficiallivecodingtv.fragments.VideosLiveFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Valeriy Strucovskiy on 4/24/2016.
 */
public class VideoLiveScreenPresenter {
    private VideosLiveFragment fragment;
    private ListAdapter adapter;
    private List<VideoOnAirModel> objects = new ArrayList<>();

    public VideoLiveScreenPresenter(VideosLiveFragment fragment) {
        this.fragment = fragment;
        adapter = new ListAdapter(fragment.getContext(), objects);
        fragment.listView.setAdapter(adapter);
        AppLoadingIndicator.show(fragment.getContext());
        updateValues();
    }

    public void updateValues() {


        new RestAPIClient()
                .getAPI()
                .getVideosOnAir()
                .enqueue(new Callback<VideoOnAirSummaryModel>() {
                    @Override
                    public void onResponse(Call<VideoOnAirSummaryModel> call, Response<VideoOnAirSummaryModel> response) {
                        objects.clear();
                        Collections.sort(response.body().results);
                        objects.addAll(response.body().results);
                        adapter.notifyDataSetInvalidated();
                        fragment.swipeLayout.setRefreshing(false);
                        AppLoadingIndicator.hide();
                    }

                    @Override
                    public void onFailure(Call<VideoOnAirSummaryModel> call, Throwable t) {
                        t.printStackTrace();
                        AppLoadingIndicator.hide();
                    }
                });
    }

    public class ListAdapter extends ArrayAdapter<VideoOnAirModel> {
        public ListAdapter(Context context, List<VideoOnAirModel> objects) {
            super(context, R.layout.item_video, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            VideoOnAirModel videoModel = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_video, null, false);
                viewHolder.name = (TextView) convertView.findViewById(R.id.tv_item_title);
                viewHolder.viewers = (TextView) convertView.findViewById(R.id.tv_viewer_num);
                viewHolder.tag = (TextView) convertView.findViewById(R.id.tv_tag);
                viewHolder.difficulty = (TextView) convertView.findViewById(R.id.tv_difficulty);
                viewHolder.preview = (ImageView) convertView.findViewById(R.id.iv_video_preview);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.name.setText(videoModel.title);
            viewHolder.difficulty.setText(videoModel.difficulty);
            viewHolder.viewers.setText(videoModel.viewersLive + "");
            viewHolder.tag.setText(videoModel.codingCategory);

            Picasso.with(fragment.getContext())
                    .load(getVideoURL(videoModel.userSlug))
                    .placeholder(R.drawable.ic_video)

                    .tag(fragment.getContext()) //
//                    .fit()
//                      .resize(0, viewHolder.preview.getWidth())
                    .into(viewHolder.preview);


            return convertView;
        }

        // View lookup cache
        private class ViewHolder {
            TextView name;
            TextView viewers;
            TextView difficulty;
            TextView tag;
            ImageView preview;
        }
    }

    public String getVideoURL(String nickname) {
        String url = "https://livecoding.tv/video/livestream/" + nickname + "/thumbnail_250_140/";
        System.out.println(url);
        return url;
    }
}
