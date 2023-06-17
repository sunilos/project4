package in.co.sunrays.proj4.bean;

/**
 * Marksheet JavaBean encapsulates Marksheet attributes
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 * 
 */

public class MarksheetBean extends BaseBean {

	 /**
     * Rollno of Student
     */
    private String rollNo;
    /**
     * ID of Student
     */
    private long studentId;
    /**
     * Name of Student
     */
    private String name;
    /**
     * Physics marks of Student
     */
    private Integer physics;
    /**
     * Chemistry marks of Student
     */
    private Integer chemistry;
    /**
     * Mathematics marks of Student
     */
    private Integer maths;

    /**
     * accessor
     */

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPhysics() {
		return physics;
	}

	public void setPhysics(Integer physics) {
		this.physics = physics;
	}

	public Integer getChemistry() {
		return chemistry;
	}

	public void setChemistry(Integer chemistry) {
		this.chemistry = chemistry;
	}

	public Integer getMaths() {
		return maths;
	}

	public void setMaths(Integer maths) {
		this.maths = maths;
	}
	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	@Override
	public String getKey() {
		return id +"";
	}

	@Override
	public String getValue() {
		return rollNo;
	}
	
	
}
