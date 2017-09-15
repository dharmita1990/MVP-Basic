package com.dkb.mvpbasic.data.source.remote;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.dkb.mvpbasic.data.Task;
import com.dkb.mvpbasic.data.source.TasksDataSource;
import com.google.common.collect.Lists;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by User on 01-09-2017.
 */

public class TasksRemoteDataSource implements TasksDataSource {
    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;

    private final static Map<String, Task> TASKS_SERVICE_DATA;
    private static TasksRemoteDataSource INSTANCE;

    static {
        TASKS_SERVICE_DATA = new LinkedHashMap<>(2);
//        addTask("Build tower in Pisa", "Ground looks good, no foundation work required.");
//        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!");
    }

    // Prevent direct instantiation.
    private TasksRemoteDataSource(@NonNull Context context) {
    }

    public static TasksRemoteDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new TasksRemoteDataSource(context);
        }
        return INSTANCE;
    }

    private static void addTask(String title, String description) {
        Task newTask = new Task(title, description);
        TASKS_SERVICE_DATA.put(newTask.getId(), newTask);
    }

    @Override
    public void getTasks(@NonNull final LoadTasksCallback callback) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onTasksLoaded(Lists.newArrayList(TASKS_SERVICE_DATA.values()));
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void getTask(@NonNull String taskId, @NonNull final GetTaskCallback callback) {
        final Task task = TASKS_SERVICE_DATA.get(taskId);

        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onTaskLoaded(task);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void saveTask(@NonNull Task task) {
        TASKS_SERVICE_DATA.put(task.getId(), task);
    }
}
