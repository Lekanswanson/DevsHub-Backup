package com.test.devshub;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import java.util.ArrayList;

@Component
@RequestScope
public class Project
{
    private String title;
    private String description;
    private String language;
    private ArrayList<String> languages = new ArrayList<>();
    private String technology;
    private ArrayList<String> technologies = new ArrayList<>();
    private String video;
    public Project(){}
    public Project(String title, String description, String language, String technology, String video)
    {
        this.title=title;
        this.description=description;
        this.language=language;
        this.technology=technology;
        this.video=video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }
    public void setLanguages(String languages) {
        this.languages.add(languages);
    }

    public ArrayList<String> getTechnologies() {
        return technologies;
    }
    public void setTechnologies(String technologies) {
        this.technologies.add(technologies);
    }

    @Override
    public String toString()
    {
        return String.format("%s %s %s %s %s", title, description, language, technology, video);
    }
}
