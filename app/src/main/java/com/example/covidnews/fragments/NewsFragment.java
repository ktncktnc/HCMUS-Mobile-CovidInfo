package com.example.covidnews.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidnews.Adapters.NewsAdapter;
import com.example.covidnews.Objects.NewsItem;
import com.example.covidnews.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsFragment extends Fragment {
    private int mLocation = 0;
    // = 0: world
    // = 1: VietNam
    private RecyclerView mRecyclerView;
    private NewsAdapter mAdapter;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news, container, false);

        mRecyclerView = view.findViewById(R.id.listViewRSS);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.newsitem_avt);

        Date date = new Date();
        ArrayList<NewsItem> data = new ArrayList<NewsItem>();
        data.add(new NewsItem(bitmap, "NÓNG: Thanh niên nhập cảnh 'chui' ở quận 9 dương tính COVID-19", date,
                "Doremon", "TTO - Nam thanh niên tên K. nhập cảnh 'chui' về quận 9, TP.HCM đã có kết quả xét nghiệm dương tính với COVID-19. Như vậy trong nhóm 6 người...", "a"));
        data.add(new NewsItem(bitmap, "NÓNG: Thanh niên nhập cảnh 'chui' ở quận 9 dương tính COVID-19", date,
                "Doremon", "TTO - Nam thanh niên tên K. nhập cảnh 'chui' về quận 9, TP.HCM đã có kết quả xét nghiệm dương tính với COVID-19. Như vậy trong nhóm 6 người...", "a"));
        data.add(new NewsItem(bitmap, "NÓNG: Thanh niên nhập cảnh 'chui' ở quận 9 dương tính COVID-19", date,
                "Doremon", "TTO - Nam thanh niên tên K. nhập cảnh 'chui' về quận 9, TP.HCM đã có kết quả xét nghiệm dương tính với COVID-19. Như vậy trong nhóm 6 người...", "a"));
        data.add(new NewsItem(bitmap, "NÓNG: Thanh niên nhập cảnh 'chui' ở quận 9 dương tính COVID-19", date,
                "Doremon", "TTO - Nam thanh niên tên K. nhập cảnh 'chui' về quận 9, TP.HCM đã có kết quả xét nghiệm dương tính với COVID-19. Như vậy trong nhóm 6 người...", "a"));
        data.add(new NewsItem(bitmap, "NÓNG: Thanh niên nhập cảnh 'chui' ở quận 9 dương tính COVID-19", date,
                "Doremon", "TTO - Nam thanh niên tên K. nhập cảnh 'chui' về quận 9, TP.HCM đã có kết quả xét nghiệm dương tính với COVID-19. Như vậy trong nhóm 6 người...", "a"));

        mAdapter = new NewsAdapter(data, getContext());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private void fetchData() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("DBG", "Fetching data");
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("https://vnexpress.net/rss/suc-khoe.rss").build();
                XmlPullParser xmlPullParser;
                XmlPullParserFactory factory = null;
                try {
                    factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(false);
                    xmlPullParser = factory.newPullParser();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            Log.d("DBG", "Error in fetching RSS: " + e.toString());
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            InputStream inputStream = new ByteArrayInputStream(response.body().toString().getBytes()) ;
                            try {
                                xmlPullParser.setInput(inputStream, "UTF-8");
                            } catch (XmlPullParserException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        executorService(runnable);
    }

    private void executorService(Runnable runnable) {

        executorService.execute(runnable);
    }


}
