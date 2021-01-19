package com.example.covidnews.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Xml;
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
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private ArrayList<NewsItem> data;
    ArrayList<String> titles;
    ArrayList<String> links;
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
        fetchData();



        return view;
    }

    private void fetchData() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL("https://vnexpress.net/rss/suc-khoe.rss");

                    //creates new instance of PullParserFactory that can be used to create XML pull parsers
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

                    //Specifies whether the parser produced by this factory will provide support
                    //for XML namespaces
                    factory.setNamespaceAware(false);

                    //creates a new instance of a XML pull parser using the currently configured
                    //factory features
                    XmlPullParser xpp = factory.newPullParser();

                    // We will get the XML from an input stream
                    xpp.setInput(getInputStream(url), "UTF_8");

                    /* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
                     * We should take into consideration that the rss feed name is also enclosed in a "<title>" tag.
                     * Every feed begins with these lines: "<channel><title>Feed_Name</title> etc."
                     * We should skip the "<title>" tag which is a child of "<channel>" tag,
                     * and take into consideration only the "<title>" tag which is a child of the "<item>" tag
                     *
                     * In order to achieve this, we will make use of a boolean variable called "insideItem".
                     */
                    boolean insideItem = false;
                    NewsItem item = new NewsItem();
                    String title = null, des = null, link = null;
                    Date date = null;
                    data = new ArrayList<NewsItem>();
                    Pattern patternDes = Pattern.compile("<\\/br>.*");
                    Pattern pattern = Pattern.compile("(Covid)|(covid)|(corona)|(Corona)|(nCoV)");
                    Matcher matcher;

                    // Returns the type of current event: START_TAG, END_TAG, START_DOCUMENT, END_DOCUMENT etc..
                    int eventType = xpp.getEventType(); //loop control variable

                    while (eventType != XmlPullParser.END_DOCUMENT)
                    {
                        Log.d("DBG", "Tag: " + xpp.getName());
                        //if we are at a START_TAG (opening tag)
                        if (eventType == XmlPullParser.START_TAG)
                        {
                            //if the tag is called "item"
                            if (xpp.getName().equalsIgnoreCase("item")){
                                insideItem = true;
                            }
                            //if the tag is called "title"
                            else if (xpp.getName().equalsIgnoreCase("title")){
                                if (insideItem)
                                {
                                    title = xpp.nextText();

                                    // extract the text between <title> and </title>

                                }
                            }
                            //if the tag is called "link"
                            else if (xpp.getName().equalsIgnoreCase("link")){
                                if (insideItem)
                                {
                                    // extract the text between <link> and </link>
                                    link = xpp.nextText();
                                }
                            }
                            else if(xpp.getName().equalsIgnoreCase("description")){
                                if(insideItem == true){
                                    matcher = patternDes.matcher(xpp.nextText());
                                    if(matcher.find()){
                                        des = matcher.group(0).substring(5);
                                    }
                                }
                            }
                            else if(xpp.getName().equalsIgnoreCase("pubDate")){
                                if(insideItem == true){
                                    date = new SimpleDateFormat("dd MMM yyyy").parse(xpp.nextText().substring(5, 15));
                                }
                            }
                        }
                        //if we are at an END_TAG and the END_TAG is called "item"
                        else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
                            insideItem = false;
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vnexpress_logo);
                            item = new NewsItem();
                            item.setmAvt(bitmap);
                            item.setmAuthor("VNExpress");
                            item.setmTitle(title);
                            item.setmLink(link);
                            item.setmDes(des);
                            item.setmTime(date);
                            Log.d("DBG", "Parsed an item");
                            matcher = pattern.matcher(item.getmTitle() + item.getmDes());
                            if(matcher.find() ==true){
                                Log.d("DBG", "Add a item do arr list");
                                if(item != null)
                                data.add(item);
                            }

                        }

                        eventType = xpp.next(); //move to next element
                    }

                } catch (MalformedURLException e) {
                    Log.d("DBG", "RSS ERR");
                } catch (XmlPullParserException e) {
                    Log.d("DBG", "RSS ERR");

                } catch (IOException e) {
                    Log.d("DBG", "RSS ERR");
                } catch (ParseException e) {
                    e.printStackTrace(); }

                Log.d("DBG", "size: " + Integer.toString(data.size()));
                for(int i = 0; i < data.size(); i++)
                    Log.d("DBG", data.get(i).toString());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setAdapter();
                    }
                });
            };
        };
        executorService(runnable);

    }

    public InputStream getInputStream(URL url)
    {
        try
        {
            //openConnection() returns instance that represents a connection to the remote object referred to by the URL
            //getInputStream() returns a stream that reads from the open connection
            return url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            return null;
        }
    }

    private void executorService(Runnable runnable) {

        executorService.execute(runnable);
    }

    private void setAdapter(){
        mAdapter = new NewsAdapter(data, getContext());
        mRecyclerView.setAdapter(mAdapter);
    }


}
