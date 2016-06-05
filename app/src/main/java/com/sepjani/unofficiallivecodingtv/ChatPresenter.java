package com.sepjani.unofficiallivecodingtv;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;

import com.sepjani.unofficiallivecodingtv.api.RestAPIClient;
import com.sepjani.unofficiallivecodingtv.api.models.ChatCredentialsModel;
import com.sepjani.unofficiallivecodingtv.fragments.ChatFragment;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqprivate.PrivateDataManager;
import org.jivesoftware.smackx.muc.DefaultParticipantStatusListener;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Valeriy Strucovskiy on 6/2/2016.
 */
public class ChatPresenter {

    public static String SERVER_HOST = "livecoding.tv";
    public static int SERVER_PORT = 5222;
    private ChatFragment fragment;

    public String room = "@chat.livecoding.tv";
    public String username;
    public String password;

    private MultiUserChat chat;

    public ChatPresenter(ChatFragment fragment, String chatholder) {
        AppLoadingIndicator.show(fragment.getContext());
        room = chatholder + room;
        this.fragment = fragment;
        new RestAPIClient()
                .getAPI()
                .getChatCredentials()
                .enqueue(new Callback<ChatCredentialsModel>() {
                    @Override
                    public void onResponse(Call<ChatCredentialsModel> call, Response<ChatCredentialsModel> response) {
                        username = response.body().jid;
                        password = response.body().password;
                        initConnection();
                        AppLoadingIndicator.hide();
                    }

                    @Override
                    public void onFailure(Call<ChatCredentialsModel> call, Throwable t) {

                    }
                });
    }

    public void initConnection() {
        XMPPTCPConnectionConfiguration conf = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword(username, password)
                .setServiceName(SERVER_HOST)
                .build();
        AbstractXMPPConnection connection = new XMPPTCPConnection(conf);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    AbstractXMPPConnection conn = connection.connect();
                    conn.login();
                } catch (XMPPException e) {
                    e.printStackTrace();
                } catch (SmackException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MultiUserChatManager manager = MultiUserChatManager.getInstanceFor(connection);
                chat = manager.getMultiUserChat("iamvalerio@chat.livecoding.tv");
                PrivateDataManager privatedataManager = PrivateDataManager.getInstanceFor(connection);
//                privatedataManager.getPrivateData()

                chat.addMessageListener(message -> {
                    fragment.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            fragment.adapter.add(message);
                            fragment.adapter.notifyDataSetChanged();
                        }
                    });

                });



                chat.addParticipantStatusListener(new DefaultParticipantStatusListener() {
                    @Override
                    public void joined(String participant) {
                        Message msg = new Message();
                        msg.setFrom("system/*");
                        msg.setBody(participant.split("/")[1] + " joined the room.");
                        fragment.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fragment.adapter.add(msg);
                                fragment.adapter.notifyDataSetChanged();
                            }
                        });

                    }
                });

                try {
                    chat.join(username.split("@")[0]);
                } catch (SmackException.NoResponseException e) {
                    e.printStackTrace();
                } catch (XMPPException.XMPPErrorException e) {
                    e.printStackTrace();
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                }

                return null;
            }


        }.execute();


    }

    public void sendMessage(String outgointMessage) {
        if (outgointMessage == null && outgointMessage.isEmpty()) {
            Log.d(LivecodingApplication.TAG, "sendMessage: false");
            return;
        }
        Message m = new Message();
        m.setBody(outgointMessage);
        try {
            chat.sendMessage(outgointMessage);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(EditText messageView) {
        String outgointMessage = messageView.getText().toString();
        if (outgointMessage == null && outgointMessage.isEmpty()) {
            Log.d(LivecodingApplication.TAG, "sendMessage: false");
            return;
        }
        messageView.setText("");
        Message m = new Message();
        m.setBody(outgointMessage);
        try {
            chat.sendMessage(outgointMessage);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }
}
