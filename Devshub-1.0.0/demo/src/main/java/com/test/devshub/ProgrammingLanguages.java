package com.test.devshub;

public class ProgrammingLanguages
{
    private String language;
    private String yearsExperience;

    public ProgrammingLanguages()
    {

    }

    public ProgrammingLanguages(String language, String yearsExperience)
    {
        this.language=language;
        this.yearsExperience=yearsExperience;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(String yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    @Override
    public String toString() {
        return String.format("%s,%s", language, yearsExperience);
    }
}
