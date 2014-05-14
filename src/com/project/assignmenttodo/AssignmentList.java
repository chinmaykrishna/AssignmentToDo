package com.project.assignmenttodo;

import java.util.ArrayList;
import java.util.List;

import sqliteHelper.DatabaseHelper;
import sqliteModel.Assignment;
import sqliteModel.Course;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class AssignmentList extends ListActivity{
	int course_id;
	DatabaseHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assignment_list);
		course_id=getIntent().getIntExtra("course_id", 0);

		db = new DatabaseHelper(getApplicationContext());
		//list of Assignments
		List<Assignment> assignments=new ArrayList<Assignment>();
		assignments= db.getAllAssignmentOfCourse(course_id);
		
		//list of Assignment names
		final ArrayList<String> list= new ArrayList<String>();
		for(Assignment assignment:assignments)
		{
			list.add(assignment.getAssignmentDesc());
		}


		ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, R.layout.assignment_row,R.id.assignment_name_row, list);
		setListAdapter(adapter);

	}
	//add assignment
	public void addAssignment(View v)
	{
		final Dialog dialog=new Dialog(this);
		dialog.setContentView(R.layout.add_assignment);
		dialog.setTitle("Assignment addition");
		Button assignment_add=(Button) dialog.findViewById(R.id.assignment_add);
		Button cancel_assignment_add=(Button) dialog.findViewById(R.id.cancel_assignment_add);

		cancel_assignment_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();

			}
		});

		assignment_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText assignment_text= (EditText)dialog.findViewById(R.id.assignment_text);
				Assignment new_assignment= new Assignment();
				String assignment_name= assignment_text.getText().toString();
				if(assignment_name!=null)
				{
					new_assignment.setAssignmentDesc(assignment_name);
					new_assignment.setAssignmentCourse(course_id);
					new_assignment.setAssignmentStatus(0);
					db.insertAssignment(new_assignment);
					
					//ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this, R.layout.assignment_row,R.id.assignment_name_row, list);
					//adapter.notifyDataSetChanged();
				}
				else
				{
					Toast toast=Toast.makeText(AssignmentList.this, "Enter a valid assignment name", Toast.LENGTH_LONG);
					toast.show();
				}
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	public void refreshAssignmentList(View v)
	{
		//list of Assignments
		List<Assignment> assignments=new ArrayList<Assignment>();
		assignments= db.getAllAssignmentOfCourse(course_id);
		//list of Assignment names
		final ArrayList<String> list= new ArrayList<String>();
		for(Assignment assignment:assignments)
		{
			list.add(assignment.getAssignmentDesc());
		}


		ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, R.layout.assignment_row,R.id.assignment_name_row, list);
		setListAdapter(adapter);

	}
	
	@Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
		List<Assignment> assignments=new ArrayList<Assignment>();
		assignments= db.getAllAssignmentOfCourse(course_id);
		//list of course names
		ArrayList<Integer> list_id= new ArrayList<Integer>() ;
		for(Assignment assignment:assignments)
		{
			list_id.add(assignment.getAssignmentNo());
		}
		int assignment_id=list_id.get(position);
	    Toast.makeText(this, assignment_id + " id selected", Toast.LENGTH_LONG).show();
	    Intent i = new Intent(AssignmentList.this, SubTaskList.class);
	    i.putExtra("assignment_id",assignment_id );
	    startActivity(i);
	  }
	

	
}
