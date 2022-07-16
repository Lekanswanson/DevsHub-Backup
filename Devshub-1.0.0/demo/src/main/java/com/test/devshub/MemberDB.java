package com.test.devshub;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import javax.validation.constraints.Email;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Repository
@SessionScope
public class MemberDB
{
    static List<Member> members = new ArrayList<Member>();
    static ArrayList<Articles> articles = new ArrayList<>();

    static ArrayList<Message> inbox = new ArrayList<>();
    static Message message;
    static int ID=0;
    static int expID=0;
    static String path ="../images/";
    static String title="What is Maven: Here's What You Need to Know";
    static String description="Interactive maven tutorial";
    static String url = "https://www.simplilearn.com/tutorials/maven-tutorial/what-is-maven";

    static String sender ="null";


    static
    {
        Member admin = new Member(new MemberEmail("admin", ""), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap());
        Member user1 = new Member(new MemberEmail("joe", ""), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap());
        Member user2 = new Member(new MemberEmail("kev", ""), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap());
        Member user3 = new Member(new MemberEmail("tom", ""), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap());

        admin.setFirstName("Lekan");
        admin.setLastName("Adams");
        admin.setLocation("Athlone Westmeath");
        admin.setImage(path+"person.png");
        admin.setColor("#cad07c");

        message=new Message();

        user1.setFirstName("Joe");
        user1.setLastName("Rogan");
        user1.setLocation("Denver Colorado");
        user1.setImage(path+"person.png");
        user1.setColor("#cad07c");

        user2.setFirstName("Kevin");
        user2.setLastName("Thomas");
        user2.setLocation("Toronto Ontario");
        user2.setImage(path+"person.png");
        user2.setColor("#cad07c");

        user3.setFirstName("Thomas");
        user3.setLastName("Party");
        user3.setLocation("London UK");
        user3.setImage(path+"person.png");
        user3.setColor("#cad07c");



        members.add(admin);
        members.add(user1);
        members.add(user2);
        members.add(user3);

        articles.add(new Articles(title, description, url,0));
        articles.add(new Articles("Docker", "Docker is an open source platform for building, deploying, and managing containerized applications." +
                "Learn about containers, how they compare to VMs, and why Docker is so widely adopted and used.",
                "https://www.ibm.com/cloud/learn/docker", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
        articles.add(new Articles("What is Jenkins? The CI server explained",
                "Jenkins offers a simple way to set up a continuous integration and continuous delivery environment" +
                        " for almost any combination of languages and source code repositories",
                "https://www.infoworld.com/article/3239666/what-is-jenkins-the-ci-server-explained.html", 0));
    }

    public static List<Member> getMembers()
    {
        return members;
    }

    public static void addMember(Member member)
    {
        members.add(member);
    }

    public static Member getMember(String email)
    {
        Member member=null;
        for(Member m : members)
        {
            if(m.getEmail().equals(email))
                member=m;
        }
        return member;
    }

    public static void deleteMember(Member mem)
    {
        Member deleteMember=null;
        for(Member member : members)
        {
            if(member.getEmail().equals(mem.getEmail()))
            {
                System.out.println(member);
                deleteMember=member;
            }
        }
        members.remove(deleteMember);
    }

    public static void updateMember(Member member)
    {
        for(Member m : members)
        {
            if(m.getEmail()==member.getEmail())
            {
                if(!member.getFirstName().isEmpty())
                    m.setFirstName(member.getFirstName());
                if(!member.getLastName().isEmpty())
                    m.setLastName(member.getLastName());
            }
        }
    }

    public static boolean validate(MemberEmail memberEmail)
    {
        boolean memberExists=false;
        for(Member m : members)
        {
            if(m.getEmail().equals(memberEmail.getEmail().trim()) && m.getPassWord().equals(memberEmail.getPassword().trim()))
                memberExists=true;
        }
        return memberExists;
    }

    public static boolean existAlready(MemberEmail email) {
        boolean exists=false;
        for(Member member : members)
        {
            if(member.getEmail().equals(email.getEmail()))
            {
                exists=true;
                break;
            }
        }
        if(!exists)
        {
            System.out.println("Adding New Member");
            members.add(new Member(email, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap()));
        }
        return exists;
    }
}
