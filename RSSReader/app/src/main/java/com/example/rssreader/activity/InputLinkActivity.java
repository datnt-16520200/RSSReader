package com.example.rssreader.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rssreader.R;
import com.example.rssreader.util.ConnectivityChecking;

public class InputLinkActivity extends AppCompatActivity {

    EditText edtRSSLink;
    Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_link);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Find RSS From Link");

        //Find Id
        edtRSSLink = findViewById(R.id.input_rss_link);
        btnGo = findViewById(R.id.input_rss_btnGo);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ConnectivityChecking.isConnected((Activity)InputLinkActivity.this)){
                    //Go to list article activity
                    Intent intent = new Intent(InputLinkActivity.this, ArticleListActivity.class);

                    //packaged channel object to bundle
                    Bundle bundle = new Bundle();
                    bundle.putInt("id",-1);
                    bundle.putString("name","No Channel Name".trim());
                    bundle.putString("link",edtRSSLink.getText().toString().trim());
                    bundle.putString("imgUrl","No ImgUrl".trim());

                    //put bundle to intent
                    intent.putExtras(bundle);

                    //start ArticleListActivity
                    InputLinkActivity.this.startActivity(intent);
                }
                else {
                    ConnectivityChecking.showWifiDialog(InputLinkActivity.this);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}