package com.sunilos.p4.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Course JavaBean encapsulates Course attributes
 */
public class CourseBean extends BaseBean {

    private String name;

    private String description;

    private String duration;

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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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
            this.setDuration(rs.getString("DURATION"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
