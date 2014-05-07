package sqliteModel;

public class Course {
int course_id;
String course_name;

//constructors
public Course()
{

}

public Course(String name)
{
	this.course_name=name;
}

public Course (int id, String name)
{
	this.course_id=id;
	this.course_name=name;
}

//getters
public int getCourseId()
{
	return this.course_id;
}

public String getCourseName()
{
	return this.course_name;
}

//setters
public void setCourseId(int id)
{
	this.course_id=id;
}

public void setCourseName(String name)
{
	this.course_name=name;
}
}