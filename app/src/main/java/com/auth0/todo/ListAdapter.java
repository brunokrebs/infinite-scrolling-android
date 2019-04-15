package com.auth0.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ListAdapter extends PagedListAdapter<JobModel,ViewHolder> {

    ListAdapter(@NonNull DiffUtil.ItemCallback<JobModel> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        JobModel currentItem = getItem(position);
        TextView titleTextView = holder.itemView.findViewById(R.id.job_title);
        titleTextView.setText(currentItem.getId());

    }
}

class ViewHolder extends RecyclerView.ViewHolder {

    ViewHolder(@NonNull View itemView) {
        super(itemView);
    }

}
