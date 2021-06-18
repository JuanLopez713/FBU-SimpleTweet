package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.lang.reflect.Array;

@Parcel
public class Entities {

    public Media media;
    public String profileImageUrl;

    public Entities() {

    }

    public static Entities fromJson(JSONObject jsonObject) throws JSONException {
        Entities entities = new Entities();
        if (jsonObject.has("media")) {
            Media mediaArray = Media.fromJson(jsonObject.getJSONObject("media"));
            Log.i("Entities", mediaArray.toString());
          // entities.media = mediaArray(0).("media_url");
        }

        // entities.mediaUrl = jsonObject.getString("media_url");

        //  media.screenName = jsonObject.getString("screen_name");
        //   media.profileImageUrl = jsonObject.getString("profile_image_url_https");
        return entities;
    }

}