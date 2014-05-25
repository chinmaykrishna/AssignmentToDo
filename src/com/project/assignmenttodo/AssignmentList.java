package com.project.assignmenttodo;

import java.util.ArrayList;
import java.util.List;

import sqliteHelper.DatabaseHelper;
import sqliteModel.Assignment;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AssignmentList extends Activity{
	int course_id;
	DatabaseHelper db;
	ListView l1;

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
		l1=(ListView)findViewById(R.id.assign_list);
		l1.setAdapter(new assignmentListAdapter(AssignmentList.this,assignments,list));


	}
	private static class AssignmentViewHolder{
		private TextView desc;
		
		public AssignmentViewHolder()
		{
			
		}
		public AssignmentViewHolder( TextView descview)
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
	class assignmentListAdapter extends BaseAdapter{

		private Activity context;
		//show assignment list
		ArrayList<String> list_name=new ArrayList<String>();
		
		List<Assignment> assignments=new ArrayList<Assignment>();
		assignmentListAdapter() {
			list_name=null;
		    assignments=null;
		}
		assignmentListAdapter(Activity context,List<Assignment> assignments2,ArrayList<String> name)
		{
			this.context=context;
			assignments=assignments2;
			list_name=name;
			
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			Assignment assignment=(Assignment) this.getItem(position);
			View row;
			if(convertView==null)
			{
			LayoutInflater inflater = (LayoutInflater)context.getSystemService
					(Context.LAYOUT_INFLATER_SERVICE);
			
			row = inflater.inflate(R.layout.assignment_row, parent, false);
			}
			else
				row=convertView;
			TextView assignment_desc;
			ImageView dlt;
			dlt=(ImageView)row.findViewById(R.id.delete_assignment);
			assignment_desc= (TextView) row.findViewById(R.id.assignment_name_row);
			
			row.setTag(new AssignmentViewHolder(assignment_desc));
			
			assignment_desc.setText(list_name.get(position));
			
			
			dlt.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				ImageView delete =(ImageView) v;	
				Assignment assn=(Assignment) delete.getTag();
				assn.setAssignmentCourse(course_id);
				int assnid=assn.getAssignmentNo();
				Toast t1=Toast.makeText(AssignmentList.this, assnid +" should be deleted", Toast.LENGTH_LONG);
				t1.show();
				db.deleteAssignment(assnid);
				refreshAssignmentList();
				}
			});
			assignment_desc.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				TextView name=(TextView) v;
				Assignment assn=(Assignment) name.getTag();
				
				int assnid=assn.getAssignmentNo();
			    Intent i = new Intent(AssignmentList.this, SubTaskList.class);
			    i.putExtra("assignment_id",assnid );
			    startActivity(i);	
				}
			});
			assignment_desc.setTag(assignment);
			dlt.setTag(assignment);
			
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
			return assignments.get(position);
		}
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
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
					long ass = db.insertAssignment(new_assignment);
					refreshAssignmentList();
					Toast t=Toast.makeText(AssignmentList.this, ass +" inserted",Toast.LENGTH_SHORT);
					t.show();
					
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
	public void refreshAssignmentList()
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


		l1=(ListView)findViewById(R.id.assign_list);
		l1.setAdapter(new assignmentListAdapter(AssignmentList.this,assignments,list));

	}
	
	/*@Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
		List<Assignment> assignments=new ArrayList<Assignment>();
		assignments= db.getAllAssignmentOfCourse(course_id);
		//list of course names
		final ArrayList<Integer> list_id= new ArrayList<Integer>() ;
		
		for(Assignment assignment:assignments)
		{
			list_id.add(assignment.getAssignmentNo());
		}
		int assignment_id=list_id.get(position);
	    Toast.makeText(this, assignment_id + " id selected", Toast.LENGTH_LONG).show();
	    Intent i = new Intent(AssignmentList.this, SubTaskList.class);
	    i.putExtra("assignment_id",assignment_id );
	    startActivity(i);
	  }*/
	
}
