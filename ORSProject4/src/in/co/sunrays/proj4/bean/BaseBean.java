package in.co.sunrays.proj4.bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Parent class of all Beans in the application. It contains generic attributes
 * that are common across multiple Beans.
 * 
 * @author SUNRAYS Technologies
 * @version 1.0
 * @Copyright (c) SUNRAYS Technologies
 */
public abstract class BaseBean implements Serializable, DropdownListBean, Comparable<BaseBean> {

    /**
     * Non-business primary key (unique identifier for each record)
     */
    protected long id;

    /**
     * User ID of the person who created this database record.
     */
    protected String createdBy;

    /**
     * User ID of the person who last modified this database record.
     */
    protected String modifiedBy;

    /**
     * Timestamp indicating when this record was created.
     */
    protected Timestamp createdDatetime;

    /**
     * Timestamp indicating when this record was last modified.
     */
    protected Timestamp modifiedDatetime;

    /**
     * Getter for the ID.
     * 
     * @return long - the ID of the record
     */
    public long getId() {
        return id;
    }

    /**
     * Setter for the ID.
     * 
     * @param id - the unique identifier to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter for the createdBy field.
     * 
     * @return String - the ID of the user who created the record
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter for the createdBy field.
     * 
     * @param createdBy - the ID of the user who created the record
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter for the modifiedBy field.
     * 
     * @return String - the ID of the user who last modified the record
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Setter for the modifiedBy field.
     * 
     * @param modifiedBy - the ID of the user who last modified the record
     */
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    /**
     * Getter for the createdDatetime field.
     * 
     * @return Timestamp - the time when the record was created
     */
    public Timestamp getCreatedDatetime() {
        return createdDatetime;
    }

    /**
     * Setter for the createdDatetime field.
     * 
     * @param createdDatetime - the creation timestamp to set
     */
    public void setCreatedDatetime(Timestamp createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    /**
     * Getter for the modifiedDatetime field.
     * 
     * @return Timestamp - the time when the record was last modified
     */
    public Timestamp getModifiedDatetime() {
        return modifiedDatetime;
    }

    /**
     * Setter for the modifiedDatetime field.
     * 
     * @param modifiedDatetime - the modification timestamp to set
     */
    public void setModifiedDatetime(Timestamp modifiedDatetime) {
        this.modifiedDatetime = modifiedDatetime;
    }

    /**
     * Compares this bean to another bean based on the value field.
     * 
     * @param next - the next BaseBean to compare against
     * @return int - the result of the comparison
     */
    public int compareTo(BaseBean next) {
        return getValue().compareTo(next.getValue());
    }

    /**
     * Abstract method to get the value of the bean, which must be implemented by 
     * subclasses.
     * 
     * @return String - the value to be compared
     */
    public abstract String getValue();
}
