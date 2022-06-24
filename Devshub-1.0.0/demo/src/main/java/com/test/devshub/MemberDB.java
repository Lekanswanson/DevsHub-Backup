package com.test.devshub;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Repository
@SessionScope
public class MemberDB
{
    static List<Member> members = new ArrayList<Member>();
    static ArrayList<Articles> articles = new ArrayList<>();

    static ArrayList<Project> projects = new ArrayList<>();

    static int memberCount=0;
    static int ID=0;

    static int expID=0;

    static String path ="../images/";
    static String videoPath="../videos/";


    static String title="What is Maven: Here's What You Need to Know";
    static String description="Interactive maven tutorial";
    static String url = "https://www.simplilearn.com/tutorials/maven-tutorial/what-is-maven";

    static
    {
        Member admin = new Member(new MemberEmail("admin", "password"), new ArrayList<>(), new ArrayList<>());
        Member user1 = new Member(new MemberEmail("laykon", "password"), new ArrayList<>(), new ArrayList<>());
        Member user2 = new Member(new MemberEmail("public", "public"), new ArrayList<>(), new ArrayList<>());

        admin.setFirstName("admin");
        admin.setLastName("null");
        admin.setLocation("null");
        admin.setImage(path+"person.png");
        admin.setVideo("");
        admin.setColor("#cad07c");

        members.add(admin);
        members.add(user1);
        members.add(user2);

        articles.add(new Articles(title, description, url,0));
        articles.add(new Articles("Docker", "Docker is an open source platform for building, deploying, and managing containerized applications." +
                "Learn about containers, how they compare to VMs, and why Docker is so widely adopted and used.",
                "https://www.ibm.com/cloud/learn/docker", 0));

        projects.add(new Project("", "", "", "", ""));
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
            members.add(new Member(email, new ArrayList<>(), new ArrayList<>()));
        }
        return exists;
    }
}
