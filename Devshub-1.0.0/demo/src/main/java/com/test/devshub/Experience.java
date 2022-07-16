package com.test.devshub;

import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.RequestScope;

@Controller
@RequestScope
public class Experience
{
    private int id;
    private String companyName;
    private String jobTitle;
    private int yearStarted;
    private int yearEnded;
    private String location;

    public Experience(){}

    public Experience(String companyName, String jobTitle, int yearStarted, int yearEnded, String location)
    {
        this.companyName=companyName;
        this.jobTitle=jobTitle;
        this.yearStarted=yearStarted;
        this.yearEnded=yearEnded;
        this.location=location;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setId(int id) {
        this.id=id;
    }
    public int getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return String.format("%d,%s,%s,%d,%d,%s", id, companyName, jobTitle, yearStarted, yearEnded, location);
    }
}
