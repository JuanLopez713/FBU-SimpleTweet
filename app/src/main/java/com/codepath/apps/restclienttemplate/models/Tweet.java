package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public Entities entities;
    public String mediaUrl;
    public String media;
    public Boolean hasMedia;
    //empty constructor needed by the parceler library
    public Tweet() {
    }


    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        //Log.i("FULLJSON: ", jsonObject.toString());
        Tweet tweet = new Tweet();

        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        if(jsonObject.has("extended_entities")){
            tweet.media = jsonObject.getJSONObject("extended_entities").getJSONArray("media").getString(0);

            try {
                JSONObject mediaObject = new JSONObject(tweet.media);
                tweet.mediaUrl = mediaObject.getString("media_url");
                tweet.hasMedia = true;
                Log.i("TWEET", tweet.mediaUrl);
            }catch (JSONException err){
                Log.d("Error", err.toString());
            }
        }else{
            tweet.hasMedia = false;
        }

       // Log.i("TWEET", tweet.media.toString());
        // if(jsonObject.getString("media_url")!= null) {
        //     tweet.media = jsonObject.getString("media_url");
        // }
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));

        }
        return tweets;
    }
}
