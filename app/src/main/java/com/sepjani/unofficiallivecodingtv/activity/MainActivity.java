package com.sepjani.unofficiallivecodingtv.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.sepjani.unofficiallivecodingtv.LivecodingApplication;
import com.sepjani.unofficiallivecodingtv.PreferenceFields;
import com.sepjani.unofficiallivecodingtv.R;
import com.sepjani.unofficiallivecodingtv.api.RestAPIClient;
import com.sepjani.unofficiallivecodingtv.api.models.UserDetailModel;
import com.sepjani.unofficiallivecodingtv.busevents.HelloEvent;
import com.sepjani.unofficiallivecodingtv.fragments.ChatFragment;
import com.sepjani.unofficiallivecodingtv.fragments.HomeFragment;
import com.sepjani.unofficiallivecodingtv.fragments.ScheduleFragment;
import com.sepjani.unofficiallivecodingtv.fragments.VideosLiveFragment;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        EventBus.getDefault().register(this);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//


        navigationView.getMenu().performIdentifierAction(R.id.nav_home, 0);
        navigationView.getMenu().getItem(0).setChecked(true);


    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LivecodingApplication.getAppContext());
        long expiresin = prefs.getLong(PreferenceFields.API_EXPIRE, 0);
        long currentMillisec = System.currentTimeMillis();
        if (expiresin == 0 || expiresin < currentMillisec) {
            startActivity(new Intent(MainActivity.this, LoginWebActivity.class));
        } else {
            new RestAPIClient()
                    .getAPI()
                    .getUser()
                    .enqueue(new Callback<UserDetailModel>() {
                        @Override
                        public void onResponse(Call<UserDetailModel> call, Response<UserDetailModel> response) {
                            System.out.println(response.body());
                            ImageView avatar = (ImageView) findViewById(R.id.iv_avatar);
                            if (response.body() != null) {
                                Picasso.with(MainActivity.this)
                                        .load(response.body().avatar)
                                        .into(avatar);
                                ((TextView) findViewById(R.id.tv_username)).setText(response.body().username);
                                ((TextView) findViewById(R.id.tv_subtitle)).setText(response.body().wantLearn.toString());

                            }
                        }

                        @Override
                        public void onFailure(Call<UserDetailModel> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
        }

//
//        new RestAPIClient()
//                .getAPI()
//                .getAllScheduledStreams()
//                .enqueue(new Callback<SchedulePageModel>() {
//                    @Override
//                    public void onResponse(Call<SchedulePageModel> call, Response<SchedulePageModel> response) {
//                        System.out.println("Scheduled = " + response.body().count);
//                    }
//
//                    @Override
//                    public void onFailure(Call<SchedulePageModel> call, Throwable t) {
//                        t.printStackTrace();
//                    }
//                });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                replaceFragment(HomeFragment.class.getName());
                break;
            case R.id.nav_livestreams:
                replaceFragment(VideosLiveFragment.class.getName());
                break;

            case R.id.nav_videos:
                replaceFragment(VideosLiveFragment.class.getName());
                break;

            case R.id.nav_schedule:
                replaceFragment(ScheduleFragment.class.getName());
                break;

            case R.id.nav_settings:
                replaceFragment(ChatFragment.class.getName());
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(String fragmentClass) {
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.placeholder_fragment, Fragment.instantiate(MainActivity.this, fragmentClass));
        tx.commit();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(HelloEvent event) {
        System.out.println("NEW EVENT = " + event.getMessage());

        new RestAPIClient()
                .getAPI()
                .getUser()
                .enqueue(new Callback<UserDetailModel>() {
                    @Override
                    public void onResponse(Call<UserDetailModel> call, Response<UserDetailModel> response) {
                        System.out.println(response.body());
                    }

                    @Override
                    public void onFailure(Call<UserDetailModel> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
        //view.setText(view.getText() + "\n" + event.getData());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
