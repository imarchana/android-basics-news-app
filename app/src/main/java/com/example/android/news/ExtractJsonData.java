package com.example.android.news;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ExtractJsonData {

    private String mJsonData;

    public ExtractJsonData(String jsonData) {
        mJsonData = jsonData;
    }

    public ArrayList<News> extractNewsJsonData() {
        ArrayList<News> news = new ArrayList<>();
        try {
            JSONObject baseObject = new JSONObject(mJsonData);
            JSONObject responseObject = baseObject.getJSONObject("response");
            JSONArray resultsArray = responseObject.getJSONArray("results");
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject arrayObject = resultsArray.optJSONObject(i);
                String title = arrayObject.optString("webTitle");
                String url = arrayObject.optString("webUrl");
                JSONObject fieldsObject = arrayObject.optJSONObject("fields");
                String thumbnailImage = "";
                //Thumbnail
                try
                {
                    thumbnailImage = fieldsObject.getString("thumbnail");
                }
                catch (JSONException e)
                {
                    thumbnailImage = "NA";
                }
                //String thumbnailImage = fieldsObject.optString("thumbnail");
                JSONArray tagsArray = arrayObject.optJSONArray("tags");
                String author = "";
                JSONObject tagsArrayObject;
                try
                {
                    for (int j = 0; j < tagsArray.length(); j++) {
                        tagsArrayObject = tagsArray.optJSONObject(j);
                        if (j > 0) {
                            author += ", ";
                        }
                        author += tagsArrayObject.optString("webTitle");
                    }
                }
                catch (Exception e)
                {
                    author = "N/A";
                }
                if(author == "")
                {
                    author = "N/A";
                }
                news.add(new News(thumbnailImage, title, "Author : " + author, url));
            }
        } catch (JSONException e) {
            Log.e("ExtractJsonData", "Problem in extracting Data from JSON !!!");
        }
        return news;
    }

}
