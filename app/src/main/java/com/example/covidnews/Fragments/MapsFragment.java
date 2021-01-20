package com.example.covidnews.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.covidnews.Adapters.patientOnMap;
import com.example.covidnews.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        ArrayList<patientOnMap> newCases = new ArrayList<patientOnMap>();

        newCases.add(new patientOnMap(10.7428679,106.6820651, 0));
        newCases.add(new patientOnMap(10.7428889,106.6846186, 30));
        newCases.add(new patientOnMap(10.7451236,106.685906, 10));
        //newCases.add(new patientOnMap(10.7471773,106.687179, 10));

//        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
//        Bitmap bmp = Bitmap.createBitmap(200, 200, conf);
//        Canvas canvas1 = new Canvas(bmp);

        for (int i = 0; i < newCases.size(); i++){
            Bitmap.Config conf = Bitmap.Config.ARGB_8888;
            Bitmap bmp = Bitmap.createBitmap(200, 200, conf);
            Canvas canvas1 = new Canvas(bmp);
            double x, y;
            int numberInfectedDay;
            x = newCases.get(i).getCurPosX();
            y = newCases.get(i).getCurPosY();
            numberInfectedDay = newCases.get(i).getCurPosDay();
            LatLng curPos = new LatLng(x, y);
            MarkerOptions options = new MarkerOptions().position(curPos).title("Marker in position " + String.valueOf(i));

            // paint defines the text color, stroke width and size
            Paint color = new Paint();
            color.setTextSize(35);
            color.setColor(Color.BLACK);

            // modify canvas
            //canvas1.drawText("Patient " + String.valueOf(i), 30, 40, color);
            if (numberInfectedDay < 14){
                canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                        R.drawable.alert_danger_2), 0,0, color);
            }
            else {
                canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                        R.drawable.alert_danger_1), 0,0, color);
//                options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
//                mMap.addMarker(options);
            }
            // add marker to Map
            mMap.addMarker(new MarkerOptions()
                    .position(curPos)
                    .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                    // Specifies the anchor to be at a particular point in the marker image.
                    .anchor(0.5f, 1));
            ;
            //mMap.addMarker(options);
        }
        LatLng curPos = new LatLng(10.7471773, 106.6871793);

        MarkerOptions options = new MarkerOptions().position(curPos).title("Marker in current position!");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(options);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curPos, 16));
    }


}