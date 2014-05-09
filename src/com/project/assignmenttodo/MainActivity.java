package com.project.assignmenttodo;

import java.util.ArrayList;
import java.util.List;

import sqliteHelper.DatabaseHelper;
import sqliteModel.Course;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity {

	DatabaseHelper db;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		db = new DatabaseHelper(getApplicationContext());
		//list of courses
		List<Course> courses=new ArrayList<Course>();
		courses= db.getAllCourses();
		//list of course names
		final ArrayList<String> list= new ArrayList<String>();
		for(Course course:courses)
		{
			list.add(course.getCourseName());
		}

		ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, R.layout.course_row,R.id.course_name_row, list);
		setListAdapter(adapter);

	}

	//add course
	public void addCourse(View v)
	{
		final Dialog dialog=new Dialog(this);
		dialog.setContentView(R.layout.add_course);
		dialog.setTitle("Course addition");
		Button course_add=(Button) dialog.findViewById(R.id.course_add);
		Button cancel_course_add=(Button) dialog.findViewById(R.id.cancel_course_add);

		cancel_course_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();

			}
		});

		course_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText course_text= (EditText)dialog.findViewById(R.id.course_text);
				Course new_course= new Course();
				String course_name= course_text.getText().toString();
				if(course_name!=null)
				{
					new_course.setCourseName(course_name);
					db.insertCourse(new_course);
					//ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this, R.layout.course_row,R.id.course_name_row, list);
					//adapter.notifyDataSetChanged();
				}
				else
				{
					Toast toast=Toast.makeText(MainActivity.this, "Enter a valid course name", Toast.LENGTH_LONG);
					toast.show();
				}
				dialog.dismiss();
			}
		});
		dialog.show();
	}
	//refresh course list
	public void refreshCourseList(View v)
	{
		//list of courses
		List<Course> courses=new ArrayList<Course>();
		courses= db.getAllCourses();
		//list of course names
		final ArrayList<String> list= new ArrayList<String>();
		for(Course course:courses)
		{
			list.add(course.getCourseName());
		}
		ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, R.layout.course_row,R.id.course_name_row, list);
		setListAdapter(adapter);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
		//list of courses
		List<Course> courses=new ArrayList<Course>();
		courses= db.getAllCourses();
		//list of course names
		ArrayList<Integer> list_id= new ArrayList<Integer>() ;
		for(Course course:courses)
		{
			list_id.add(course.getCourseId());
		}
		int course_id=list_id.get(position);
	    Toast.makeText(this, course_id + " id selected", Toast.LENGTH_LONG).show();
	    Intent i = new Intent(MainActivity.this, AssignmentList.class);
	    i.putExtra("course_id",course_id );
	    startActivity(i);
	  }

}

