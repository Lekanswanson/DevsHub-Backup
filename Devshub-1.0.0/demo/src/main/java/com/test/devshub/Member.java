package com.test.devshub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@RequestScope
public class Member
{
    //private int memberId;
    @Autowired
    private MemberEmail email;
    @Autowired
    private ArrayList<Education> education = new ArrayList<>();

    @Autowired
    private ArrayList<Experience> experiences = new ArrayList<>();

    private String firstName;
    private String lastName;
    private String location;
    private String message;
    private String image;

    private String color;

    private String video;


    public Member()
    {

    }

    public Member(MemberEmail email, ArrayList<Education> education, ArrayList<Experience> experiences)
    {
        this.email=email;
        this.education=education;
        this.experiences=experiences;
    }

    public void setExperiences(Experience experience)
    {
        MemberDB.expID++;
        experience.setId(MemberDB.expID);
        this.experiences.add(experience);
    }
    public ArrayList<Experience> getExperiences()
    {
        return experiences;
    }

    public void setEducation(Education education)
    {
        MemberDB.ID++;
        education.setId(MemberDB.ID);
        this.education.add(education);
    }
    public ArrayList<Education> getEducation()
    {
        return education;
    }

    public String getEmail() {
        return email.getEmail();
    }

    public void setEmail(String email) {
        this.email.setEmail(email);
    }

    public String getPassWord() {
        return email.getPassword();
    }

    public void setPassword(String password)
    {
        this.email.setPassword(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setImage(String image)
    {
        this.image=image;
    }

    public String getImage()
    {
        return image;
    }

    @Override
    public String toString()
    {
        return String.format("%s %s %s %s", email, firstName, lastName, location);
    }

    public void setVideo(String video) {
        this.video=video;
    }

    public String getVideo()
    {
        return this.video;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
