package com.lightningboltstudios.audubontrailmap;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainBuildingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Main Building");
        setContentView(R.layout.activity_main_building);
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
