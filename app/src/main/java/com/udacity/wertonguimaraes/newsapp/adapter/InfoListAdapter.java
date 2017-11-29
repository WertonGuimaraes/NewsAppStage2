package com.udacity.wertonguimaraes.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udacity.wertonguimaraes.newsapp.R;
import com.udacity.wertonguimaraes.newsapp.model.Info;

import java.util.List;

import static android.content.ContentValues.TAG;


public class InfoListAdapter extends RecyclerView.Adapter<InfoListAdapter.ViewHolder> {

    private List<Info> mInfoList;

    public InfoListAdapter(List<Info> infoList) {
        mInfoList = infoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view_items, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return (null != mInfoList ? mInfoList.size() : 0);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Info info = mInfoList.get(position);
        holder.mTitleView.setText(info.getTitle());
        holder.mSectionView.setText(info.getSection());
        holder.mDateView.setText(info.getWebPublicationDate());
        holder.mAuthorView.setText(info.getAuthor());

        if (info.getWebPublicationDate().isEmpty()) {
            holder.mDateView.setVisibility(View.GONE);
        }
        if (info.getAuthor().isEmpty()) {
            holder.mAuthorView.setVisibility(View.GONE);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleView;
        private TextView mSectionView;
        private TextView mDateView;
        private TextView mAuthorView;

        private ViewHolder(View view) {
            super(view);
            mTitleView = (TextView) view.findViewById(R.id.news_title);
            mSectionView = (TextView) view.findViewById(R.id.news_section);
            mDateView = (TextView) view.findViewById(R.id.news_date);
            mAuthorView = (TextView) view.findViewById(R.id.news_author);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            PackageManager packageManager = context.getPackageManager();
            Info info = mInfoList.get(getAdapterPosition());

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(info.getWebUrl()));
            if (browserIntent.resolveActivity(packageManager) != null) {
                context.startActivity(browserIntent);
            } else {
                Log.d(TAG, "No Intent available to handle action");
            }
        }

    }
}