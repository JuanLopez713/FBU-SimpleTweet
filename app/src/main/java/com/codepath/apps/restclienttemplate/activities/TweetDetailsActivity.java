package com.codepath.apps.restclienttemplate.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetailsActivity extends AppCompatActivity {
    private static final String TAG = "TweetDetailsActivity";
    Tweet tweet;
    TextView tvTweetBody;
    String body;
    ImageView ivTweetProfile;
    TextView tvTweetScreenName;
    ImageView ivTweetMediaImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        tvTweetBody = (TextView) findViewById(R.id.tvTweetBody);
        ivTweetProfile = (ImageView) findViewById(R.id.ivTweetProfile);
        tvTweetScreenName = (TextView) findViewById(R.id.tvTweetScreenName);
        ivTweetMediaImage = (ImageView) findViewById(R.id.ivTweetMediaImage);

        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra("tweetContents"));
        Log.d(TAG, "onCreate: " + tweet);


        tvTweetBody.setText(tweet.body);
        tvTweetScreenName.setText(tweet.user.screenName);

        int radius = 30;
        int margin = 5;
        Glide.with(this).load(tweet.user.profileImageUrl).fitCenter().transform(new RoundedCornersTransformation(radius, margin)).into(ivTweetProfile);
        //itemView.setOnClickListener(this);

        if (tweet.hasMedia == true) {
            ivTweetMediaImage.setVisibility(View.VISIBLE);
            Glide.with(this).load(tweet.mediaUrl).centerCrop().transform(new RoundedCornersTransformation(radius, margin)).into(ivTweetMediaImage);
            // Log.i("TweetsAdapter", "loaded image!" + tweet.body + " "+tweet.mediaUrl);

        } else {
            ivTweetMediaImage.setVisibility(View.GONE);
        }


    }
}