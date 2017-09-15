package com.dkb.mvpbasic.task;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dkb.mvpbasic.R;
import com.dkb.mvpbasic.data.Task;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by User on 06-09-2017.
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder> {

    private List<Task> mValues;
    private CountryInteraction mListener;

    public TasksAdapter(List<Task> countries,
                        CountryInteraction listener) {
        setList(countries);
        mListener = listener;
    }

    private void setList(List<Task> countries) {
        mValues = checkNotNull(countries);
    }

    public void replaceData(List<Task> tasks) {
        setList(tasks);
        notifyDataSetChanged();
    }

    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.tasklist_item, parent, false);
        return new ViewHolder(view, mListener);

    }

    @Override
    public void onBindViewHolder(TasksAdapter.ViewHolder holder, int position) {
        holder.mTxtCountryName.setText(mValues.get(position).getTitle());
        holder.mTxtCountryCapital.setText(mValues.get(position).getDescription());
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;

        public CountryInteraction mListener;
        @BindView(R.id.txt_country_name)
        public TextView mTxtCountryName;

        @BindView(R.id.txt_country_capital)
        public TextView mTxtCountryCapital;


        public ViewHolder(View view, CountryInteraction Listener) {
            super(view);
            mView = view;
            itemView.setOnClickListener(this);
            mListener = Listener;
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View view) {
            mListener.OnListInteractionListener(mValues.get(getAdapterPosition()));

        }
    }

    public interface CountryInteraction {
        void OnListInteractionListener(Task task);
    }
}

