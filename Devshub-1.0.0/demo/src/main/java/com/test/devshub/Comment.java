package com.test.devshub;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Date;

@Component
@RequestScope
public class Comment
{
    private int id;
    private String date=new Date().toString();
    private String memberName;
    private String comment;
    private int articleId;
    private String image;
    public Comment(){}
    public Comment(int id, String memberName, String comment, int articleId, String image)
    {
        this.id=id;
        this.memberName=memberName;
        this.comment=comment;
        this.articleId=articleId;
        this.image=image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString()
    {
        return String.format("%d,%s,%s,%s,%s", id,date,memberName,comment,image);
    }
}