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

    static int memberCount=0;
    static int ID=0;

    static
    {
        Member admin = new Member(new MemberEmail("admin", "password"), new ArrayList<>());
        Member user1 = new Member(new MemberEmail("laykon", "password"), new ArrayList<>());
        Member user2 = new Member(new MemberEmail("public", "public"), new ArrayList<>());

        admin.setFirstName("admin");
        admin.setLastName("null");
        admin.setLocation("null");

        members.add(admin);
        members.add(user1);
        members.add(user2);
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
            members.add(new Member(email, new ArrayList<>()));
        }
        return exists;
    }
}
