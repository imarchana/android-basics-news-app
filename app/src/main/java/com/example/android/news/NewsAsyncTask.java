package com.example.android.news;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class NewsAsyncTask extends AsyncTask<Void, Void, ArrayList<News>> {

    private NewsAsyncResponse delegate = null;
    private String mUrl;

    public NewsAsyncTask(String urlString, NewsAsyncResponse newsAsyncResponse) {
        mUrl = urlString;
        delegate = newsAsyncResponse;
    }

    @Override
    protected ArrayList<News> doInBackground(Void... voids) {
        URL url = createUrl(mUrl);
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("BookAsyncTask", "Error retrieving data from JSON !!!");
        }
        ExtractJsonData extractJsonData = new ExtractJsonData(jsonResponse);
        return extractJsonData.extractNewsJsonData();
    }

    @Override
    protected void onPostExecute(ArrayList<News> news) {
        if (news == null) {
            return;
        }
        delegate.processFinish(news);
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("NewsAsyncTask", "Error reading Stream !!!");
            }
        } catch (IOException e) {
            Log.e("NewsAsyncTask", "Error IOException !!!");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
                ;
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private URL createUrl(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e("NewsAsyncTask", "Error creating URL Object !!!");
        }
        return url;
    }


}
