package sqliteHelper;

import sqliteModel.Assignment;
import sqliteModel.Course;
import sqliteModel.Subtask;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	private static final String LOG= DatabaseHelper.class.getName();
	private static final int DATABASE_VERSION= 1;
	private static final String DATABASE_NAME= "assignmentManager";
	//Table names
	private static final String TABLE_ASSIGNMENT="assignments";
	private static final String TABLE_COURSES="courses";
	private static final String TABLE_SUBTASK="subtasks";
	//assignment column names
	private static final String KEY_ASSIGNMENT_ID="assignment_id";
	private static final String KEY_ASSIGNMENT_DESC="assignment_desc";
	private static final String KEY_ASSIGNMENT_STATUS="assignment_status";
	private static final String KEY_ASSIGNMENT_COURSE="assignment_course";
	//courses column names
	private static final String KEY_COURSE_ID="course_id";
	private static final String	KEY_COURSE_NAME="course_name";
	//subtask column names
	private static final String KEY_TASK_ID="task_id";
	private static final String KEY_TASK_DESC="task_desc";
	private static final String KEY_TASK_DUEDATE="duedate";
	private static final String KEY_TASK_STATUS	="status";
	private static final String KEY_TASK_ASSIGNMENT="assignment_id";
	
	
	//assignment table creation query
	private static final String CREATE_TABLE_ASSIGNMENT= "CREATE TABLE " + TABLE_ASSIGNMENT +
			"("+ KEY_ASSIGNMENT_ID+" INTEGER PRIMARY KEY,"+ KEY_ASSIGNMENT_DESC +" TEXT,"+
			KEY_ASSIGNMENT_STATUS+" INTEGER,"+ KEY_ASSIGNMENT_COURSE+" INTEGER, FOREIGN KEY("+KEY_ASSIGNMENT_COURSE+")"
			+" REFERENCES TABLE_COURSE(KEY_COURSE_ID)"+")";
	//course table creation query
	private static final String CREATE_TABLE_COURSE= "CREATE TABLE " +TABLE_COURSES+
			"(" + KEY_COURSE_ID +" INTEGER PRIMARY KEY,"+ KEY_COURSE_NAME+" TEXT"+")";
	//subtask table creation query
	private static final String CREATE_TABLE_SUBTASK= "CREATE TABLE " + TABLE_SUBTASK+
			"("+KEY_TASK_ID+"INTEGER PRIMARY KEY,"+KEY_TASK_DESC +" TEXT,"+KEY_TASK_DUEDATE+" DATETIME,"
			+KEY_TASK_STATUS+" INTEGER,"+KEY_TASK_ASSIGNMENT+" INTEGER, FOREIGN KEY("+KEY_TASK_ASSIGNMENT+")"
			+" REFERENCES TABLE_ASSIGNMENT(KEY_ASSIGNMENT_ID)"+")";

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_ASSIGNMENT);
		db.execSQL(CREATE_TABLE_COURSE);
		db.execSQL(CREATE_TABLE_SUBTASK);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
		db.execSQL("DROP TABLE IF EXIST"+TABLE_ASSIGNMENT);
		db.execSQL("DROP TABLE IF EXIST" + TABLE_COURSES);
		db.execSQL("DROP TABLE IF EXIST" +TABLE_SUBTASK);
		onCreate(db);
	}
	

//CRUD
//add course
public long insertCourse(Course course)
{
	SQLiteDatabase db= this.getWritableDatabase();
	ContentValues values= new ContentValues();
	values.put(KEY_COURSE_NAME,course.getCourseName());
	long course_id=db.insert(TABLE_COURSES, null, values);
	return course_id;
}
//add assignment
public long insertAssignment(Assignment assignment)
{
	SQLiteDatabase db = this.getWritableDatabase();
	ContentValues values= new ContentValues();
	values.put(KEY_ASSIGNMENT_DESC, assignment.getAssignmentDesc());
	values.put(KEY_ASSIGNMENT_STATUS, assignment.getAssignmentStatus());
	values.put(KEY_ASSIGNMENT_COURSE, assignment.getAssignmentCourse());
	long assignment_id= db.insert(TABLE_ASSIGNMENT,null,values);
	return assignment_id;
}
//add subtask
public long insertSubtask(Subtask subtask)
{
	SQLiteDatabase db = this.getWritableDatabase();
	ContentValues values= new ContentValues();
	values.put(KEY_TASK_DESC, subtask.getTaskDesc());
	values.put(KEY_TASK_STATUS, subtask.getTaskStatus());
	values.put(KEY_TASK_DUEDATE, subtask.getTaskDuedate());
	values.put(KEY_TASK_ASSIGNMENT, subtask.getTaskAssignment());
	long task_id= db.insert(TABLE_SUBTASK,null,values);
	return task_id;
}
}