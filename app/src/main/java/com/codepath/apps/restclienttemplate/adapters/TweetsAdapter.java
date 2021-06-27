package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;


public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    Context context;
    List<Tweet> tweets;
    private static final String TAG = "TweetsAdapter";
    private OnTweetListener onTweetListener;

    //Pass in the context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets, OnTweetListener onTweetListener) {
        this.context = context;
        this.tweets = tweets;
        this.onTweetListener = onTweetListener;
    }
    //For each row, inflate the layout

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);

        return new ViewHolder(view, onTweetListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get the data at position
        Tweet tweet = tweets.get(position);
        //Bind the tweet with the view holder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }


    //Bind values based on the position of the element

    //Define a ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        ImageView ivMediaImage;
        OnTweetListener onTweetListener;
        ImageButton likeButton;
        TwitterClient twitterClient;

        public ViewHolder(@NonNull View itemView, OnTweetListener onTweetListener) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            ivMediaImage = itemView.findViewById(R.id.ivMediaImage);
            likeButton = itemView.findViewById(R.id.likeBtn);
//            Bitmap bitmapLike = likeButton.getLikeBitmap();
//            Bitmap bitmapUnlike = likeButton.getUnlikeBitmap();
//            likeButton.setLikeIcon(Bitmap);
            //likeButton.setColorFilter(ContextCompat.getColor(context, R.color.COLOR_YOUR_COLOR), android.graphics.PorterDuff.Mode.MULTIPLY);

            this.onTweetListener = onTweetListener;
        }

        public void bind(Tweet tweet) {
            twitterClient = TwitterApp.getRestClient(context);
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            int radius = 30;
            int margin = 5;
            Glide.with(context).load(tweet.user.profileImageUrl).fitCenter().transform(new RoundedCornersTransformation(radius, margin)).into(ivProfileImage);
            itemView.setOnClickListener(this);

            if (tweet.hasMedia == true) {
                ivMediaImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(tweet.mediaUrl).override(1000).centerCrop().transform(new RoundedCornersTransformation(radius, margin)).into(ivMediaImage);
                // Log.i("TweetsAdapter", "loaded image!" + tweet.body + " "+tweet.mediaUrl);
//ivMediaImage.setMaxHeight(50);
            } else {
                ivMediaImage.setVisibility(View.GONE);
            }

            if (tweet.favorited == true) {
                likeButton.setBackgroundResource(R.drawable.ic_cards_heart);
            } else {
                likeButton.setBackgroundResource(R.drawable.ic_heart_outline);
            }

            likeButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tweet.favorited == false) {


                        //Log.d(TAG, "onClick: " + tweet.tweetID);
                        // https://api.twitter.com/1.1/favorites/create.json?id=TWEET_ID_TO_FAVORITE
                        twitterClient.likeTweet(tweet.tweetID, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                likeButton.setBackgroundResource(R.drawable.ic_cards_heart);
                                tweet.favorited = true;

                                Log.d(TAG, "onSuccess: ");
                            }

                            @Override
                            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                                Log.d(TAG, "onFailure" + response, throwable);
                            }
                        });
                    } else{
                        twitterClient.unLikeTweet(tweet.tweetID, new JsonHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Headers headers, JSON json) {
                                likeButton.setBackgroundResource(R.drawable.ic_heart_outline);
                                tweet.favorited = false;

                                Log.d(TAG, "onSuccess: ");
                            }

                            @Override
                            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                                Log.d(TAG, "onFailure" + response, throwable);
                            }
                        });
                    }

                }
            });
            //
        }


        @Override
        public void onClick(View v) {
            onTweetListener.onTweetClick(getAdapterPosition());
        }
    }
    /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        tweets.addAll(list);
        // populateHomeTimeline();
        notifyDataSetChanged();
    }

    public interface OnTweetListener {
        void onTweetClick(int position);
    }

    public void tweetLikeHandler() {


    }


}
