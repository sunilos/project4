package com.sunilos.p4.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectBean extends BaseBean {

    private String name;
    private String description;
    private long courseId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String getKey() {
        return id + "";
    }

    @Override
    public String getValue() {
        return name;
    }

    @Override
    public void setResultset(ResultSet rs) {
        try {
            super.setResultset(rs);
            this.setName(rs.getString("NAME"));
            this.setDescription(rs.getString("DESCRIPTION"));
            this.setCourseId(rs.getLong("COURSE_ID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
