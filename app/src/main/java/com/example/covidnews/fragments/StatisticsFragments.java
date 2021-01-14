package com.example.covidnews.fragments;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.covidnews.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragments extends Fragment {
    int location = 0;
    int time = 0;
    //location:
    // = 0: VietNam
    // = 1: World
    //time:
    // = 0: total
    // = 1: today
    // = 2: yesterday
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics, container, false) ;
        MaterialButtonToggleGroup toggleGroup = view.findViewById(R.id.statistic_toggle);
        MaterialButtonToggleGroup timeGroup = view.findViewById(R.id.statistic_time);

        MaterialButton toggleVN = view.findViewById(R.id.statistic_toggle_vietnam);
        MaterialButton toggleWorld = view.findViewById(R.id.statistic_toggle_world);
        MaterialButton timeToday = view.findViewById(R.id.statistic_time_today);
        MaterialButton timeTotal = view.findViewById(R.id.statistic_time_total);
        MaterialButton timeYesterday = view.findViewById(R.id.statistic_time_yesterday);

        toggleVN.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        BarChart barChart = view.findViewById(R.id.chart_mainchart);

        List<BarEntry> list = new ArrayList<BarEntry>();
        list.add(new BarEntry(1, 3000));
        list.add(new BarEntry(2, 700));
        list.add(new BarEntry(3, 2000));
        list.add(new BarEntry(4, 10000));
        list.add(new BarEntry(5, 800));
        list.add(new BarEntry(6, 5000));
        list.add(new BarEntry(7, 1300));

        BarDataSet dataSet = new BarDataSet(list, null);

        setChart(barChart, dataSet);



        toggleGroup.check(R.id.statistic_toggle_vietnam);

        timeGroup.check(R.id.statistic_time_today);
        toggleGroup.check(R.id.statistic_toggle_vietnam);

        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(checkedId == R.id.statistic_toggle_vietnam){
                    setSelectedToggleButton(toggleVN);
                    setUnselectedToggleButton(toggleWorld);
                }
                else{
                    setSelectedToggleButton(toggleWorld);
                    setUnselectedToggleButton(toggleVN);
                }
            }
        });

        timeGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                setUnselectedTimeButton(timeTotal);
                setUnselectedTimeButton(timeToday);
                setUnselectedTimeButton(timeYesterday);
                if(checkedId == R.id.statistic_time_today){
                    setSelectedTimeButton(timeToday);

                }
                else if(checkedId == R.id.statistic_time_total){
                    setSelectedTimeButton(timeTotal);

                }
                else{
                    setSelectedTimeButton(timeYesterday);

                }
            }
        });

        return view;
    }

    private void setChart(BarChart barChart, BarDataSet dataSet){
        dataSet.setColor(getResources().getColor(R.color.red));
        Typeface raleway = ResourcesCompat.getFont(getContext(), R.font.raleway_semibold);
        dataSet.setValueTypeface(raleway);
        dataSet.setDrawValues(false);
        BarData data = new BarData(dataSet);
        data.setBarWidth((float)0.3);

        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setTypeface(raleway);
        barChart.getAxisLeft().setTextSize((float)12.0);
        barChart.getAxisLeft().setTextColor(getResources().getColor(R.color.brown));
        barChart.getAxisLeft().setGridColor(getResources().getColor(R.color.brown));
        XAxis xAxis =  barChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setTypeface(raleway);
        xAxis.setTextSize((float)12.0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(getResources().getColor(R.color.brown));

        barChart.getAxisLeft().enableGridDashedLine((float)8.0,(float)5.0, (float)1.0);
        barChart.invalidate();
    }

    private void setSelectedToggleButton(MaterialButton button){
        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        button.setTextColor(getResources().getColor(R.color.black));
    }
    private void setUnselectedToggleButton(MaterialButton button){
        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.trans)));
        button.setTextColor(getResources().getColor(R.color.white));
        button.setStateListAnimator(null);
    }

    private void setSelectedTimeButton(MaterialButton button){
        button.setTextColor(getResources().getColor(R.color.white));
    }

    private void setUnselectedTimeButton(MaterialButton button){
        button.setTextColor(getResources().getColor(R.color.brown));
    }
}
