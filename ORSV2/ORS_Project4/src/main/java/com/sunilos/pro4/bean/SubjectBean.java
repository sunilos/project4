package com.sunilos.pro4.bean;

/**
 * Subject JavaBean encapsulates Subject attributes.
 * 
 * @author Sunil OS
 *
 */
public class SubjectBean extends BaseBean{

	private String subjectName;
	private String description;
	private long courseId;
	private String courseName;
	
	
	
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getCourseId() {
		return courseId;
	}
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return id+"";
	}
	public String getValue() {
		// TODO Auto-generated method stub
		return subjectName;
	}
	
	
	
}
