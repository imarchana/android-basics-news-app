package com.example.android.news;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    NewsAdapter adapter;
    ArrayList<News> news;
    TextView defaultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        defaultTextView = (TextView) findViewById(R.id.default_text_view);
        news = new ArrayList<>();

        startProcess();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                startProcess();
            }
        }, 0, 30000);

    }

    private void startProcess() {
        String url = getUrl("stocks");
        adapter = new NewsAdapter(this, news);
        NewsAsyncTask newsAsyncTask = new NewsAsyncTask(url, new NewsAsyncResponse() {
            @Override
            public void processFinish(ArrayList<News> newsList) {
                clearNewsList();
                news.addAll(newsList);
                if (newsList.size() != 0) {
                    defaultTextView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                } else {
                    listView.setVisibility(View.GONE);
                    defaultTextView.setVisibility(View.VISIBLE);
                }
                listView.setAdapter(adapter);
            }
        });
        newsAsyncTask.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News currentObject = news.get(i);
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(currentObject.getUrl())));
            }
        });
    }

    private void clearNewsList() {
        news.clear();
    }

    private String getUrl(String keyword) {
        String url = "http://content.guardianapis.com/search?" +
                "show-fields=thumbnail" +
                "&show-tags=contributor" +
                "&q=" + keyword +
                "&api-key=test";
        return url;
    }


}
