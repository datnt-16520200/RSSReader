package com.example.rssreader.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rssreader.R;
import com.example.rssreader.data.DBManager;
import com.example.rssreader.model.Channel;

public class EditChannelActivity extends AppCompatActivity {

    EditText edtChannelName, edtChannelLink;
    Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_channel);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edtChannelName = findViewById(R.id.edit_channel_name);
        edtChannelLink = findViewById(R.id.edit_channel_link);
        btnUpdate = findViewById(R.id.edit_channel_btnUpdate);

        //Get Bundle from ChannelListActivity
        Bundle bundle = getIntent().getExtras();
        final Channel channel = new Channel(bundle.getInt("id"),
                bundle.getString("name"),
                bundle.getString("link"),
                bundle.getString("imgUrl"));

        //show channel data to edittext
        edtChannelName.setText(channel.getmName());
        edtChannelLink.setText(channel.getmLink());

        //set event click for btn update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //update channel here
                Channel newChannel = new Channel();
                newChannel.setmId(channel.getmId());
                newChannel.setmName(edtChannelName.getText().toString().trim());
                newChannel.setmLink(edtChannelLink.getText().toString().trim());
                newChannel.setmImgUrl(channel.getmImgUrl());

                DBManager manager = new DBManager(EditChannelActivity.this);

                //if update success, show Toast, else show dialog
                if (manager.updateChannel(newChannel)>0){
                    Toast.makeText(EditChannelActivity.this, "Update: '"+ edtChannelName.getText().toString()+"' successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditChannelActivity.this);
                    builder.setTitle("Error Update")
                            .setMessage("Fail to update")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

                manager.close();

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