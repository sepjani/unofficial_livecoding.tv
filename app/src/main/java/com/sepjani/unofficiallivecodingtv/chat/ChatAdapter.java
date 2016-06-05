package com.sepjani.unofficiallivecodingtv.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sepjani.unofficiallivecodingtv.R;

import org.jivesoftware.smack.packet.Message;

/**
 * Created by Valeriy Strucovskiy on 6/5/2016.
 */
public class ChatAdapter extends ArrayAdapter<Message> {

    public ChatAdapter(Context context) {
        super(context, R.layout.item_chat);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message msg = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_chat, null, false);
            viewHolder.dateView = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.nickname = (TextView) convertView.findViewById(R.id.tv_nickname);
            viewHolder.text = (TextView) convertView.findViewById(R.id.tv_chat_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nickname.setText(parseFromMessage(msg.getFrom()));
        viewHolder.text.setText(msg.getBody());
        return convertView;
    }


    private String parseFromMessage(String fullname){
        return fullname.split("/")[1];
    }


    private class ViewHolder {
        public TextView dateView;
        public TextView nickname;
        public TextView text;
    }
}
