package com.test.devshub;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

@Component
@Repository
@ConfigurationProperties(prefix = "sqldriver")
public class SQL
{
    private Connection connection;
    private Statement statement;
    private String driver;
    private String path;
    private String userName;
    private String passWord;

    public boolean initDBConnection()
    {
        boolean successful=false;
        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(path, userName, passWord);
            statement = connection.createStatement();
            successful=true;
        }
        catch(Exception e)
        {
            System.out.print("Failed to initialise DB Connection\n" + e.getMessage());
        }
        return successful;
    }

    public void selectAllFromDetails()
    {
        try
        {
            ResultSet rs=statement.executeQuery("select * from users;");
            while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void selectBetween40And50()
    {
        ResultSet rs;
        String cmd = "SELECT * from logindetails;";
        try
        {
            rs = statement.executeQuery(cmd);
            while (rs.next())
            {
                System.out.println(rs.getString("email"));
                System.out.println(rs.getString("passcode"));
            }
            //connection.close();
        }
        catch(Exception e1){e1.printStackTrace();}
    }

    boolean validateEmail(MemberEmail memberEmail)
    {
        boolean memberExists=false;
        String command = "select * from logindetails where email='" + memberEmail.getEmail() +"'";

        try{
            ResultSet rs = statement.executeQuery(command);
            if(rs.next())
            {
                String email = rs.getString("email");
                String password = rs.getString("passcode");

                if(email.trim().equals(memberEmail.getEmail().trim()))
                    if(password.trim().equals(memberEmail.getPassword().trim()))
                        memberExists=true;
            }
        }
        catch (SQLException sqlDataException){
            System.out.println(sqlDataException.getMessage());
        }
        return memberExists;
    }

    Member getMember(MemberEmail memberEmail)
    {
        Member member=new Member(memberEmail, new ArrayList<>(), new ArrayList(), new ArrayList(), new HashMap<>());
        String command = String.format("select * from users where email='%s';", member.getEmail());
        String path = "../images/";
        try
        {
            ResultSet resultSet = statement.executeQuery(command);
            if(resultSet.next())
            {
                member.setFirstName(resultSet.getString("firstName"));
                member.setLastName(resultSet.getString("lastName"));
                member.setLocation(resultSet.getString("location"));
                member.setMessage((resultSet.getString("message")));
                member.setImage(path+resultSet.getString("image"));
                member.setColor(resultSet.getString("color"));
            }
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }

        command = String.format("select * from education where email = '%s';", member.getEmail());
        try {
            ResultSet resultSet = statement.executeQuery(command);
            Education education = new Education();

            while (resultSet.next())
            {
                education.setId(resultSet.getInt("id"));
                education.setUniversityLocation(resultSet.getString("universityLocation"));
                education.setYearStarted(resultSet.getInt("yearStarted"));
                education.setYearEnded(resultSet.getInt("yearEnded"));
                education.setCourseStudied(resultSet.getString("courseStudied"));
                education.setDegreeLevel(resultSet.getString("degreeLevel"));
                education.setResultsAchieved(resultSet.getString("resultsAchieved"));

                member.setEducation(education);
            }

        }catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }

        command = String.format("select * from experience where email = '%s';", member.getEmail());
        try {
            ResultSet resultSet = statement.executeQuery(command);
            Experience experience = new Experience();

            while (resultSet.next())
            {
                experience.setId(resultSet.getInt("id"));
                experience.setCompanyName(resultSet.getString("companyName"));
                experience.setJobTitle(resultSet.getString("jobTitle"));
                experience.setYearStarted(resultSet.getInt("yearStarted"));
                experience.setYearEnded(resultSet.getInt("yearEnded"));
                experience.setLocation(resultSet.getString("location"));

                member.setExperiences(experience);
            }
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }

        //Create message object from database
        //Check if member is

        command = String.format("select * from messages;");
        try {
            ResultSet rs = statement.executeQuery(command);
            while (rs.next())
            {
                ArrayList<Message> inbox;

                String sender = rs.getString("sender");
                String receiver = rs.getString("receiver");
                String message = rs.getString("message");

                Message m = new Message(sender, receiver, message, "show", "hide");
                System.out.println(m);

                if(member.getEmail().equals(sender))
                {
                    if(member.getMessages().containsKey(receiver))
                    {
                        inbox = member.getMessages().get(receiver);
                        inbox.add(m);
                    }
                    else
                    {
                        inbox = new ArrayList<>();
                        inbox.add(m);
                    }
                    member.addMessagesToInbox(receiver, inbox);
                }
                else if(member.getEmail().equals(receiver))
                {
                    if(member.getMessages().containsKey(sender))
                    {
                        inbox = member.getMessages().get(sender);
                        inbox.add(m);
                    }
                    else
                    {
                        inbox = new ArrayList<>();
                        inbox.add(m);
                    }
                    member.addMessagesToInbox(sender, inbox);
                }
            }
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        MemberDB.addMember(member);
        return member;
    }

    boolean saveEducation(Member member, Education education) {
        boolean successful=false;
        try {
            String command = String.format("INSERT INTO education VALUES (null, '%s', %d, %d, '%s', '%s', '%s', '%s');",
                    education.getUniversityLocation(), education.getYearStarted(), education.getYearEnded(), education.getCourseStudied(), education.getDegreeLevel(), education.getResultsAchieved(), member.getEmail());
            statement.executeUpdate(command);
            successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
    }

    boolean saveExperience(Member member, Experience experience)
    {
        boolean successful=false;
        try {
            String command = String.format("INSERT INTO experience VALUES (null, '%s', '%s', %d, %d, '%s', '%s');",
                    experience.getCompanyName(), experience.getJobTitle(), experience.getYearStarted(), experience.getYearEnded(), experience.getLocation(), member.getEmail());
            statement.executeUpdate(command);
            successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
    }

    boolean existAlready(MemberEmail email) {
        boolean exists=false;
        String command = String.format("select email from logindetails where email='%s';" + email.getEmail());
        try
        {
            ResultSet rs = statement.executeQuery(command);
            if(rs.next())
            {
                exists=true;
            }
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return exists;
    }

    boolean addMember(Member member)
    {
        boolean successful=false;
        try {
            String command = String.format("insert into logindetails values('%s','%s');", member.getEmail(), member.getPassWord());
            statement.executeUpdate(command);
            command = String.format("INSERT INTO users VALUES (null, '%s', '%s', '%s', null, 'person.png', '#cad07c', '%s');", member.getFirstName(), member.getLastName(), member.getLocation(), member.getEmail());
            statement.executeUpdate(command);

            successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
    }

    boolean setMessage(Member member, String message)
    {
        boolean successful=false;
        try
        {
            String command = String.format("update users set message = '%s' where email = '%s';", message, member.getEmail());
            statement.executeUpdate(command);

            successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
    }

    public boolean addMessageToInbox(Message message)
    {
        boolean successful=false;
        String command = String.format("INSERT INTO messages VALUES ('%s', '%s', '%s', '%s');", message.getDate(), message.getSender(), message.getReceiver(), message.getMessage());
        try
        {
            statement.executeUpdate(command);
            successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return  successful;
    }

    public boolean updateMember()
    {
        return false;
    }

    public boolean deleteMember()
    {
        return false;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
