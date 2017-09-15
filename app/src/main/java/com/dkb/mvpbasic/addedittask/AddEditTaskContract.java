package com.dkb.mvpbasic.addedittask;


import com.dkb.mvpbasic.base.BasePresenter;
import com.dkb.mvpbasic.base.BaseView;

/**
 * Created by User on 06-09-2017.
 */

public interface AddEditTaskContract {
    interface View extends BaseView<Presenter> {
        void setTitle(String title);

        void setDescription(String description);

        void showTasksList();

        boolean isActive();


    }

    interface Presenter extends BasePresenter {
        void save(String title, String description);

        void getTask();
    }
}
