package com.project.assignmenttodo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sqliteHelper.DatabaseHelper;
import sqliteModel.Course;
import sqliteModel.Subtask;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SubTaskList extends FragmentActivity{
	int assignment_id;
	DatabaseHelper db;
	ListView l1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subtask_list);
		Bundle extra=getIntent().getExtras();
		assignment_id=extra.getInt("assignment_id");
		
		db=new DatabaseHelper(getApplicationContext());
		List<Subtask> subtasks= new ArrayList<Subtask>();
		subtasks = db.getAllSubtaskOfAssignment(assignment_id);
		if(subtasks.isEmpty())
		{
			Toast t= Toast.makeText(SubTaskList.this, "subtasks are empty", Toast.LENGTH_LONG);
			t.show();
		}
		ArrayList<String> list_name=new ArrayList<String>();
		ArrayList<String> list_date=new ArrayList<String>();
		ArrayList<Integer> list_status=new ArrayList<Integer>();
		for(Subtask subtask:subtasks)
		{
			list_name.add(subtask.getTaskDesc());
			list_date.add(subtask.getTaskDuedate());
			list_status.add(subtask.getTaskStatus());
		}
		
		l1=(ListView)findViewById(R.id.task_list);
		l1.setAdapter(new subtaskListAdapter(SubTaskList.this,subtasks,list_name, list_date, list_status));
	}
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
	class subtaskListAdapter extends BaseAdapter{

		private Activity context;
		//show assignment list
		ArrayList<String> list_name=new ArrayList<String>();
		ArrayList<String> list_date=new ArrayList<String>();
		ArrayList<Integer> list_status=new ArrayList<Integer>();
		List<Subtask> subtasks=new ArrayList<Subtask>();
		subtaskListAdapter() {
			list_name=null;
			list_date=null;
			list_status=null;
			subtasks=null;
		}
		subtaskListAdapter(Activity context,List<Subtask> subtasks2,ArrayList<String> name, ArrayList<String> date, ArrayList<Integer> status)
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
					
					if(subtask.getTaskStatus()==1)
					{
						subtask.setTaskStatus(0);
					}
					else
						subtask.setTaskStatus(1);
					db.subtaskUpdate(subtask);
					//Toast toast=Toast.makeText(SubTaskList.this, rows+" rows updated ", Toast.LENGTH_LONG);
					//toast.show();
					
				}
			});
			cb.setTag(subtask);
			
			/*Boolean checkStatus;
			if(subtask.getTaskStatus()==1)
			{
				checkStatus=true;
			}
			else
				checkStatus=false;
			cb.setChecked(checkStatus);
			duedate.setText(subtask.getTaskDuedate());*/
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
	//used for updating date
	public static class DatePickerFragment extends DialogFragment
	implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
		}
	}
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}

	public void addSubtask(View v)
	{
		final Dialog dialog=new Dialog(this);
		dialog.setContentView(R.layout.add_subtask);
		dialog.setTitle("Subtask addition");
		Button subtask_add=(Button) dialog.findViewById(R.id.add_subtask);
		Button cancel_subtask_add=(Button) dialog.findViewById(R.id.cancel_subtask);

		cancel_subtask_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();

			}
		});

		subtask_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText subtask_text= (EditText)dialog.findViewById(R.id.subtask_text);
				EditText date= (EditText)dialog.findViewById(R.id.date);
				Subtask new_subtask= new Subtask();
				String subtask_name= subtask_text.getText().toString();
				String subtask_date= date.getText().toString();
				if(subtask_name!=null)
				{
					new_subtask.setTaskDesc(subtask_name);
					new_subtask.setTaskStatus(0);
					new_subtask.setTaskDuedate(subtask_date);
					new_subtask.setTaskAssignment(assignment_id);
					long taskid=db.insertSubtask(new_subtask);
					Toast toast= Toast.makeText(SubTaskList.this, "id is "+taskid, Toast.LENGTH_LONG);
					toast.show();
					
				}
				else
				{
					Toast toast=Toast.makeText(SubTaskList.this, "Enter a valid subtask description", Toast.LENGTH_LONG);
					toast.show();
				}
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	public void refreshsubtaskList(View v)
	{
		List<Subtask> subtasks= new ArrayList<Subtask>();
		subtasks = db.getAllSubtaskOfAssignment(assignment_id);
		ArrayList<String> list_name=new ArrayList<String>();
		ArrayList<String> list_date=new ArrayList<String>();
		ArrayList<Integer> list_status=new ArrayList<Integer>();
		for(Subtask subtask:subtasks)
		{
			list_name.add(subtask.getTaskDesc());
			list_date.add(subtask.getTaskDuedate());
			list_status.add(subtask.getTaskStatus());
		}
		l1=(ListView)findViewById(R.id.task_list);
		l1.setAdapter(new subtaskListAdapter(SubTaskList.this,subtasks,list_name, list_date, list_status));

	}

}