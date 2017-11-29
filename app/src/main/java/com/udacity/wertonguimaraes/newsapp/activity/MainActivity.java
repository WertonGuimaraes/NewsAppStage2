package com.udacity.wertonguimaraes.newsapp.activity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.udacity.wertonguimaraes.newsapp.R;
import com.udacity.wertonguimaraes.newsapp.adapter.InfoListAdapter;
import com.udacity.wertonguimaraes.newsapp.async.URLAsyncTaskLoader;
import com.udacity.wertonguimaraes.newsapp.model.Info;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Info>>,
        SwipeRefreshLayout.OnRefreshListener{

    private static int LOADER_ID = 0;

    private RecyclerView mRvItem;
    private InfoListAdapter mNewsAdapter;
    private SwipeRefreshLayout nSwipe;
    private SwipeRefreshLayout nSwipeEmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRvItem = (RecyclerView) findViewById(R.id.recycler_view);
        nSwipe = (SwipeRefreshLayout) findViewById(R.id.refresh);
        nSwipeEmpty = (SwipeRefreshLayout) findViewById(R.id.refresh_empty);

        nSwipeEmpty.setVisibility(View.GONE);
        nSwipe.setVisibility(View.GONE);

        nSwipe.setOnRefreshListener(this);
        nSwipeEmpty.setOnRefreshListener(this);

        startOrRestartLoaderCustom(getSupportLoaderManager().initLoader(LOADER_ID, null, this));
    }

    @Override
    public Loader<List<Info>> onCreateLoader(int id, Bundle args) {
        return new URLAsyncTaskLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Info>> loader, List<Info> data) {
        nSwipe.setRefreshing(false);
        nSwipeEmpty.setRefreshing(false);

        mRvItem.setLayoutManager(new LinearLayoutManager(this));
        if (data != null && data.size() > 0) {
            nSwipeEmpty.setVisibility(View.GONE);
            nSwipe.setVisibility(View.VISIBLE);
            mNewsAdapter = new InfoListAdapter(data);
            mRvItem.setAdapter(mNewsAdapter);
        } else {
            nSwipeEmpty.setVisibility(View.VISIBLE);
            nSwipe.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Info>> loader) {
    }

    @Override
    public void onRefresh() {
        startOrRestartLoaderCustom(getSupportLoaderManager().restartLoader(LOADER_ID, null, this));
    }

    private void startOrRestartLoaderCustom(Loader<List<Info>> loaderManager) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            nSwipeEmpty.setVisibility(View.GONE);
            nSwipe.setVisibility(View.VISIBLE);
            loaderManager.forceLoad();
        } else {
            nSwipe.setRefreshing(false);
            nSwipeEmpty.setRefreshing(false);
            nSwipeEmpty.setVisibility(View.VISIBLE);
            nSwipe.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), getString(R.string.no_connection), Toast.LENGTH_LONG).show();
        }
    }
}