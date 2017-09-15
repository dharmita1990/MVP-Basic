package com.dkb.mvpbasic.task;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dkb.mvpbasic.R;
import com.dkb.mvpbasic.addedittask.AddEditTaskActivity;
import com.dkb.mvpbasic.addedittask.AddEditTaskFragment;
import com.dkb.mvpbasic.data.Task;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by User on 06-09-2017.
 */

public class TasksFragment extends Fragment implements TasksContract.View, TasksAdapter.CountryInteraction {
    private TasksContract.Presenter mPresenter;
    private TasksAdapter mListAdapter;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.txtEmpty)
    TextView txtEmpty;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    private Unbinder unbinder;
    private FloatingActionButton fab;

    public TasksFragment() {
    }


    public static TasksFragment newInstance() {
        return new TasksFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_task, container, false);
        unbinder = ButterKnife.bind(this, root);

        SetAdapter();
        mRecyclerView.setAdapter(mListAdapter);

        // Set up floating action button
        fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_task);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewTask();
            }
        });
        setHasOptionsMenu(true);
        return root;
    }

    private void SetAdapter() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mListAdapter = new TasksAdapter(new ArrayList<Task>(0), this);
        mRecyclerView.setAdapter(mListAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showProgress(boolean show) {
        if (show) {

            progressBar.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
        } else {
            fab.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoadingTasksError() {
        showMessage(getString(R.string.loading_tasks_error));
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void showTasks(List<Task> tasks) {
        mListAdapter.replaceData(tasks);

        mRecyclerView.setVisibility(View.VISIBLE);
        txtEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showNoTasks() {

        mRecyclerView.setVisibility(View.GONE);
        txtEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuccessfullySavedMessage() {
        showMessage(getString(R.string.successfully_saved_task_message));
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void showAddTask() {
        Intent intent = new Intent(getContext(), AddEditTaskActivity.class);
        startActivityForResult(intent, AddEditTaskActivity.REQUEST_ADD_TASK);
    }

    @Override
    public void setPresenter(TasksContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void OnListInteractionListener(Task task) {
        Intent intent = new Intent(getContext(), AddEditTaskActivity.class);
        intent.putExtra(AddEditTaskFragment.ARGUMENT_EDIT_TASK_ID, task.getId());
        startActivityForResult(intent, AddEditTaskActivity.REQUEST_EDIT_TASK);
    }
}
