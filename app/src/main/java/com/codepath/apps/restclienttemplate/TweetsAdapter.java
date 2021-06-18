package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;


public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    Context context;
    List<Tweet> tweets;


    //Pass in the context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }
    //For each row, inflate the layout

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);

        return new ViewHolder(view);
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
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        ImageView ivMediaImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            ivMediaImage = itemView.findViewById(R.id.ivMediaImage);
        }

        public void bind(Tweet tweet) {

            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            int radius = 30;
            int margin = 5;
            Glide.with(context).load(tweet.user.profileImageUrl).fitCenter().transform(new RoundedCornersTransformation(radius, margin)).into(ivProfileImage);


            if (tweet.hasMedia == true){
                ivMediaImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(tweet.mediaUrl).fitCenter().transform(new RoundedCornersTransformation(radius, margin)).into(ivMediaImage);
               // Log.i("TweetsAdapter", "loaded image!" + tweet.body + " "+tweet.mediaUrl);

            }else{
                ivMediaImage.setVisibility(View.GONE);
            }
            //
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

}
