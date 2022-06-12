package com.test.devshub;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Component
@RequestScope
public class MemberEmail
{
    protected String email;
    protected String password;

    public MemberEmail(){}
    public MemberEmail(String email, String password) {
        this.email = email;
        this.password=password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return String.format("[%s] %s", email, password);
    }
}
