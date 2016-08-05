package com.example.android.news;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newsListItem = convertView;
        if (newsListItem == null) {
            newsListItem = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }

        News currentNews = getItem(position);

        ImageView newsImageView = (ImageView) newsListItem.findViewById(R.id.news_image);
        TextView titleTextView = (TextView) newsListItem.findViewById(R.id.news_title);
        TextView authorTextView = (TextView) newsListItem.findViewById(R.id.news_author);

        if (currentNews.hasImage()) {
            Picasso.with(getContext()).load(currentNews.getImageResource()).into(newsImageView);
            newsImageView.setVisibility(View.VISIBLE);
        } else {
            newsImageView.setVisibility(View.GONE);
        }

        titleTextView.setText(currentNews.getTitle());
        authorTextView.setText(currentNews.getAuthor());

        return newsListItem;
    }

}
