package com.dkb.mvpbasic.addedittask;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.dkb.mvpbasic.ActivityUtils;
import com.dkb.mvpbasic.R;
import com.dkb.mvpbasic.data.TasksRepository;
import com.dkb.mvpbasic.data.source.local.TasksLocalDataSource;
import com.dkb.mvpbasic.data.source.remote.TasksRemoteDataSource;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 06-09-2017.
 */
public class AddEditTaskActivity extends AppCompatActivity {
    public static final int REQUEST_ADD_TASK = 1;
    public static final int REQUEST_EDIT_TASK = 2;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private AddEditTaskPresenter mTasksPresenter;
    private String taskID;
    private ActionBar mActionBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        ButterKnife.bind(this);
        // Set up the toolbar.
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);

        taskID = getIntent().getStringExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID);
        setToolbarTitle(taskID);

        AddEditTaskFragment tasksFragment =
                (AddEditTaskFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (tasksFragment == null) {
            tasksFragment = AddEditTaskFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), tasksFragment, R.id.contentFrame);
        }


        TasksRepository.getInstance(TasksRemoteDataSource.getInstance(this),
                TasksLocalDataSource.getInstance(this));
        // Create the presenter
        mTasksPresenter = new AddEditTaskPresenter(
                tasksFragment, taskID, TasksRepository.getInstance(TasksRemoteDataSource.getInstance(this),
                TasksLocalDataSource.getInstance(this)));
    }

    private void setToolbarTitle(@Nullable String taskId) {
        if (taskId == null) {
            mActionBar.setTitle(R.string.add_task);
        } else {
            mActionBar.setTitle(R.string.edit_task);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
