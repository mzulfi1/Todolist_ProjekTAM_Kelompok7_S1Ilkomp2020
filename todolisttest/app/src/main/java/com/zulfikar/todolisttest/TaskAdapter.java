package com.zulfikar.todolisttest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private ArrayList<TaskModel> taskList;
    private MainActivity mainActivity;
    private DatabaseHandler db;

    public SimpleDateFormat dateFormat = new SimpleDateFormat("EE dd MM yyyy");
    public SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd-M-yyyy");
    public SimpleDateFormat timeFormat = new SimpleDateFormat("HH mm ss");
    public SimpleDateFormat inputTimeFormat = new SimpleDateFormat("HH:mm");
    Date time = null;
    Date date = null;
    String outputDateString = null;
    String outputTimeString = null;

    public TaskAdapter(DatabaseHandler db, MainActivity mainActivity){

        this.db = db;
        this.mainActivity = mainActivity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(view);
    }

    public void setTask(ArrayList<TaskModel> taskList){
        this.taskList = taskList;
        notifyDataSetChanged();
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(taskList.get(position).getTitle());
        try{
            time = inputTimeFormat.parse(taskList.get(position).getTime());
            outputTimeString = timeFormat.format(time);

            String[] item2 = outputTimeString.split(" ");
            String hour = item2[0];
            String minutes = item2[1];

            holder.hour.setText(hour);
            holder.minute.setText(minutes);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            date = inputDateFormat.parse(taskList.get(position).getDate());
            outputDateString = dateFormat.format(date);

            String[] items1 = outputDateString.split(" ");
            String day = items1[0];
            String dd = items1[1];
            String month = items1[2];

            holder.day.setText(dd);
            holder.month.setText(month);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getItemCount(){
        return taskList.size();
    }

    public Context getContext(){
        return mainActivity;
    }

    public void deleteItem(int position){
        db.deleteTask(taskList.get(position).getId());
        taskList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position){
        Bundle bundle = new Bundle();
        bundle.putInt("id", taskList.get(position).getId());
        bundle.putString("title", taskList.get(position).getTitle());
        bundle.putString("date", taskList.get(position).getDate());
        bundle.putString("time", taskList.get(position).getTime());
        AddTaskFragment fragment = new AddTaskFragment();
        fragment.setArguments(bundle);
        fragment.show(mainActivity.getSupportFragmentManager(), AddTaskFragment.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, day, month, hour, minute;
        ViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.tv_tittle);
            day = view.findViewById(R.id.tv_day);
            month = view.findViewById(R.id.tv_month);
            hour = view.findViewById(R.id.tv_hour);
            minute = view.findViewById(R.id.tv_minutes);
        }
    }
}
