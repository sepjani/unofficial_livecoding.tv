package com.sepjani.unofficiallivecodingtv.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.sepjani.unofficiallivecodingtv.chat.ChatPresenter;
import com.sepjani.unofficiallivecodingtv.R;
import com.sepjani.unofficiallivecodingtv.chat.ChatAdapter;

/**
 * Created by Valeriy Strucovskiy on 6/5/2016.
 */
public class ChatFragment extends Fragment {

    View root;
    EditText messageView;
    Button sendButton;
    ListView listView;

    public ChatAdapter adapter;
    ChatPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_chat, null);
        initViews();

        adapter = new ChatAdapter(getContext());
        listView.setAdapter(adapter);
        presenter = new ChatPresenter(this, "iamvalerio");

        sendButton.setOnClickListener(view -> presenter.sendMessage(messageView));
        return root;
    }

    private void initViews() {
        messageView = (EditText) root.findViewById(R.id.et_chat_message);
        sendButton = (Button) root.findViewById(R.id.btn_send);
        listView = (ListView) root.findViewById(R.id.listView);

    }

}
