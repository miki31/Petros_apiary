package com.apiary.sch.mykhailo.petros_apiary;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 30.08.2018.
 */

public class TestConectServer {
    private static final String TAG = "tag__ test TestConectServer";

//    private static final String API_KEY = "461cf357f975cff58315772b02c11c81";


    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() +
                        ": with " + urlSpec);
            }

            int byteRead = 0;
            byte[] buffer = new byte[1024];
            while ((byteRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, byteRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }


    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    @SuppressLint("LongLogTag")
    public List<TestRemind> fetchItems() {

        List<TestRemind> items = new ArrayList<>();

        try {
//             https://api.flickr.com/services/rest/
//             ?
//             method=flickr.photos.getRecent&api_key=461cf357f975cff58315772b02c11c81
//             &
//             format=json
//             &
//             nojsoncallback=1
            String url = Uri.parse("http://172.16.10.5:8080/reminders/")
                    .buildUpon()
                    .build()
                    .toString();
            String jsonString = getUrlString(url);

            Log.i(TAG, "Received JSON: " + jsonString);

            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return items;
    }

    @SuppressLint("LongLogTag")
    private void parseItems(List<TestRemind> items, JSONObject jsonBody)
            throws JSONException {

//        JSONObject jsonObject = jsonBody.getJSONObject("");
        JSONArray jsonArray = jsonBody.getJSONArray("");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject photoJsonObject = jsonArray.getJSONObject(i);

            Log.i(TAG,"method: parseItems ==> " + photoJsonObject.toString());

            TestRemind item = new TestRemind();
            item.setId(photoJsonObject.getString("id"));
            item.setTitle(photoJsonObject.getString("title"));
            item.setDate(photoJsonObject.getString("remind_date"));

            if (!photoJsonObject.has("url_s")){
                continue;
            }

            items.add(item);
        }
    }

}
