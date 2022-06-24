package com.test.devshub;

public class Like
{
    String memberName;
    int likes;

    public Like()
    {

    }

    public  Like(String memberName, int likes)
    {
        this.memberName=memberName;
        this.likes=likes;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
