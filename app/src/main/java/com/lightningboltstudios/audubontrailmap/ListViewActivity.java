package com.lightningboltstudios.audubontrailmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ListViewActivity extends ActionBarActivity {
    ListView listView;
    int[]move_poster_resource={R.drawable.schlitzmainbuilding,R.drawable.schlitzobservationtower,R.drawable.schlitzpavilion,
            R.drawable.schlitzmysterylake,R.drawable.schlitzboardwalkpond,R.drawable.schlitzsolitudemarsh,R.drawable.schlitzbirdblind,R.drawable.schlitzfarmequipment,R.drawable.schlitzwigwam,R.drawable.schlitzlakemichigan, R.drawable.schlitzlakemichigan,};
    String[]move_title;
    String[]move_rating;
    MoveAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        listView=(ListView)findViewById(R.id.list_view);
        move_rating=getResources().getStringArray(R.array.move_rating);
        move_title=getResources().getStringArray(R.array .move_title);

        int i=0;
        adapter=new MoveAdapter(getApplicationContext(),R.layout.activity_list_view);
        listView.setAdapter(adapter);
        for (String title:move_title) {
            MoveDataProvider dataProvider=new MoveDataProvider(move_poster_resource[i],
                    title,move_rating[i]);
            adapter.add(dataProvider);
            i++;

        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?>parent, View view, int position, long id){
                Intent myIntent;

                switch(position){
                    case 0: myIntent = new Intent(view.getContext(), MainBuildingActivity.class);
                        break;
                    case 1: myIntent = new Intent(view.getContext(), ObservationTowerActivity.class);
                        break;
                    case 2: myIntent = new Intent(view.getContext(), PavilionActivity.class);
                        break;
                    case 3: myIntent = new Intent(view.getContext(), MysteryLakeActivity.class);
                        break;
                    case 4: myIntent = new Intent(view.getContext(), BoardwalkPondActivity.class);
                        break;
                    case 5: myIntent = new Intent(view.getContext(), SecretDoorToSolitudeMarshActivity.class);
                        break;
                    case 6: myIntent = new Intent(view.getContext(), BirdBlindPondActivity.class);
                        break;
                    case 7: myIntent = new Intent(view.getContext(), FarmEquipmentActivity.class);
                        break;
                    case 8: myIntent = new Intent(view.getContext(), WigwamActivity.class);
                        break;
                    case 9: myIntent = new Intent(view.getContext(), LakeMichiganNorthTrailActivity.class);
                        break;
                    case 10: myIntent = new Intent(view.getContext(), LakeMichiganSouthTrailActivity.class);
                        break;
                    default: myIntent = new Intent(view.getContext(), ListViewActivity.class);
                }
                //Toast.makeText(getBaseContext(), position + "is selected", Toast.LENGTH_SHORT).show();
                //Intent myIntent = new Intent(view.getContext(), ListViewActivity.class);
                startActivity(myIntent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}