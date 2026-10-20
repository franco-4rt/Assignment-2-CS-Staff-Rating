package com.ratingstaff.asn2.models;

public class StaffRating {
    public enum RoleType{
        TA,
        PROF,
        INSTRUCTOR,
        STAFF
    };
    private int  id;
    private String name;
    private String email;
    private RoleType roleType;
    private int clarity;
    private int niceness;
    private int knowledgeable;
    private String comment;
    
    public StaffRating() {
    }

    public int getOverall() {
        return (this.clarity + this.niceness +  this.knowledgeable)/3;
    }

    public StaffRating( String name, String email, RoleType roleType, int clarity, int niceness, int knowledgeable, String comment) {
        this.name = name;
        this.email = email;
        this.roleType = roleType;
        this.clarity = clarity;
        this.niceness = niceness;
        this.knowledgeable = knowledgeable;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public int getClarity() {
        return clarity;
    }

    public void setClarity(int clarity) {
        this.clarity = clarity;
    }

    public int getNiceness() {
        return niceness;
    }

    public void setNiceness(int niceness) {
        this.niceness = niceness;
    }

    public int getKnowledgeable() {
        return knowledgeable;
    }

    public void setKnowledgeable(int knowledgeable) {
        this.knowledgeable = knowledgeable;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
