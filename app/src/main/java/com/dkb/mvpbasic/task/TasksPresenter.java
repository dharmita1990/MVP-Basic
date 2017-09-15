package com.dkb.mvpbasic.task;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.dkb.mvpbasic.addedittask.AddEditTaskActivity;
import com.dkb.mvpbasic.data.Task;
import com.dkb.mvpbasic.data.TasksRepository;
import com.dkb.mvpbasic.data.source.TasksDataSource;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by User on 06-09-2017.
 */

public class TasksPresenter implements TasksContract.Presenter {

    private final TasksContract.View mTasksView;
    private final TasksRepository mTasksRepository;


    public TasksPresenter(@NonNull TasksContract.View TasksView, @NonNull TasksRepository tasksRepository) {
        mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null");

        mTasksView = checkNotNull(TasksView, "tasksView cannot be null!");
        mTasksView.setPresenter(this);
    }

    @Override
    public void start() {

        if (mTasksView.isActive())
            mTasksView.showProgress(true);
        loadTask();
    }

    @Override
    public void loadTask() {
        mTasksRepository.getTasks(new TasksDataSource.LoadTasksCallback() {
            @Override
            public void onTasksLoaded(List<Task> tasks) {
                List<Task> tasksToShow = new ArrayList<Task>();

                // This callback may be called twice, once for the cache and once for loading
                // the data from the server API, so we check before decrementing, otherwise
                // it throws "Counter has been corrupted!" exception.

                // We filter the tasks based on the requestType
                for (Task task : tasks) {
                    tasksToShow.add(task);
                }
                // The view may not be able to handle UI updates anymore
                if (!mTasksView.isActive()) {
                    return;
                }

                processTasks(tasksToShow);
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore
                if (!mTasksView.isActive()) {
                    return;
                }
                mTasksView.showLoadingTasksError();
            }
        });
    }

    private void processTasks(List<Task> tasks) {
        if (tasks.isEmpty()) {
            // Show a message indicating there are no tasks for that filter type.
            if (mTasksView.isActive()) {
                mTasksView.showProgress(false);
                mTasksView.showNoTasks();
            }
        } else {
            // Show the list of tasks
            if (mTasksView.isActive()) {
                mTasksView.showProgress(false);
                mTasksView.showTasks(tasks);
            }

        }
    }


    @Override
    public void addNewTask() {
        mTasksView.showAddTask();
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if (AddEditTaskActivity.REQUEST_ADD_TASK == requestCode && Activity.RESULT_OK == resultCode) {
            mTasksView.showSuccessfullySavedMessage();
        }
    }


}
