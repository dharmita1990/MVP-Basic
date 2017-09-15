package com.dkb.mvpbasic.task;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dkb.mvpbasic.ActivityUtils;
import com.dkb.mvpbasic.R;
import com.dkb.mvpbasic.data.TasksRepository;
import com.dkb.mvpbasic.data.source.local.TasksLocalDataSource;
import com.dkb.mvpbasic.data.source.remote.TasksRemoteDataSource;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private TasksPresenter mTasksPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);
        // Set up the toolbar.
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Task");

        TasksFragment tasksFragment =
                (TasksFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (tasksFragment == null) {
            tasksFragment = TasksFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), tasksFragment, R.id.contentFrame);
        }
        TasksRepository.getInstance(TasksRemoteDataSource.getInstance(this),
                TasksLocalDataSource.getInstance(this));
        // Create the presenter
        mTasksPresenter = new TasksPresenter(
                tasksFragment, TasksRepository.getInstance(TasksRemoteDataSource.getInstance(this),
                TasksLocalDataSource.getInstance(this)));
    }


}
