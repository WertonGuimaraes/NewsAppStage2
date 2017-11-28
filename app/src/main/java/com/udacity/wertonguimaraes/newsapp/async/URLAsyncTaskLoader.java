package com.udacity.wertonguimaraes.newsapp.async;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.udacity.wertonguimaraes.newsapp.model.Info;
import com.udacity.wertonguimaraes.newsapp.request.Request;
import com.udacity.wertonguimaraes.newsapp.util.Parse;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import static android.content.ContentValues.TAG;

public class URLAsyncTaskLoader extends AsyncTaskLoader<List<Info>> {


    public URLAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    public List<Info> loadInBackground() {
        List<Info> responseListInfo = null;

        try {
            Request request = new Request();
            String getDataURL = request.getInfoInURL();

            Parse parse;
            parse = new Parse(getDataURL);

            responseListInfo = parse.convertJsonToInfoObjects();
        } catch (MalformedURLException e) {
            Log.e(TAG, "URL error.");
        } catch (IOException e) {
            Log.e(TAG, "I/O error to get data.");
        } catch (JSONException e) {
            Log.e(TAG, "JSON Malformed.");
        }

        return responseListInfo;
    }

}
