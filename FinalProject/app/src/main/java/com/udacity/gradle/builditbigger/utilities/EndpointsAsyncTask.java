package com.udacity.gradle.builditbigger.utilities;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask
        extends AsyncTask<Void, Void, String> {
    private static MyApi sMyApi = null;
    private final IEndpointsAsyncTaskCallback mCallback;

    //Constructor
    public EndpointsAsyncTask(IEndpointsAsyncTaskCallback callback){
        mCallback = callback;
    }

    /*
     * Adapted from https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/77e9910911d5412e5efede5fa681ec105a0f02ad/HelloEndpoints#2-connecting-your-android-app-to-the-backend
     */
    @Override
    protected String doInBackground(Void... params) {
        //Sets up the connection to the API
        if(sMyApi == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            sMyApi = builder.build();
        }

        try {
            //Requests a joke
            return sMyApi.getJoke()
                    .execute()
                    .getData();
        }
        catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        super.onPostExecute(joke);

        //Sends the joke back to the appropriate Activity
        mCallback.sendJoke(joke);
    }
}