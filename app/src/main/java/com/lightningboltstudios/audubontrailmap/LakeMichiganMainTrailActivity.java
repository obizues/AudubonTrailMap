package com.lightningboltstudios.audubontrailmap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LakeMichiganMainTrailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Lake Michigan (Main Trail)");
        setContentView(R.layout.activity_lake_michigan_main_trail);
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
