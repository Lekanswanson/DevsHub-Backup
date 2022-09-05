package com.test.devshub;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;

@Component
@RequestScope
public class Like
{
    private String memberName;
    private int id;

    public Like()
    {

    }

    public  Like(String memberName, int id)
    {
        this.memberName=memberName;
        this.id=id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return String.format("%s,%d",memberName,id);
    }
}
