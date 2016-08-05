package com.example.android.news;


import java.util.ArrayList;

public interface NewsAsyncResponse {
    public void processFinish(ArrayList<News> news);
}
