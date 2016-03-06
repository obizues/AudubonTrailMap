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

    LatLng mainBuilding = new LatLng(43.174961, -87.885165);
    LatLng observationTower = new LatLng(43.173308, -87.884019);
    LatLng pavilion = new LatLng(43.174203, -87.884298);
    LatLng mysteryLake = new LatLng(43.173289, -87.886801);
    LatLng boardwalkPond = new LatLng(43.173308, -87.889794);
    LatLng solitudeMarsh = new LatLng(43.170981, -87.888717);
    LatLng birdBlindPond = new LatLng(43.172987, -87.894747);
    LatLng farmEquipment = new LatLng(43.174139, -87.890424);
    LatLng lakeMichiganNorthStair = new LatLng(43.177314, -87.884221);
    LatLng lakeMichiganMainTrail = new LatLng(43.175525, -87.883239);
    private double viewportHeight = 0.0;
    private double viewportWidth = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        LatLng initialMapLocation;
        LatLng schlitzLocation = new LatLng(43.174265, -87.886025);
        CameraUpdate cameraZoomUpdate = CameraUpdateFactory.zoomTo(DEFAULT_ZOOM);
        Bundle extras = getIntent().getExtras();

        initialMapLocation = setInitialMapLocation(extras, schlitzLocation);

        // Add a marker to Schlitz Audubon and move the camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(initialMapLocation));
        mMap.moveCamera(cameraZoomUpdate);
        updateViewport();
        mMap.getUiSettings().setTiltGesturesEnabled(false);
        mMap.getUiSettings().setRotateGesturesEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setMyLocationEnabled(true);

        //Add custom graphic to map for Audubon
        GroundOverlayOptions audubonTrailMap = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.trailmapimage))
                .position(schlitzLocation, 1860f, 1092f);
        GroundOverlayOptions whiteMapOverlay = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.whitemapoverlay))
                .position(schlitzLocation, 5000f, 5000f);
        mMap.addGroundOverlay(whiteMapOverlay);
        mMap.addGroundOverlay(audubonTrailMap);

        setMarkers();

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {
                VisibleRegion vr = mMap.getProjection().getVisibleRegion();

                double left = vr.latLngBounds.southwest.longitude;
                double top  = vr.latLngBounds.northeast.latitude;
                double right = vr.latLngBounds.northeast.longitude;
                double bottom = vr.latLngBounds.southwest.latitude;
                zoomFix(position);
                checkXYAxis(left, top, right, bottom);
            }
        });
    }

    public LatLng setInitialMapLocation(Bundle extras, LatLng schlitzLocation){
        LatLng initialMapLocation = null;
        if (extras != null) {
            String location = extras.getString("location");
            assert location != null;
            if (location.equals("Bird Blind Pond")){
                return birdBlindPond;
            }
            if (location.equals("Boardwalk Pond")){
                initialMapLocation = boardwalkPond;
            }
            if (location.equals("Farm Equipment")){
                initialMapLocation = farmEquipment;
            }
            if (location.equals("Lake Michigan (North Stair)")){
                initialMapLocation = lakeMichiganNorthStair;
            }
            if (location.equals("Lake Michigan (Main Trail)")){
                initialMapLocation = lakeMichiganMainTrail;
            }
            if (location.equals("Main Building")){
                initialMapLocation = mainBuilding;
            }
            if (location.equals("Mystery Lake")){
                initialMapLocation = mysteryLake;
            }
            if (location.equals("Observation Tower")){
                initialMapLocation = observationTower;
            }
            if (location.equals("Pavilion")){
                initialMapLocation = pavilion;
            }
            if (location.equals("Solitude Marsh")){
                initialMapLocation = solitudeMarsh;
            }
            CameraUpdateFactory.zoomTo(16.5f);
        }
        else {
            initialMapLocation = schlitzLocation;
        }
        return initialMapLocation;
    }

    public void zoomFix(CameraPosition position) {
        if (position.zoom < DEFAULT_ZOOM) {
            CameraUpdate defaultZoomMove = CameraUpdateFactory.zoomTo(DEFAULT_ZOOM);
            mMap.moveCamera(defaultZoomMove);
        } else if (position.zoom > 17) {
            CameraUpdate zoomOut = CameraUpdateFactory.zoomTo(17);
            mMap.moveCamera(zoomOut);
        }
        updateViewport();
    }

    public void checkXYAxis(double left, double top, double right, double bottom) {
        boolean change = false;

        if (left < -87.896567) {
            left = -87.896567;
            right = left + viewportWidth;
            change = true;
        }
        else if (right > -87.874628) {
            right = -87.874628;
            left = right - viewportWidth;
            change = true;
        }
        //Y
        if (top > 43.178949) {
            top = 43.178949;
            bottom = top - viewportHeight;
            change = true;
        }
        else if (bottom < 43.169292) {
            bottom = 43.169292;
            top = bottom + viewportHeight;
            change = true;
        }
        if (change) {
            LatLng southwest = new LatLng(bottom, left);
            LatLng northeast = new LatLng(top, right);
            LatLngBounds newBounds = new LatLngBounds(southwest, northeast);
            CameraUpdate update = CameraUpdateFactory.newLatLngBounds(newBounds, 0);
            mMap.moveCamera(update);
            updateViewport();
        }
    }

    public void setMarkers(){
        mMap.addMarker(new MarkerOptions().position(mainBuilding).title("Main Building").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(observationTower).title("Observation Tower").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(pavilion).title("Pavilion").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(mysteryLake).title("Mystery Lake").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(boardwalkPond).title("Boardwalk Pond").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(solitudeMarsh).title("Solitude Marsh").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(birdBlindPond).title("Bird Blind Pond").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(farmEquipment).title("Farm Equipment").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(lakeMichiganNorthStair).title("Lake Michigan (North Trail)").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
        mMap.addMarker(new MarkerOptions().position(lakeMichiganMainTrail).title("Lake Michigan (South Trail)").icon(BitmapDescriptorFactory.fromResource(R.drawable.schlitzmarker)));
    }

    private void updateViewport() {
        LatLngBounds initialBounds = mMap.getProjection().getVisibleRegion().latLngBounds;
        viewportWidth = Math.abs(initialBounds.northeast.longitude
                - initialBounds.southwest.longitude);

        viewportHeight = Math.abs(initialBounds.northeast.latitude
                - initialBounds.southwest.latitude);
    }
}