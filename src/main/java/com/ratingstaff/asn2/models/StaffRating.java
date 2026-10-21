package com.ratingstaff.asn2.models;

import com.ratingstaff.asn2.profiles.GeneralStaffProfile;
import com.ratingstaff.asn2.profiles.ProfProfile;
import com.ratingstaff.asn2.profiles.StaffMemberProfile;
import com.ratingstaff.asn2.profiles.TAProfile;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table (name = "staffRating")
public class StaffRating {
    public enum RoleType{
        TA,
        PROF,
        INSTRUCTOR,
        STAFF
    };

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank 
    @Size (max = 20, message = "Name cannot exceed 20 characters")
    private String name;

    @NotBlank 
    @Email 
    @Size (max = 255, message="Email must not exceed 255 characters")
    private String email;

    @NotNull 
    @Enumerated (EnumType.STRING)
    private RoleType roleType;

    @NotNull 
    @Min (value = 1)
    @Max (value = 10)
    private Integer clarity;

    @NotNull 
    @Min (value = 1)
    @Max (value = 10)
    private Integer niceness;

    @NotNull 
    @Min (value = 1)
    @Max (value = 10)
    private Integer knowledgeable;

    @Size (max = 200, message="Comment must not exceed 200 characters")
    private String comment;
    
    public StaffRating() {
    }

    public Integer getOverall() {
        return (this.clarity + this.niceness +  this.knowledgeable)/3;
    }

    public StaffRating( String name, String email, RoleType roleType, Integer clarity, Integer niceness, Integer knowledgeable, String comment) {
        this.name = name;
        this.email = email;
        this.roleType = roleType;
        this.clarity = clarity;
        this.niceness = niceness;
        this.knowledgeable = knowledgeable;
        this.comment = comment;
    }

    public long getId() {
        return id;
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

    public Integer getClarity() {
        return clarity;
    }

    public void setClarity(Integer clarity) {
        this.clarity = clarity;
    }

    public Integer getNiceness() {
        return niceness;
    }

    public void setNiceness(Integer niceness) {
        this.niceness = niceness;
    }

    public Integer getKnowledgeable() {
        return knowledgeable;
    }

    public void setKnowledgeable(Integer knowledgeable) {
        this.knowledgeable = knowledgeable;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public StaffMemberProfile getProfile(){
        return switch (roleType) {
            case TA -> new TAProfile();
            case PROF -> new ProfProfile();
            default -> new GeneralStaffProfile();
        };
    }
}
