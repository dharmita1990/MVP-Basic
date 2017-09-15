package com.dkb.mvpbasic.task;


import com.dkb.mvpbasic.base.BasePresenter;
import com.dkb.mvpbasic.base.BaseView;
import com.dkb.mvpbasic.data.Task;

import java.util.List;

/**
 * Created by User on 04-09-2017.
 */

public interface TasksContract {

    interface View extends BaseView<Presenter> {
        void showTasks(List<Task> tasks);

        void showNoTasks();

        void showSuccessfullySavedMessage();

        void showLoadingTasksError();

        boolean isActive();

        void showProgress(boolean show);

        void showAddTask();
    }

    interface Presenter extends BasePresenter {

        void loadTask();


        void addNewTask();

        void result(int requestCode, int resultCode);
    }
}
