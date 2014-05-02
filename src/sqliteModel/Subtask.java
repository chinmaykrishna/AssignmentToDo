package sqliteModel;

public class Subtask {
int task_id;
String duedate;
String task_desc;
int status;
int assignment_id;

//constructors
public Subtask()
{
}
public Subtask(int id, String duedate)
{
	this.task_id=id;
	this.duedate=duedate;
}
public Subtask(int id, int status)
{
	this.task_id=id;
	this.status=status;
}
public Subtask(int id, String duedate,String desc, int status)
{
	this.task_id=id;
	this.duedate=duedate;
	this.task_desc=desc;
	this.status=status;
}
//getters
public int getTaskId()
{
	return this.task_id;
}
public String getTaskDuedate()
{
	return this.duedate;
}
public int getTaskStatus()
{
	return this.status;
}
public String getTaskDesc()
{
	return this.task_desc;
}
public int getTaskAssignment()
{
	return this.assignment_id;
}
//setters
public void setTaskId(int id)
{
	this.task_id=id;
}
public void setTaskDuedate(String date)
{
	this.duedate=date;
}
public void setTaskStatus(int status)
{
	this.status=status;
}
public void setTaskDesc(String desc)
{
	this.task_desc=desc;
}
public void setTaskAssignment(int id)
{
	this.assignment_id=id;
}
}
