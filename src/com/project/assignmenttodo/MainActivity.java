package com.project.assignmenttodo;

import java.util.ArrayList;
import java.util.List;

import sqliteHelper.DatabaseHelper;
import sqliteModel.Course;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	DatabaseHelper db;
	ListView l1;
	
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

		l1=(ListView)findViewById(R.id.c_list);
		l1.setAdapter(new courseListAdapter(MainActivity.this,courses,list));

	}
	private static class courseViewHolder{
		private TextView desc;
		
		public courseViewHolder()
		{
			
		}
		public courseViewHolder( TextView descview)
		{
		
			this.desc=descview;
		}
		
		public void setDesc(TextView desc)
		{
			this.desc=desc;
		}
		public TextView getDesc()
		{
			return this.desc;
		}
	}
	class courseListAdapter extends BaseAdapter{

		private Activity context;
		//show course list
		ArrayList<String> list_name=new ArrayList<String>();
		
		List<Course> courses=new ArrayList<Course>();
		courseListAdapter() {
			list_name=null;
		    courses=null;
		}
		courseListAdapter(Activity context,List<Course> courses2,ArrayList<String> name)
		{
			this.context=context;
			courses=courses2;
			list_name=name;
			
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			Course course=(Course) this.getItem(position);
			View row;
			if(convertView==null)
			{
			LayoutInflater inflater = (LayoutInflater)context.getSystemService
					(Context.LAYOUT_INFLATER_SERVICE);
			
			row = inflater.inflate(R.layout.course_row, parent, false);
			}
			else
				row=convertView;
			TextView course_desc;
			ImageView dlt;
			dlt=(ImageView)row.findViewById(R.id.delete_course);
			course_desc= (TextView) row.findViewById(R.id.course_name_row);
			
			row.setTag(new courseViewHolder(course_desc));
			
			course_desc.setText(list_name.get(position));
			
			
			dlt.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				ImageView delete =(ImageView) v;	
				Course c=(Course) delete.getTag();
				int cid=c.getCourseId();
				Toast t1=Toast.makeText(MainActivity.this, cid +" should be deleted", Toast.LENGTH_LONG);
				t1.show();
				db.deleteCourse(cid);
				refreshCourseList();
				}
			});
			course_desc.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				TextView name=(TextView) v;
				Course co=(Course) name.getTag();
				
				int coid=co.getCourseId();
			    Intent i = new Intent(MainActivity.this, AssignmentList.class);
			    i.putExtra("course_id",coid );
			    startActivity(i);	
				}
			});
			course_desc.setTag(course);
			dlt.setTag(course);
			
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
			return courses.get(position);
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
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
					refreshCourseList();
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
	public void refreshCourseList()
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
		l1=(ListView)findViewById(R.id.c_list);
		l1.setAdapter(new courseListAdapter(MainActivity.this,courses,list));

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.instruction:
			Intent i=new Intent(MainActivity.this, Instruction.class);
			startActivity(i);
		default:
		return super.onOptionsItemSelected(item);
		}
	}
	
	
	
}

