package com.test.devshub;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class Education
{
    private int id;
    private String universityLocation;
    private int yearStarted;
    private int yearEnded;
    private String courseStudied;
    private String degreeLevel;
    private String resultsAchieved;

    public Education(){}

    public Education(int id, String universityLocation, int yearStarted, int yearEnded, String courseStudied, String degreeLevel, String resultsAchieved)
    {
        this.id=id;
        this.universityLocation=universityLocation;
        this.yearStarted=yearStarted;
        this.yearEnded=yearEnded;
        this.courseStudied=courseStudied;
        this.degreeLevel=degreeLevel;
        this.resultsAchieved=resultsAchieved;
    }

    public void setId(int id)
    {
        this.id=id;
    }
    public int getId()
    {
        return id;
    }
    public String getUniversityLocation() {
        return universityLocation;
    }

    public void setUniversityLocation(String universityLocation) {
        this.universityLocation = universityLocation;
    }

    public int getYearStarted() {
        return yearStarted;
    }

    public void setYearStarted(int yearStarted) {
        this.yearStarted = yearStarted;
    }

    public int getYearEnded() {
        return yearEnded;
    }

    public void setYearEnded(int yearEnded) {
        this.yearEnded = yearEnded;
    }

    public String getCourseStudied() {
        return courseStudied;
    }

    public void setCourseStudied(String courseStudied) {
        this.courseStudied = courseStudied;
    }

    public String getDegreeLevel() {
        return degreeLevel;
    }

    public void setDegreeLevel(String degreeLevel) {
        this.degreeLevel = degreeLevel;
    }

    public String getResultsAchieved() {
        return resultsAchieved;
    }

    public void setResultsAchieved(String resultsAchieved) {
        this.resultsAchieved = resultsAchieved;
    }

    @Override
    public String toString()
    {
        return String.format("%d,%s,%d,%d,%s,%s,%s", id, universityLocation, yearStarted, yearEnded, courseStudied, degreeLevel, resultsAchieved);
    }
}
