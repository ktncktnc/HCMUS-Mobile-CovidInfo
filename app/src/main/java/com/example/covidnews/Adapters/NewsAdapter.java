package com.example.covidnews.Adapters;

import android.content.Context;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidnews.Objects.NewsItem;
import com.example.covidnews.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder> {
    private final DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
    private ArrayList<NewsItem> mData;
    private Context mContext;


    public NewsAdapter(ArrayList<NewsItem> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        NewsItem item = mData.get(position);
        holder.setData(item);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ItemHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mAvt;
        TextView mTitle;
        TextView mDes;
        TextView mAuthor;
        TextView mTime;

        public ItemHolder(@NonNull View view){
            super(view);
            mAvt = view.findViewById(R.id.newsitem_avt);
            mTitle = view.findViewById(R.id.newsitem_title);
            mDes = view.findViewById(R.id.newsitem_des);
            mAuthor = view.findViewById(R.id.newsitem_author);
            mTime = view.findViewById(R.id.newsitem_time);
            view.setOnClickListener(this);
        }


        @RequiresApi(api = Build.VERSION_CODES.O)
        public void setData(NewsItem item){
            mAvt.setImageBitmap(item.getmAvt());
            mTitle.setText(item.getmTitle());
            mDes.setText(item.getmDes());
            mAuthor.setText(item.getmAuthor());
            mTime.setText(dateFormat.format(item.getmTime()));
            mTitle.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
            mDes.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);


        }

        @Override
        public void onClick(View v) {

        }
    }
}
