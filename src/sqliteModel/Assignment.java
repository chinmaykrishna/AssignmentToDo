package sqliteModel;

public class Assignment {
int assignment_no;
String assignment_Desc;
int course_id;
int status;

//constructors

public Assignment()
{
}

public Assignment(int no, int status)
{
	this.assignment_no=no;
	this.status=status;
}

public Assignment(int no, String desc, int status)
{
	this.assignment_Desc=desc;
	this.assignment_no=no;
	this.status=status;
}

//setters
public void setAssignmentNo(int no)
{
	this.assignment_no=no;
}

public void setAssignmentDesc(String desc)
{
	this.assignment_Desc=desc;
}
public void seAssignmentStatus(int status)
{
	this.status=status;
}
public void setAssignmentCourse(int course_id)
{
	this.course_id=course_id;
}
//getters
public int getAssignmentNo()
{
	return this.assignment_no;
}
public String getAssignmentDesc()
{
	return this.assignment_Desc;
}
public int getAssignmentStatus()
{
	return this.status;
}
public int getAssignmentCourse()
{
	return this.course_id;
}
}