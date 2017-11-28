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
        holder.titleView.setText(info.getTitle());
        holder.sectionView.setText(info.getSection());
        holder.dateView.setText(info.getWebPublicationDate());
        holder.authorView.setText(info.getAuthor());

        if (info.getWebPublicationDate().trim().equals("")) {
            holder.dateView.setVisibility(View.GONE);
        }
        if (info.getAuthor().trim().equals("")) {
            holder.authorView.setVisibility(View.GONE);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleView;
        private TextView sectionView;
        private TextView dateView;
        private TextView authorView;

        private ViewHolder(View view) {
            super(view);
            titleView = (TextView) view.findViewById(R.id.title);
            sectionView = (TextView) view.findViewById(R.id.section);
            dateView = (TextView) view.findViewById(R.id.date);
            authorView = (TextView) view.findViewById(R.id.author);

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