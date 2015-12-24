package com.lightningboltstudios.audubontrailmap;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

public class TrailMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    final float DEFAULT_ZOOM = (float) 15.25;
    Integer cameraUpdateLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cameraUpdateLock = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trail_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker to Schlitz Audubon and move the camera
        LatLng schlitzAudubon = new LatLng(43.174265, -87.886025);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(schlitzAudubon));
        CameraUpdate cameraZoomUpdate = CameraUpdateFactory.zoomTo(DEFAULT_ZOOM);
        mMap.moveCamera(cameraZoomUpdate);
        mMap.getUiSettings().setTiltGesturesEnabled(false);

        //Add custom graphic to map for Audubon
        GroundOverlayOptions audubonTrailMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.trailmapimage))
                .position(schlitzAudubon, 1860f, 1092f);
        mMap.addGroundOverlay(audubonTrailMap);

        setMarkers();

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {
                VisibleRegion vr = mMap.getProjection().getVisibleRegion();
                double left = vr.latLngBounds.southwest.longitude;
                double top = vr.latLngBounds.northeast.latitude;
                double right = vr.latLngBounds.northeast.longitude;
                double bottom = vr.latLngBounds.southwest.latitude;

                zoomFix(position);
                checkXYAxis(left, top, right, bottom);
            }
        });
    }

    public void zoomFix(CameraPosition position){
        if (position.zoom < DEFAULT_ZOOM) {
            CameraUpdate defaultZoomMove = CameraUpdateFactory.zoomTo(DEFAULT_ZOOM);
            mMap.moveCamera(defaultZoomMove);
        }
        else if (position.zoom > 17){
            CameraUpdate zoomOut = CameraUpdateFactory.zoomTo(DEFAULT_ZOOM);
            mMap.moveCamera(zoomOut);
        }
    }

    public double[] checkXAxis(double left, double top, double right, double bottom){
        if (left < -87.896567) {
            left = -87.896567;
        }
        else if (right > -87.874628)
        {
            right = -87.874628;
        }
        return new double[]{left, top, right, bottom};
    }

    public double[] checkYAxis(double left, double top, double right, double bottom){
        if (top > 43.178949){
            top = 43.169292;
        }
        else if (bottom < 43.169292){
            bottom = 43.169292;
        }
        return new double[]{left, top, right, bottom};
    }
    
    public void checkXYAxis(double left, double top, double right, double bottom){
        //X
        if (left < -87.896567) {
            left = -87.896567;
        }
        else if (right > -87.874628)
        {
            right = -87.874628;
        }
        //Y
        if (top > 43.178949){
            top = 43.169292;
        }
        else if (bottom < 43.169292){
            bottom = 43.169292;
        }
        
        //update camera position
        LatLng southwest = new LatLng(bottom, left);
        LatLng northeast = new LatLng(top, right);
        LatLngBounds newBounds = new LatLngBounds(southwest, northeast);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(newBounds, 0);
        mMap.moveCamera(cameraUpdate);
    }

    public void setMarkers(){
        LatLng mainBuilding = new LatLng(43.174961, -87.885165);
        LatLng observationTower = new LatLng(43.173308, -87.884019);
        LatLng pavilion = new LatLng(43.174203, -87.884298);
        LatLng mysteryLake = new LatLng(43.173289, -87.886801);
        LatLng boardwalkPond = new LatLng(43.173308, -87.889794);
        LatLng secretDoorToSolitudeMarsh = new LatLng(43.170981, -87.888717);
        LatLng birdBlindPond = new LatLng(43.172987, -87.894747);
        LatLng farmEquipment = new LatLng(43.174139, -87.890424);
        LatLng lakeMichiganNorthTrail = new LatLng(43.177314, -87.884221);
        LatLng lakeMichiganSouthTrail = new LatLng(43.175525, -87.883239);

        mMap.addMarker(new MarkerOptions().position(mainBuilding).title("Main Building").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(observationTower).title("Observation Tower").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(pavilion).title("Pavilion").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(mysteryLake).title("Mystery Lake").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(boardwalkPond).title("Boardwalk Pond").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(secretDoorToSolitudeMarsh).title("Secret Door to Solitude Marsh").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(birdBlindPond).title("Bird Blind Pond").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(farmEquipment).title("Farm Equipment").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(lakeMichiganNorthTrail).title("Lake Michigan (North Trail)").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(lakeMichiganSouthTrail).title("Lake Michigan (South Trail)").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
    }
}
