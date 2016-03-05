package com.lightningboltstudios.audubontrailmap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

public class WigwamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Wigwam");
        setContentView(R.layout.activity_wigwam);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    public void showOnMap(View view) {
        Intent intent = new Intent(this, TrailMapsActivity.class);
        intent.putExtra("location", getTitle());
        startActivity(intent);
    }
}
