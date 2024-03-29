package com.test.devshub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
@RequestScope
public class Member
{
    @Value("${filename}")
    private String filename;
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private MemberEmail email;
    @Autowired
    private ArrayList<Education> education;
    @Autowired
    private ArrayList<Experience> experiences;
    @Autowired
    private ArrayList<Project> projects;
    @Autowired
    private Map<String, ArrayList<Message>> messages;
    @Autowired
    private ArrayList<ProgrammingLanguages> languages = new ArrayList<>();
    private String firstName;
    private String lastName;
    private String location;
    private String message;
    private String image;
    private String color;

    public Member(){

    }

    public Member(MemberEmail email, ArrayList education, ArrayList experiences, ArrayList projects, Map messages)
    {
        this.email=email;
        this.education=education;
        this.experiences=experiences;
        this.projects=projects;
        this.messages=messages;
    }

    public void setExperiences(Experience experience)
    {
        this.experiences.add(experience);
    }
    public ArrayList<Experience> getExperiences()
    {
        return experiences;
    }

    public void setEducation(Education education)
    {
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


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }
    public void addProject(Project project){
        projects.add(project);
    }

    public void addMessagesToInbox(String name, ArrayList<Message> message) {
        messages.put(name, message);
    }

    public Map<String, ArrayList<Message>> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, ArrayList<Message>> messages) {
        this.messages = messages;
    }

    public void setProgrammingLanguages(ArrayList<ProgrammingLanguages> languages)
    {
        this.languages=languages;
    }

    public ArrayList<ProgrammingLanguages> getLanguages()
    {
        return languages;
    }

    @Override
    public String toString()
    {
        return String.format("%s %s %s %s", email, firstName, lastName, location);
    }
}
