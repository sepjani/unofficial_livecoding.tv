package com.sepjani.unofficiallivecodingtv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sepjani.unofficiallivecodingtv.api.RestAPIClient;
import com.sepjani.unofficiallivecodingtv.api.models.ScheduleModel;
import com.sepjani.unofficiallivecodingtv.api.models.SchedulePageModel;
import com.sepjani.unofficiallivecodingtv.fragments.ScheduleFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Valeriy Strucovskiy on 5/30/2016.
 */
public class SchedulePresenter {

    private ScheduleFragment fragment;
    private ListAdapter adapter;
    private List<ScheduleModel> objects = new ArrayList<>();

    public SchedulePresenter(ScheduleFragment fragment) {
        this.fragment = fragment;
        adapter = new ListAdapter(fragment.getContext(), objects);
        fragment.listView.setAdapter(adapter);
    }

    public void updateValues() {

        new RestAPIClient()
                .getAPI()
                .getAllScheduledStreams()
                .enqueue(new Callback<SchedulePageModel>() {
                    @Override
                    public void onResponse(Call<SchedulePageModel> call, Response<SchedulePageModel> response) {
                        objects.clear();
                        //Collections.sort(response.body().results);
                        objects.addAll(response.body().results);
                        adapter.notifyDataSetInvalidated();
                        fragment.swipeLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Call<SchedulePageModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    public class ListAdapter extends ArrayAdapter<ScheduleModel> {
        public ListAdapter(Context context, List<ScheduleModel> objects) {
            super(context, R.layout.item_video, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            ScheduleModel itemModel = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_schedule, null, false);
                viewHolder.date = (TextView) convertView.findViewById(R.id.tv_date);
                viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.name = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.nickname = (TextView) convertView.findViewById(R.id.tv_nickname);
                viewHolder.language = (TextView) convertView.findViewById(R.id.tv_language);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            try {
                Date date = parseDate(itemModel.startTime);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                String currentTime = calendar.get(Calendar.HOUR_OF_DAY) + ":";
                int minute = calendar.get(Calendar.MINUTE);
                if (minute < 10) {
                    currentTime += "0";
                    currentTime += minute;
                }
                viewHolder.date.setText(currentTime);
                if (calendar.get(Calendar.AM_PM) == 0)
                    currentTime = currentTime + " AM";
                else
                    currentTime = currentTime + " PM";
                viewHolder.date.setText(currentTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            viewHolder.title.setText(itemModel.title);
            viewHolder.nickname.setText(itemModel.livestream);
            viewHolder.language.setText(itemModel.codingCategory);
            return convertView;
        }

        // View lookup cache
        private class ViewHolder {
            TextView date;
            TextView title;
            TextView name;
            TextView nickname;
            TextView language;
        }
    }

    public Date parseDate(String string) throws ParseException {
        String defaultTimezone = TimeZone.getDefault().getID();
        return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:SSZ")).parse(string.replaceAll("Z$", "+0000"));
    }

}
