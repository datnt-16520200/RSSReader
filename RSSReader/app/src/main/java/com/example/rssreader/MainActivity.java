package com.example.rssreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rssreader.activity.InputLinkActivity;
import com.example.rssreader.data.DBManager;
import com.example.rssreader.fragment.AddChannelFragment;
import com.example.rssreader.fragment.ChannelListFragment;
import com.example.rssreader.fragment.ReadLaterFragment;
import com.example.rssreader.model.Channel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Find view Id
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nv_view);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (checkFirstTimeAppRun()==true){
            //Toast
            Toast.makeText(this, "First time start", Toast.LENGTH_SHORT).show();

            //Add default data to Table Channel
            addDefaultData();

            //Update FirstTimeMode to false
            SharedPreferences prefs = this.getSharedPreferences("FirstTimeMode",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstStart",false);
            editor.apply();
        }

        //Set event item click
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                showFragment(item);
                return false;
            }
        });

        fragment = new ChannelListFragment();
        showDefaultFragment(fragment,"Channel List");
    }

    private void addDefaultData() {
        DBManager manager = new DBManager(MainActivity.this);

        //Create default channel
        List<Channel>  lstChannel = new ArrayList<>();
        lstChannel.add(new Channel("VNEXPRESS", "https://vnexpress.net/rss/tin-moi-nhat.rss","https://s.vnecdn.net/vnexpress/i/v20/logos/vne_logo_rss.png"));
        lstChannel.add(new Channel("Thanh Niên", "https://thanhnien.vn/rss/home.rss","https://static.thanhnien.vn/v2/App_Themes/images/logo-tn-2.png"));
        lstChannel.add(new Channel("Tuổi Trẻ", "https://tuoitre.vn/rss/tin-moi-nhat.rss","https://static.mediacdn.vn/tuoitre/web_images/tto_default_avatar.png"));
        lstChannel.add(new Channel("Ngôi Sao", "https://ngoisao.net/rss/tin-moi-nhat.rss","https://scdn.vnecdn.net/ngoisao/restruct/i/v58/ngoisao2016/graphics/ngoisao.jpg"));

        //Add default channels to database
        for(int i = 0; i < lstChannel.size(); i++){
            manager.addChannel(lstChannel.get(i));
        }

        manager.close();
    }

    //Function show fragment corresponding to menuItem
    private void showFragment(MenuItem item) {
        //Get MenuItem Id
        int id = item.getItemId();

        fragment = null;
        Class classFragment = null;

        //Create classFragment corresponding to menuItem
        if (id == R.id.channel_list)
            classFragment = ChannelListFragment.class;
        if (id == R.id.read_later)
            classFragment = ReadLaterFragment.class;
        if (id == R.id.add_channel)
            classFragment = AddChannelFragment.class;

        try {
            fragment = (Fragment) classFragment.newInstance();

            //Config FragmentManager to show Fragment
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.frame_layout,fragment)
                    .commit();

            setTitle(item.getTitle());
            drawerLayout.closeDrawers();
        }catch (Exception e){
            Log.d("loi",e.toString());
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDefaultFragment(Fragment fragment, String title){
        try{
            fragment = new ChannelListFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.frame_layout,fragment)
                    .commit();
            setTitle(title);
        }catch (Exception e){
            Log.d("loi",e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);

        MenuItem item = menu.findItem(R.id.search_item);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, InputLinkActivity.class);
                MainActivity.this.startActivity(intent);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private boolean checkFirstTimeAppRun(){
        SharedPreferences preferences = this.getSharedPreferences("FirstTimeMode", Context.MODE_PRIVATE);
        boolean firstStart = preferences.getBoolean("firstStart",true);

        return firstStart;
    }
}