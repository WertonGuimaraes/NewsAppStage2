package com.udacity.wertonguimaraes.newsapp.async;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;

import com.udacity.wertonguimaraes.newsapp.R;
import com.udacity.wertonguimaraes.newsapp.model.Info;
import com.udacity.wertonguimaraes.newsapp.request.Request;
import com.udacity.wertonguimaraes.newsapp.util.Parse;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import static android.content.ContentValues.TAG;

public class URLAsyncTaskLoader extends AsyncTaskLoader<List<Info>> {
    private Context mContext;
    private SharedPreferences mPreferences;

    public URLAsyncTaskLoader(Context context) {
        super(context);
        mContext = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public List<Info> loadInBackground() {
        List<Info> responseListInfo = null;

        try {
            String subject = mPreferences.getString(mContext.getString(R.string.key_subject_name), "");
            String orderBy = mPreferences.getString(mContext.getString(R.string.key_order_by), mContext.getString(R.string.value_relevance));
            String pageSize = mPreferences.getString(mContext.getString(R.string.key_page_size), mContext.getString(R.string.value_default_page_size));
            Request request = new Request(subject, orderBy, pageSize);
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
