package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Media {
    public String mediaUrl;
    public String media;
    public String profileImageUrl;

    public Media() {

    }

    public static Media fromJson(JSONObject jsonObject) throws JSONException {
        Media media = new Media();
        media.mediaUrl = jsonObject.getString("media_url");
       Log.i("MediaLOGGED", media.mediaUrl);
//        entities.mediaUrl = jsonObject.getString("media_url");

        //  media.screenName = jsonObject.getString("screen_name");
        //   media.profileImageUrl = jsonObject.getString("profile_image_url_https");
        return media;
    }
}
