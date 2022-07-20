package com.test.devshub;

import org.springframework.stereotype.Repository;
import javax.inject.Singleton;
import java.util.ArrayList;

@Singleton
@Repository
public class OnlineUsers
{
    private ArrayList<Member> onlineUsers;

    public void init()
    {
        onlineUsers=new ArrayList<>();
    }

    public OnlineUsers()
    {
        init();
    }
    public void addMember(Member member)
    {
        onlineUsers.add(member);
    }

    public void removeMember(Member mem) {
        Member deleteMember=null;

        if(onlineUsers!=null)
        {
            for(Member member : onlineUsers)
            {
                if(member.getEmail().equals(mem.getEmail()))
                {
                    deleteMember=member;
                }
            }
            onlineUsers.remove(deleteMember);
        }
    }
    public Member getMember(String email)
    {
        Member member=null;
        for(Member m : onlineUsers)
        {
            if(m.getEmail().equals(email))
                member=m;
        }
        return member;
    }

}
