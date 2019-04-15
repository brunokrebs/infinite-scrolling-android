package com.auth0.todo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class DiffUtilCallback extends DiffUtil.ItemCallback<JobModel> {

    @Override
    public boolean areItemsTheSame(@NonNull JobModel oldItem, @NonNull JobModel newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(@NonNull JobModel oldItem, @NonNull JobModel newItem) {

        return oldItem.getTitle().equals(newItem.getTitle()) &&
                oldItem.getUrl().equals(newItem.getUrl()) &&
                oldItem.getCompany().equals(newItem.getCompany()) &&
                oldItem.getCompanyUrl().equals(newItem.getCompanyUrl()) &&
                oldItem.getCreatedAt().equals(newItem.getCreatedAt()) &&
                oldItem.getDescription().equals(newItem.getDescription()) &&
                oldItem.getLocation().equals(newItem.getLocation());
    }
}
