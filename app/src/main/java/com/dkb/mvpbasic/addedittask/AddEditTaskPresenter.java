package com.dkb.mvpbasic.addedittask;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dkb.mvpbasic.data.Task;
import com.dkb.mvpbasic.data.TasksRepository;
import com.dkb.mvpbasic.data.source.TasksDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by User on 13-09-2017.
 */

public class AddEditTaskPresenter implements AddEditTaskContract.Presenter {


    private final TasksRepository mTasksRepository;
    private final AddEditTaskContract.View mTasksView;
    private final String mTaskID;

    public AddEditTaskPresenter(@NonNull AddEditTaskContract.View TasksView, @Nullable String taskID, @NonNull TasksRepository tasksRepository) {
        mTasksRepository = checkNotNull(tasksRepository, "tasksRepository cannot be null");
        mTasksView = checkNotNull(TasksView, "tasksView cannot be null!");
        mTasksView.setPresenter(this);
        mTaskID = taskID;
    }

    @Override
    public void save(String title, String description) {
        Task task = new Task(title, description);
        mTasksRepository.saveTask(task);
        mTasksView.showTasksList();
    }

    @Override
    public void getTask() {
        mTasksRepository.getTask(mTaskID, new TasksDataSource.GetTaskCallback() {
            @Override
            public void onTaskLoaded(Task task) {
                if (mTasksView.isActive()) {
                    mTasksView.setTitle(task.getTitle());
                    mTasksView.setDescription(task.getDescription());
                }
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }

    @Override
    public void start() {
        if (mTaskID != null)
            getTask();
    }
}
