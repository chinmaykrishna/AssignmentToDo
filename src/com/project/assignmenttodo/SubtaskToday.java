package com.project.assignmenttodo;

import java.util.ArrayList;
import java.util.List;

import sqliteHelper.DatabaseHelper;
import sqliteModel.Subtask;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SubtaskToday extends Activity{
	DatabaseHelper db;
	ListView l1;
	todaySubtaskListAdapter sub_adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subtask_today);
		
		db=new DatabaseHelper(getApplicationContext());
		List<Subtask> subtasks= new ArrayList<Subtask>();
		subtasks = db.getTodaySubtask();
		
			
		ArrayList<String> list_name=new ArrayList<String>();
		ArrayList<String> list_date=new ArrayList<String>();
		ArrayList<Integer> list_status=new ArrayList<Integer>();
		for(Subtask subtask:subtasks)
		{
			list_name.add(subtask.getTaskDesc());
			list_date.add(subtask.getTaskDuedate());
			list_status.add(subtask.getTaskStatus());
		}
		
		l1=(ListView)findViewById(R.id.today_task_list);
		l1.setAdapter(new todaySubtaskListAdapter(SubtaskToday.this,subtasks,list_name, list_date, list_status));
	
		
	}
	
	//view holder
		private static class SubtaskViewHolder{
			private CheckBox cb;
			private TextView date;
			
			public SubtaskViewHolder()
			{
				
			}
			public SubtaskViewHolder(CheckBox checkbox, TextView dateview)
			{
				this.cb=checkbox;
				this.date=dateview;
			}
			
			public void setCheckBox(CheckBox c)
			{
				this.cb=c;
			}
			public void setDateView(TextView date)
			{
				this.date=date;
			}
			public CheckBox getCheckBox()
			{
				return this.cb;
			}
			public TextView getDateView()
			{
				return this.date;
			}
		}
		//base adapter class
		class todaySubtaskListAdapter extends BaseAdapter{

			private Activity context;
			//show assignment list
			ArrayList<String> list_name=new ArrayList<String>();
			ArrayList<String> list_date=new ArrayList<String>();
			ArrayList<Integer> list_status=new ArrayList<Integer>();
			List<Subtask> subtasks=new ArrayList<Subtask>();
			todaySubtaskListAdapter() {
				list_name=null;
				list_date=null;
				list_status=null;
				subtasks=null;
			}
			todaySubtaskListAdapter(Activity context,List<Subtask> subtasks2,ArrayList<String> name, ArrayList<String> date, ArrayList<Integer> status)
			{
				this.context=context;
				subtasks=subtasks2;
				list_name=name;
				list_date=date;
				list_status=status;
			}
			public View getView(int position, View convertView, ViewGroup parent) {
				Subtask subtask=(Subtask) this.getItem(position);
				View row;
				if(convertView==null)
				{
				LayoutInflater inflater = (LayoutInflater)context.getSystemService
						(Context.LAYOUT_INFLATER_SERVICE);
				
				row = inflater.inflate(R.layout.subtask_row, parent, false);
				}
				else
					row=convertView;
				TextView subtask_desc, duedate;
				CheckBox cb;
				ImageView dlt;
				dlt=(ImageView)row.findViewById(R.id.delete_task);
				cb=(CheckBox) row.findViewById(R.id.checkBox1);
				subtask_desc= (TextView) row.findViewById(R.id.subtask_desc);
				duedate = (TextView) row.findViewById(R.id.duedate);
				row.setTag(new SubtaskViewHolder(cb, duedate));
				if(list_status.get(position)==1)
				{
					cb.setChecked(true);
				}
				else
					cb.setChecked(false);
				subtask_desc.setText(list_name.get(position));
				duedate.setText(list_date.get(position));
				cb.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						CheckBox check=(CheckBox)v;
						Subtask subtask= (Subtask) check.getTag();
						//subtask.setTaskAssignment(assignment_id);					
						if(subtask.getTaskStatus()==1)
						{
							subtask.setTaskStatus(0);
						}
						else
							subtask.setTaskStatus(1);
						db.subtaskUpdate(subtask);
						
						
					}
				});
				dlt.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
					ImageView delete =(ImageView) v;	
					Subtask sub=(Subtask) delete.getTag();
					//sub.setTaskAssignment(assignment_id);
					int subid=sub.getTaskId();
					db.deleteSubtask(subid);
					refreshTodaySubtaskList();
					
					//sub_adapter.notifyDataSetChanged();
					}
				});
				cb.setTag(subtask);
				dlt.setTag(subtask);
				
				
				Log.e("POS", list_name.get(position));

				return (row);
			}
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return list_name.size();
			}
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return subtasks.get(position);
			}
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}
		}

	public void refreshTodaySubtaskList()
	{
		List<Subtask> subtasks= new ArrayList<Subtask>();
		subtasks = db.getTodaySubtask();
		ArrayList<String> list_name=new ArrayList<String>();
		ArrayList<String> list_date=new ArrayList<String>();
		ArrayList<Integer> list_status=new ArrayList<Integer>();
		for(Subtask subtask:subtasks)
		{
			list_name.add(subtask.getTaskDesc());
			list_date.add(subtask.getTaskDuedate());
			list_status.add(subtask.getTaskStatus());
		}
		l1=(ListView)findViewById(R.id.today_task_list);
		l1.setAdapter(new todaySubtaskListAdapter(SubtaskToday.this,subtasks,list_name, list_date, list_status));
	}

}
