package com.project.assignmenttodo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sqliteHelper.DatabaseHelper;
import sqliteModel.Subtask;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

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
		l1.setAdapter(new subtaskListAdapter(list_name, list_date, list_status));
	}
	class subtaskListAdapter extends BaseAdapter{

		private Activity context;
		//show assignment list
		ArrayList<String> list_name=new ArrayList<String>();
		ArrayList<String> list_date=new ArrayList<String>();
		ArrayList<Integer> list_status=new ArrayList<Integer>();
		subtaskListAdapter() {
			list_name=null;
			list_date=null;
			list_status=null;
		}
		subtaskListAdapter(ArrayList<String> name, ArrayList<String> date, ArrayList<Integer> status)
		{
			list_name=name;
			list_date=date;
			list_status=status;
		}
		public View getView(int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater)context.getSystemService
					(Context.LAYOUT_INFLATER_SERVICE);
			View row;
			row = inflater.inflate(R.layout.subtask_row, parent, false);
			TextView subtask_desc, duedate;
			CheckBox cb;
			cb=(CheckBox) row.findViewById(R.id.checkBox1);
			subtask_desc= (TextView) row.findViewById(R.id.subtask_desc);
			duedate = (TextView) row.findViewById(R.id.duedate);
			if(list_status.get(position)==1)
			{
				cb.setChecked(true);
			}
			else
				cb.setChecked(false);
			subtask_desc.setText(list_name.get(position));
			duedate.setText(list_date.get(position));
			//i1.setImageResource(imge[position]);

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
			return position;
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
	}
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

}