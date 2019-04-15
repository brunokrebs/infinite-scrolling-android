package com.auth0.todo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListAdapter listAdapter = new ListAdapter(new DiffUtilCallback());

        RecyclerView recyclerView = findViewById(R.id.recycler_view_jobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

        PagedList.Config config = new PagedList.Config.Builder()
                .setPageSize(15)
                .setEnablePlaceholders(false)
                .build();

        DataSource.Factory<String,JobModel> factory = new DataSource.Factory<String,JobModel>(){
            @NonNull
            @Override
            public DataSource<String, JobModel> create() {
                return new GitHubDataSource();
            }
        };

        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder<>(factory, config);
        LiveData<PagedList> listLiveData = livePagedListBuilder.build();

        listLiveData.observe(this, new Observer<PagedList>() {
            @Override
            public void onChanged(PagedList pagedList) {
                listAdapter.submitList(pagedList);
            }
        });


    }

}
