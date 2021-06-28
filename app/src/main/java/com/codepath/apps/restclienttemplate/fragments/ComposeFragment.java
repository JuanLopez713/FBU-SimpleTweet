package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterApp;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComposeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComposeFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "screenName";
    private static final String ARG_PARAM2 = "isReply";
    public static final int MAX_TWEET_LENGTH = 280;
    private static final String TAG = "ComposeFragment";

    // TODO: Rename and change types of parameters
    private String username;
    private Boolean isReply;
    EditText etCompose;
    Button btnTweet;

    TwitterClient client;

    public ComposeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param username Parameter 1.
     * @param isReply Parameter 2.
     * @return A new instance of fragment ComposeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComposeFragment newInstance(String username, Boolean isReply) {
        ComposeFragment fragment = new ComposeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, username);
        args.putBoolean(ARG_PARAM2, isReply);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            username = getArguments().getString(ARG_PARAM1);
            isReply = getArguments().getBoolean(ARG_PARAM2);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getContext();

        client = TwitterApp.getRestClient(context);
        etCompose = view.findViewById(R.id.etComposeFrag);
        btnTweet = view.findViewById(R.id.btnTweetFrag);
        getUser();
        //Set click listener on button
        btnTweet.setOnClickListener(v -> {
            String tweetContent = etCompose.getText().toString();
            if (tweetContent.isEmpty()) {
                Toast.makeText(context, "Sorry, your tweet cannot be empty", Toast.LENGTH_LONG).show();
            }
            if (tweetContent.length() > MAX_TWEET_LENGTH) {
                Toast.makeText(context, "Sorry, your tweet is too long", Toast.LENGTH_LONG).show();
            }
            Toast.makeText(context, tweetContent, Toast.LENGTH_LONG).show();
            client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Headers headers, JSON json) {
                    Log.i(TAG, "onSuccess to publish tweet");
                    try {
                        Tweet tweet = Tweet.fromJson(json.jsonObject);
                        Log.i(TAG, "Published tweet says: "+tweet.body);
                      //  Intent intent = new Intent();
                       // intent.putExtra("tweet", Parcels.wrap(tweet));
                        //set results code and bundles data for the response
                       // setResult(RESULT_OK, intent);
                       // finish(); //closes the activity

                        getDialog().dismiss();

                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                    Log.e(TAG, "onFailure to publish tweet", throwable);
                }
            });
        });
    }

    private void getUser() {
        if(isReply){
            String atUser = String.format("@%s ", username);
            etCompose.setText(atUser);
            etCompose.setSelection(etCompose.getText().length());

        }
    }
}