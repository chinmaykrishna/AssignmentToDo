package com.project.assignmenttodo;

import java.util.ArrayList;
import java.util.List;

import sqliteHelper.DatabaseHelper;
import sqliteModel.Subtask;
import android.app.Application;

public class TaskelApplication extends Application{
	DatabaseHelper db;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		db=new DatabaseHelper(getApplicationContext());
		List<Subtask> subs=new ArrayList<Subtask>();
		
		subs=db.getTodaySubtask();
		if(!subs.isEmpty())
		{
		int no_of_subtasks=subs.size();
		Notifiers n=new Notifiers();
		n.createNotification(getApplicationContext(), no_of_subtasks);
		}

		
	}
}
	

