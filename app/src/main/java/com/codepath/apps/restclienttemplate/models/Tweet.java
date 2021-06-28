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
    private static final String TAG = "Tweet";
    public String body;
    public String createdAt;
    public User user;
    public Entities entities;
    public String mediaUrl;
    public String media;
    public Boolean hasMedia;
    public Long id;
    public Boolean favorited;
    public String tweetID;
    public Long favorite_count;
    public Long retweet_count;
    //empty constructor needed by the parceler library
    public Tweet() {
    }


    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        //Log.i("FULLJSON: ", jsonObject.toString());
        Tweet tweet = new Tweet();
       // Log.d(TAG, "fromJson: " + jsonObject.toString(50));
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.id = jsonObject.getLong("id");
        tweet.tweetID = jsonObject.getString("id_str");
        tweet.favorited = jsonObject.getBoolean("favorited");
        tweet.favorite_count = jsonObject.getLong("favorite_count");
        tweet.retweet_count = jsonObject.getLong("retweet_count");
       // Log.d(TAG, "fromJson: " + tweet.retweet_count);
        if (jsonObject.has("extended_entities")) {
            tweet.media = jsonObject.getJSONObject("extended_entities").getJSONArray("media").getString(0);

            try {
                JSONObject mediaObject = new JSONObject(tweet.media);
                tweet.mediaUrl = mediaObject.getString("media_url");
                tweet.hasMedia = true;
               // Log.i("TWEET", .toString());
            } catch (JSONException err) {
                Log.d("Error", err.toString());
            }
        } else {
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

//    public String getBody() {
//        return body;
//    }

//    public String getCreatedAt() {
//        return createdAt;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public Entities getEntities() {
//        return entities;
//    }
//
//    public String getMediaUrl() {
//        return mediaUrl;
//    }
//
//    public String getMedia() {
//        return media;
//    }
//
//    public Boolean getHasMedia() {
//        return hasMedia;
//    }
}
