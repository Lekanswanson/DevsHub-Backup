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

    public boolean initDBConnection()
    {
        boolean successful = false;
        try
        {
            Class.forName(driver);
            connection = DriverManager.getConnection(path, userName, passWord);
            statement = connection.createStatement();
            successful = true;
        }
        catch (Exception e)
        {
            System.out.print("Failed to initialise DB Connection\n" + e.getMessage());
        }
        return successful;
    }

    public boolean closeConnection()
    {
        boolean successful = false;
        try
        {
            connection.close();
            statement.close();
            successful = true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
    }

    boolean validateEmail(MemberEmail memberEmail) {
        boolean memberExists = false;
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement("select * from logindetails where email = ?");
            preparedStatement.setString(1, memberEmail.getEmail());

            ResultSet rs = preparedStatement.executeQuery();
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
        PreparedStatement preparedStatement;
        for (Member member : members) {
            try {
                preparedStatement = connection.prepareStatement("select * from education where email = ?");
                preparedStatement.setString(1, member.getEmail());

                ResultSet resultSet = preparedStatement.executeQuery();
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

            try
            {
                preparedStatement = connection.prepareStatement("select * from experience where email = ?");
                preparedStatement.setString(1, member.getEmail());

                ResultSet resultSet = preparedStatement.executeQuery();
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

            try
            {
                preparedStatement = connection.prepareStatement("select * from memberLanguages where email = ?");
                preparedStatement.setString(1, member.getEmail());

                ResultSet resultSet = preparedStatement.executeQuery();
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

            try
            {
                preparedStatement = connection.prepareStatement("select * from projects where email = ?");
                preparedStatement.setString(1, member.getEmail());

                ResultSet resultSet = preparedStatement.executeQuery();
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
        try
        {
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
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("INSERT INTO articleComments VALUE (null, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, comment.getDate());
            preparedStatement.setString(2, comment.getMemberName());
            preparedStatement.setString(3, comment.getComment());
            preparedStatement.setInt(4, articleId);
            preparedStatement.setString(5, member.getImage());
            preparedStatement.setString(6, member.getEmail());

            preparedStatement.executeUpdate();
            successful = true;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return successful;
    }
    boolean updateArticleImage(String path, Member member)
    {
        boolean successful=false;
        PreparedStatement preparedStatement;
        try{
            preparedStatement = connection.prepareStatement("update articleComments set image=? where email=?");
            preparedStatement.setString(1, path);
            preparedStatement.setString(2, member.getEmail());

            preparedStatement.executeUpdate();
            successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
    }

    ArrayList<Comment> getArticleComments()
    {
        ArrayList<Comment> allComments = new ArrayList<>();
        String command = "select * from articleComments;";

        Comment comment;
        try
        {
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
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("select * from articleComments where articleId=?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
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
        String path = "../images/";

        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("select * from users where email=?");
            preparedStatement.setString(1, member.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
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

        try {
            preparedStatement = connection.prepareStatement("select * from education where email = ?");
            preparedStatement.setString(1, member.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
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

        try {
            preparedStatement = connection.prepareStatement("select * from experience where email =?");
            preparedStatement.setString(1, member.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
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

        try {
            preparedStatement = connection.prepareStatement("select * from memberLanguages where email =?");
            preparedStatement.setString(1, member.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
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

        try {
            preparedStatement = connection.prepareStatement("select * from projects where email =?");
            preparedStatement.setString(1, member.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
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

        String command = String.format("select * from messages;");
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

    public boolean setImagePath(String fileName, Member member)
    {
        boolean successful=false;
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("update users set image=? where email = ?");
            preparedStatement.setString(1, fileName);
            preparedStatement.setString(2, member.getEmail());

            preparedStatement.executeUpdate();
            successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
    }

    boolean saveEducation(Member member, Education education)
    {
        boolean successful=false;
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("INSERT INTO education VALUES (null, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, education.getUniversityLocation());
            preparedStatement.setInt(2, education.getYearStarted());
            preparedStatement.setInt(3, education.getYearEnded());
            preparedStatement.setString(4, education.getCourseStudied());
            preparedStatement.setString(5, education.getDegreeLevel());
            preparedStatement.setString(6, education.getResultsAchieved());
            preparedStatement.setString(7, member.getEmail());

            preparedStatement.executeUpdate();
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
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("INSERT INTO experience VALUES (null, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, experience.getCompanyName());
            preparedStatement.setString(2, experience.getJobTitle());
            preparedStatement.setInt(3, experience.getYearStarted());
            preparedStatement.setInt(4, experience.getYearEnded());
            preparedStatement.setString(5, experience.getLocation());
            preparedStatement.setString(6, member.getEmail());

            preparedStatement.executeUpdate();
            successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
    }

    boolean existAlready(MemberEmail email)
    {
        boolean exists=false;
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("select email from logindetails where email = ?");
            preparedStatement.setString(1, email.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
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
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("insert into logindetails values(?, ?)");
            preparedStatement.setString(1, member.getEmail());
            preparedStatement.setString(2, member.getPassWord());
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO users VALUES (null, ?, ?, ?, null, 'person.png', '#cad07c', ?)");
            preparedStatement.setString(1, member.getFirstName());
            preparedStatement.setString(2, member.getLastName());
            preparedStatement.setString(3, member.getLocation());
            preparedStatement.setString(4, member.getEmail());
            preparedStatement.executeUpdate();

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
        PreparedStatement preparedStatement;
        try
        {
            if(message==null)
            {
                preparedStatement = connection.prepareStatement("update users set message=null where email = ?");
                preparedStatement.setString(1, member.getEmail());
            }
            else
            {
                preparedStatement = connection.prepareStatement("update users set message = ? where email = ?;");
                preparedStatement.setString(1, message);
                preparedStatement.setString(2, member.getEmail());
            }
            preparedStatement.executeUpdate();
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
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("INSERT INTO messages VALUES (null, ?, ?, ?, ?, 0);");
            preparedStatement.setString(1, message.getDate());
            preparedStatement.setString(2, message.getSender());
            preparedStatement.setString(3, message.getReceiver());
            preparedStatement.setString(4, message.getMessage());

            preparedStatement.executeUpdate();
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
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("update messages set size=? where id=?");
            preparedStatement.setInt(1, size);
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
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
        try
        {
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


    public boolean removeArticleLike(int id)
    {
        boolean successful=false;
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("update articles set likes=likes-1 where id=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
    }

    public boolean removeMemberLike(Member member, int id)
    {
        boolean successful=false;
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("delete from memberlikes where email=? and articleId=?");
            preparedStatement.setString(1, member.getEmail());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
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
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("update articles set likes=likes+1 where id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

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
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("insert into memberlikes values (?, ?);");
            preparedStatement.setString(1, member.getEmail());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
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
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("select * from memberlikes where email = ? and articleId=?");
            preparedStatement.setString(1, member.getEmail());
            preparedStatement.setInt(2, id);

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next())
                successful=true;
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return successful;
    }
    ArrayList<Like> memberLikedArticles(Member member)
    {
        ArrayList<Like> arrayList = new ArrayList<>();
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("select * from memberlikes where email = ?");
            preparedStatement.setString(1, member.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                Like like = new Like(resultSet.getString("email"), resultSet.getInt("articleId"));
                arrayList.add(like);
            }
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return  arrayList;
    }

    boolean addNewProject(Project project, Member member)
    {
        boolean successful=false;
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("insert into projects values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, project.getTitle());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setString(3, project.getLanguage());
            preparedStatement.setString(4, project.getTechnology());
            preparedStatement.setString(5, project.getVideo());
            preparedStatement.setString(6, member.getEmail());

            preparedStatement.executeUpdate();
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
        StringBuilder emails=new StringBuilder();
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("select email from users where email like ?");
            preparedStatement.setString(1, "%%%"+email+"%%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                emails.append(resultSet.getString("email")+"_");
            }
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return emails.toString();
    }

    public String getLanguageMatching(String language)
    {
        StringBuilder languageNames=new StringBuilder();
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("select languageName from languages where languageName like ?");
            preparedStatement.setString(1, "%%%"+language+"%%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            {
                languageNames.append(resultSet.getString("languageName")+"_");
            }
        }
        catch (SQLException exception)
        {
            System.out.println(exception.getMessage());
        }
        return languageNames.toString();
    }

    public boolean addProgrammingLanguages(Member member, ProgrammingLanguages languages)
    {
        boolean successful=false;
        PreparedStatement preparedStatement;
        try
        {
            preparedStatement = connection.prepareStatement("INSERT INTO memberLanguages VALUES (null, ?, ?, ?);");
            preparedStatement.setString(1, languages.getLanguage());
            preparedStatement.setString(2, languages.getYearsExperience());
            preparedStatement.setString(3, member.getEmail());

            preparedStatement.executeUpdate();
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
