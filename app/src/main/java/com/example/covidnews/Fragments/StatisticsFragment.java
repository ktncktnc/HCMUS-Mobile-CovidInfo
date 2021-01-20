package com.example.covidnews.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.covidnews.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StatisticsFragment extends Fragment {
    private int location = 1;
    private int time = 0;
    private Activity mActivity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (Activity)context;
    }

    private boolean fetch = false;
    private boolean fetch_graphViet = false;
    private boolean fetch_graphWorld = false;
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());
    //location:
    // = 0: World
    // = 1: VietNam
    //time:
    // = 0: total
    // = 1: yesterday
    // = 2: 2 days ago
    private ArrayList < JSONObject > dataStatistic = new ArrayList < JSONObject > ();
    private ArrayList<JSONObject> dataGraph = new ArrayList<JSONObject>();
    private TextView numberAffected;
    private TextView numberDeath;
    private TextView numberRecover;
    private TextView numberActive;
    private TextView numberSerious;
    private BarChart barChart;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.statistics, container, false);
        MaterialButtonToggleGroup toggleGroup = view.findViewById(R.id.statistic_toggle);
        MaterialButtonToggleGroup timeGroup = view.findViewById(R.id.statistic_time);

        MaterialButton toggleVN = view.findViewById(R.id.statistic_toggle_vietnam);
        MaterialButton toggleWorld = view.findViewById(R.id.statistic_toggle_world);
        MaterialButton timeTotal = view.findViewById(R.id.statistic_time_total);
        MaterialButton time2daysago = view.findViewById(R.id.statistic_time_2daysago);
        MaterialButton timeYesterday = view.findViewById(R.id.statistic_time_yesterday);



        numberAffected = view.findViewById(R.id.numbers_affected);
        numberDeath = view.findViewById(R.id.numbers_death);
        numberRecover = view.findViewById(R.id.numbers_recovered);
        numberActive = view.findViewById(R.id.numbers_active);
        numberSerious = view.findViewById(R.id.numbers_serious);
        barChart = view.findViewById(R.id.chart_mainchart);
        toggleVN.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));


        fetchData();
        fetchDataGraph();

        toggleGroup.check(R.id.statistic_toggle_vietnam);

        timeGroup.check(R.id.statistic_time_total);
        toggleGroup.check(R.id.statistic_toggle_vietnam);

        toggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (checkedId == R.id.statistic_toggle_vietnam) {
                    setSelectedToggleButton(toggleVN);
                    setUnselectedToggleButton(toggleWorld);
                    location = 1;
                    setStatistic();
                } else {
                    setSelectedToggleButton(toggleWorld);
                    setUnselectedToggleButton(toggleVN);
                    location = 0;
                    setStatistic();
                }
            }
        });

        timeGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                setUnselectedTimeButton(timeTotal);
                setUnselectedTimeButton(time2daysago);
                setUnselectedTimeButton(timeYesterday);
                if (checkedId == R.id.statistic_time_total) {
                    setSelectedTimeButton(timeTotal);
                    time = 0;
                    setStatistic();

                } else if (checkedId == R.id.statistic_time_yesterday) {
                    setSelectedTimeButton(timeYesterday);
                    time = 1;
                    setStatistic();

                } else {
                    setSelectedTimeButton(time2daysago);
                    time = 2;
                    setStatistic();
                }
            }
        });

        return view;
    }

    private void setChart(BarChart barChart, BarDataSet dataSet) {
        dataSet.setColor(mActivity.getResources().getColor(R.color.red));
        //Typeface raleway = ResourcesCompat.getFont(mActivity, R.font.raleway_semibold);
        //dataSet.setValueTypeface(raleway);
        dataSet.setDrawValues(false);
        BarData data = new BarData(dataSet);
        data.setBarWidth((float) 0.3);

        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        //barChart.getAxisLeft().setTypeface(raleway);
        barChart.getAxisLeft().setTextSize((float) 11.0);
        barChart.getAxisLeft().setTextColor(mActivity.getResources().getColor(R.color.brown));
        barChart.getAxisLeft().setGridColor(mActivity.getResources().getColor(R.color.brown));
        XAxis xAxis = barChart.getXAxis();

        xAxis.setDrawGridLines(false);
        //xAxis.setTypeface(raleway);
        xAxis.setTextSize((float) 11.0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(mActivity.getResources().getColor(R.color.brown));

        barChart.getAxisLeft().enableGridDashedLine((float) 8.0, (float) 5.0, (float) 1.0);
        barChart.invalidate();
    }

    private void setSelectedToggleButton(MaterialButton button) {
        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        button.setTextColor(mActivity.getResources().getColor(R.color.black));
    }
    private void setUnselectedToggleButton(MaterialButton button) {
        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.trans)));
        button.setTextColor(mActivity.getResources().getColor(R.color.white));
        button.setStateListAnimator(null);
    }

    private void setSelectedTimeButton(MaterialButton button) {
        button.setTextColor(mActivity.getResources().getColor(R.color.white));
    }

    private void setUnselectedTimeButton(MaterialButton button) {
        button.setTextColor(mActivity.getResources().getColor(R.color.brown));
    }

    private void fetchDataGraph(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("DBG", "Fetch graph data");
                OkHttpClient client = new OkHttpClient();
                Request requestGraphViet = new Request.Builder().url("https://covidnewsapi.herokuapp.com/api/graph/viet").build();
                Request requestGraphWorld = new Request.Builder().url("https://covidnewsapi.herokuapp.com/api/graph/world").build();
                client.newCall(requestGraphViet).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                        Log.d("DBG", "Error: " + e.toString());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String result = response.body().string();
                        try {
                            dataGraph.add(new JSONObject(result));

                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fetch_graphWorld = true;
                                    setStatistic();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                client.newCall(requestGraphWorld).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                        Log.d("DBG", "Error: " + e.toString());
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(fetch_graphViet == false && fetch_graphWorld == false) fetchDataGraph();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String result = response.body().string();
                        try {
                            if(dataGraph.size() > 0)
                                dataGraph.add(0, new JSONObject(result));
                            else
                                dataGraph.add(new JSONObject(result));

                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fetch_graphViet = true;
                                    setStatistic();
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        };
        executorService(runnable);
    }

    private void fetchData() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("DBG", "Fetching data");
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("https://covidnewsapi.herokuapp.com/api/statistic/").build();


                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                        Log.d("DBG", "Error: " + e.toString());
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fetchData();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            //Log.d("DBG", "Error: " + response.toString());
                            throw new IOException("Unexpected code " + response);
                        } else {
                            String result = response.body().string();
                            //Log.d("DBG", result);
                            try {
                                JSONObject object = new JSONObject(result);
                                JSONObject jsonObject;
                                for (int i = 0; i < 6; i++) {
                                    jsonObject = object.getJSONObject(Integer.toString(i));
                                    dataStatistic.add(jsonObject);
                                }
                                fetch = true;
                                mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        fetch = true;
                                        setStatistic();
                                    }
                                });

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        };
        executorService(runnable);
    }

    private void executorService(Runnable runnable) {

        executorService.execute(runnable);
    }

    private void setStatistic() {
        if (fetch == false) return;
        JSONObject object = dataStatistic.get(location + time * 2);
        try {
            String str = object.getString("TotalCases");
            numberAffected.setText(normalizeString(str));
            str = object.getString("TotalDeaths");
            numberDeath.setText(normalizeString(str));
            str = object.getString("TotalRecovered");
            numberRecover.setText(normalizeString(str));
            str = object.getString("ActiveCases");
            numberActive.setText(normalizeString(str));
            str = object.getString("CriticalCases");
            numberSerious.setText(str.length() == 0 ? "No data" : normalizeString(str));

        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("DBG", "setStatistic() error: " + e.toString());
        }

        if(fetch_graphViet == false || fetch_graphWorld == false) return;

        JSONObject jsonObject = dataGraph.get(location);
        Iterator<String> keys = jsonObject.keys();
        ArrayList<String> axisLabel = new ArrayList<String>();
        List < BarEntry > list = new ArrayList < BarEntry > ();
        int value;
        int i = 0;
        String key;
        while(keys.hasNext()) {
            key = keys.next();
            try {
                value = jsonObject.getInt(key);
                axisLabel.add(0, key);
                list.add(0, new BarEntry(i, value));
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(axisLabel));
        BarDataSet dataSet = new BarDataSet(list, null);

        setChart(barChart, dataSet);
    }

    private String normalizeString(String buf) {
        String res = new String();
        for (char ch: buf.toCharArray()) {
            if ((ch >= '0' && ch <= '9') || ch == ',') {
                res += ch;
            }
        }
        return res;
    }
}