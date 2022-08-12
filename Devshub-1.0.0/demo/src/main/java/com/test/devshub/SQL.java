package com.test.devshub;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

@Component
@Repository
@ConfigurationProperties(prefix = "sqldriver")
public class SQL {
    private Connection connection;
    private Statement statement;
    private String driver;
    private String path;
    private String userName;
    private String passWord;

    public boolean initDBConnection() {
        boolean successful = false;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(path, userName, passWord);
            statement = connection.createStatement();
            successful = true;
        } catch (Exception e) {
            System.out.print("Failed to initialise DB Connection\n" + e.getMessage());
        }
        return successful;
    }

    public boolean closeConnection() {
        boolean successful = false;
        try {
            connection.close();
            statement.close();
            successful = true;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return successful;
    }

    boolean validateEmail(MemberEmail memberEmail) {
        boolean memberExists = false;
        String command = "select * from logindetails where email='" + memberEmail.getEmail() + "'";

        try {
            ResultSet rs = statement.executeQuery(command);
            if (rs.next()) {
                String email = rs.getString("email");
                String password = rs.getString("passcode");

                if (email.trim().equals(memberEmail.getEmail().trim()))
                    if (password.trim().equals(memberEmail.getPassword().trim()))
                        memberExists = true;
            }
        } catch (SQLException sqlDataException) {
            System.out.println(sqlDataException.getMessage());
        }
        return memberExists;
    }

    public void addMemberInformation(ArrayList<Member> members) {
        for (Member member : members) {
            String command = String.format("select * from education where email = '%s';", member.getEmail());
            try {
                ResultSet resultSet = statement.executeQuery(command);
                Education education = new Education();

                while (resultSet.next()) {
                    education.setId(resultSet.getInt("id"));
                    education.setUniversityLocation(resultSet.getString("universityLocation"));
                    education.setYearStarted(resultSet.getInt("yearStarted"));
                    education.setYearEnded(resultSet.getInt("yearEnded"));
                    education.setCourseStudied(resultSet.getString("courseStudied"));
                    education.setDegreeLevel(resultSet.getString("degreeLevel"));
                    education.setResultsAchieved(resultSet.getString("resultsAchieved"));

                    member.setEducation(education);
                }

            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }

            command = String.format("select * from experience where email = '%s';", member.getEmail());
            try {
                ResultSet resultSet = statement.executeQuery(command);
                Experience experience = new Experience();

                while (resultSet.next()) {
                    experience.setId(resultSet.getInt("id"));
                    experience.setCompanyName(resultSet.getString("companyName"));
                    experience.setJobTitle(resultSet.getString("jobTitle"));
                    experience.setYearStarted(resultSet.getInt("yearStarted"));
                    experience.setYearEnded(resultSet.getInt("yearEnded"));
                    experience.setLocation(resultSet.getString("location"));

                    member.setExperiences(experience);
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }

            command = String.format("select * from memberLanguages where email = '%s';", member.getEmail());
            try {
                ResultSet resultSet = statement.executeQuery(command);
                ArrayList<ProgrammingLanguages> langArray = new ArrayList<>();
                if (resultSet.next()) {
                    ProgrammingLanguages languages = new ProgrammingLanguages(resultSet.getString("languageName"), resultSet.getString("yearsExperience"));

                    String[] lang = languages.getLanguage().split("_");
                    String[] exp = languages.getYearsExperience().split("_");

                    int length = lang.length;
                    for (int i = 0; i < length; i++) {
                        ProgrammingLanguages pl = new ProgrammingLanguages(lang[i], exp[i]);
                        langArray.add(pl);
                    }
                    member.setProgrammingLanguages(langArray);
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }

            command = String.format("select * from projects where email = '%s';", member.getEmail());
            try {
                ResultSet resultSet = statement.executeQuery(command);
                Project project = new Project();

                while (resultSet.next()) {
                    project.setTitle(resultSet.getString("title"));
                    project.setDescription(resultSet.getString("details"));
                    project.setLanguage(resultSet.getString("lang"));
                    project.setTechnology(resultSet.getString("technology"));
                    project.setVideo(resultSet.getString("video"));

                    String[] p = project.getLanguage().split("_");
                    if (p.length > 0) {
                        for (String i : p) {
                            project.setLanguages(i.trim());
                        }
                    }
                    String[] t = project.getTechnology().split("_");
                    if (t.length > 0) {
                        for (String i : t) {
                            project.setTechnologies(i.trim());
                        }
                    }
                    member.addProject(project);
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    ArrayList<Member> getMembers() {
        ArrayList<Member> members = new ArrayList<>();
        String path = "../images/";

        String command = "select * from users;";

        Member member;
        try {
            ResultSet resultSet = statement.executeQuery(command);
            while (resultSet.next()) {
                member = new Member(new MemberEmail(), new ArrayList<>(), new ArrayList(), new ArrayList(), new HashMap<>());

                member.setFirstName(resultSet.getString("firstName"));
                member.setLastName(resultSet.getString("lastName"));
                member.setLocation(resultSet.getString("location"));
                member.setMessage((resultSet.getString("message")));
                member.setImage(path + resultSet.getString("image"));
                member.setColor(resultSet.getString("color"));
                member.setEmail(resultSet.getString("email"));

                members.add(member);
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        addMemberInformation(members);
        return members;
    }

    boolean addArticleComment(Comment comment, int articleId, Member member) {
        boolean successful = false;
        String command = String.format("INSERT INTO articleComments VALUE (null, '%s', '%s', '%s', '%d', '%s');", comment.getDate(), comment.getMemberName(), comment.getComment(), articleId, member.getImage());
        try {
            statement.executeUpdate(command);
            successful = true;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return successful;
    }

    ArrayList<Comment> getArticleComments()
    {
        ArrayList<Comment> allComments = new ArrayList<>();
        String command = "select * from articleComments;";

        Comment comment;

        try{
            ResultSet resultSet = statement.executeQuery(command);
            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String date = resultSet.getString("dt");
                String fullName = resultSet.getString("email");
                String userComment = resultSet.getString("userComment");
                int articleId = resultSet.getInt("articleId");
                String image = resultSet.getString("image");

                comment = new Comment(id, fullName, userComment, articleId, image);
                comment.setDate(date);
                allComments.add(comment);
            }
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return allComments;
    }

    int countComments(int id)
    {
        int count=0;
        String command = String.format("select * from articleComments where articleId='%d';", id);

        try {
            Statement statement1 = connection.createStatement();
            ResultSet resultSet=statement1.executeQuery(command);
            while (resultSet.next())
            {
                count++;
            }
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return count;
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
            while (resultSet.next())
            {
                Education education = new Education();
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
            while (resultSet.next())
            {
                Experience experience = new Experience();
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

        command = String.format("select * from memberLanguages where email = '%s';", member.getEmail());
        try {
            ResultSet resultSet = statement.executeQuery(command);
            ArrayList<ProgrammingLanguages> langArray = new ArrayList<>();
            if (resultSet.next())
            {
                ProgrammingLanguages languages = new ProgrammingLanguages(resultSet.getString("languageName"), resultSet.getString("yearsExperience"));

                String[] lang = languages.getLanguage().split("_");
                String[] exp = languages.getYearsExperience().split("_");

                int length=lang.length;
                for(int i=0; i<length; i++)
                {
                    ProgrammingLanguages pl = new ProgrammingLanguages(lang[i], exp[i]);
                    langArray.add(pl);
                }
                member.setProgrammingLanguages(langArray);
            }
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }

        command = String.format("select * from projects where email = '%s';", member.getEmail());
        try {
            ResultSet resultSet = statement.executeQuery(command);
            while (resultSet.next())
            {
                Project project = new Project();
                project.setTitle(resultSet.getString("title"));
                project.setDescription(resultSet.getString("details"));
                project.setLanguage(resultSet.getString("lang"));
                project.setTechnology(resultSet.getString("technology"));
                project.setVideo(resultSet.getString("video"));

                String[] p = project.getLanguage().split("_");
                if(p.length > 0)
                {
                    for(String i : p)
                    {
                        project.setLanguages(i.trim());
                    }
                }
                String[] t = project.getTechnology().split("_");
                if(t.length > 0)
                {
                    for(String i : t)
                    {
                        project.setTechnologies(i.trim());
                    }
                }
                member.addProject(project);
            }
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }

        command = String.format("select * from messages;");
        try {
            ResultSet rs = statement.executeQuery(command);
            while (rs.next())
            {
                ArrayList<Message> inbox;

                int id=rs.getInt("id");
                String sender = rs.getString("sender");
                String receiver = rs.getString("receiver");
                String message = rs.getString("message");
                int size = rs.getInt("size");

                Message m = new Message(sender, receiver, message, "show", "hide");
                m.setSize(size);
                m.setId(id);

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
        return member;
    }

    public boolean setImagePath(String fileName, Member member) {
        boolean successful=false;
        String command = String.format("update users set image='%s' where email = '%s';", fileName, member.getEmail());
        try
        {
            statement.executeUpdate(command);
            successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
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
        String command = String.format("select email from logindetails where email='%s';", email.getEmail());
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
            String command;
            if(message==null)
            {
                command = String.format("update users set message=null where email = '%s';", member.getEmail());
            }
            else
            {
                command = String.format("update users set message = '%s' where email = '%s';", message, member.getEmail());
            }
            statement.executeUpdate(command);
            successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
    }

    boolean addMessageToInbox(Message message)
    {
        boolean successful=false;
        String command = String.format("INSERT INTO messages VALUES (null, '%s', '%s', '%s', '%s', 0);", message.getDate(), message.getSender(), message.getReceiver(), message.getMessage());
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
    boolean setSize(int size, int id)
    {
        boolean successful=false;
        String command = String.format("update messages set size=%d where id=%d;", size, id);
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

    int getMessagesSize()
    {
        int size=0;
        String command = "select * from messages;";
        try{
            ResultSet rs = statement.executeQuery(command);

            while (rs.next())
            {
                size++;
            }
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
        return size;
    }
    ArrayList<Articles> getArticles()
    {
        Articles article;
        ArrayList<Articles> articles;

        articles = new ArrayList<>();
        try{
            String command = "select * from articles;";
            ResultSet resultSet = statement.executeQuery(command);

            while(resultSet.next())
            {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("details");
                String url = resultSet.getString("url");
                int likes = resultSet.getInt("likes");

                int count = countComments(id);

                article = new Articles(id, title, description, url, likes);
                article.setCount(count);
                articles.add(article);
            }
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return articles;
    }


    public boolean removeArticleLike(int id) {
        boolean successful=false;

        String command = String.format("update articles set likes=likes-1 where id=%d;", id);
        try
        {
            statement.executeUpdate(command);
            successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
    }

    public boolean removeMemberLike(Member member, int id) {
        boolean successful=false;
        String command = String.format("delete from memberlikes where email='%s' and articleId=%d;", member.getEmail(), id);
        try
        {
            statement.executeUpdate(command);
            successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
    }


    boolean addArticleLike(int id)
    {
        boolean successful = false;
        String command = String.format("update articles set likes=likes+1 where id=%d;", id);

        try{
            statement.executeUpdate(command);
            successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
    }
    boolean addMemberLike(Member member, int id)
    {
        boolean successful = false;
        String command = String.format("insert into memberlikes values ('%s', '%d');", member.getEmail(), id);
        try{
            statement.executeUpdate(command);
            successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
    }

    boolean memberHasLikedArticles(Member member, int id)
    {
        boolean successful=false;
        String command = String.format("select * from memberlikes where email = '%s' and articleId=%d;", member.getEmail(), id);

        try {
            ResultSet rs = statement.executeQuery(command);
            if(rs.next())
                successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
    }

    boolean addNewProject(Project project, Member member){
        boolean successful=false;
        String command = String.format("insert into projects values ('%s', '%s', '%s', '%s', '%s', '%s');",
                project.getTitle(), project.getDescription(), project.getLanguage(), project.getTechnology(), project.getVideo(), member.getEmail());
        try {
            statement.executeUpdate(command);
            successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
    }

    public String getMembersMatching(String email)
    {
        String command = String.format("select email from users where email like '%%%s%%' ;", email);
        StringBuilder emails=new StringBuilder();
        try {
            ResultSet resultSet = statement.executeQuery(command);
            while (resultSet.next())
            {
                emails.append(resultSet.getString("email")+"_");
            }
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        System.out.println(emails.toString());
        return emails.toString();
    }

    public String getLanguageMatching(String language)
    {
        String command = String.format("select languageName from languages where languageName like '%%%s%%';", language);
        StringBuilder languageNames=new StringBuilder();
        try {
            ResultSet resultSet = statement.executeQuery(command);
            while (resultSet.next())
            {
                languageNames.append(resultSet.getString("languageName")+"_");
            }
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        System.out.println(languageNames.toString());
        return languageNames.toString();
    }

    public boolean addProgrammingLanguages(Member member, ProgrammingLanguages languages)
    {
        boolean successful=false;
        String command = String.format("INSERT INTO memberLanguages VALUES (null, '%s', '%s', '%S');", languages.getLanguage(), languages.getYearsExperience(), member.getEmail());
        try {
            statement.executeUpdate(command);
            successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
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
