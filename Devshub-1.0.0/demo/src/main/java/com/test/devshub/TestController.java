package com.test.devshub;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;


@Controller
@SessionScope
public class TestController
{
    @Autowired
    private Member member;
    @Autowired
    private MemberEmail email;
    @Autowired
    private Education education;

    @RequestMapping(method = RequestMethod.GET, path="/devshub")
    public String foo(Model model)
    {
        model.addAttribute("memberEmail", new MemberEmail());
        return "foo";
    }

    @RequestMapping(method = RequestMethod.POST, path="/register")
    public String registerMember(@ModelAttribute Member m, Model model)
    {
        member.setFirstName(m.getFirstName());
        member.setLastName(m.getLastName());
        member.setLocation(m.getLocation());
        model.addAttribute("member", member);
        return "redirect:/home/"+member.getFirstName();
    }

    @RequestMapping(method = RequestMethod.POST, path="/saveMessage")
    public String saveMessage(@ModelAttribute Member m, Model model)
    {
        if(m.getMessage().isEmpty())
           member.setMessage(null);
        else
            member.setMessage(m.getMessage());
        model.addAttribute("member", member);
        return "redirect:/home/"+member.getFirstName();
    }

    @RequestMapping(path="/newaccount")
    public String newAccount(Model model)
    {
        model.addAttribute("newEmail", new MemberEmail());
        return "newaccount";
    }

    @RequestMapping(path="/passwordreset")
    public String resetPass(Model model)
    {
        return "passwordreset";
    }


    @RequestMapping(method = RequestMethod.POST, path = "/next")
    public String next(@ModelAttribute MemberEmail memberEmail, Model model)
    {
        if(MemberDB.existAlready(memberEmail))
        {
            System.out.println(memberEmail.getEmail() + " already exists! Sign in instead;");
            return "redirect:/newaccount";
        }
        else
        {
            member = MemberDB.getMember(memberEmail.getEmail());
            model.addAttribute("member", member);
            return "nextpage";
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/saveEducation")
    public String saveEducationInformation(@ModelAttribute Education edu, Model model)
    {
        member.setEducation(edu);
        model.addAttribute("member", member);
        model.addAttribute("education", new Education());
        return "redirect:/home/"+member.getFirstName();
    }

    @RequestMapping(method=RequestMethod.GET, path="/home/{user}")
    public String home(Model model)
    {
        model.addAttribute("member", member);
        model.addAttribute("myed", education);
        model.addAttribute("education", new Education());
        return "home";
    }
    @RequestMapping(method = RequestMethod.POST, path = "/profile")
    public String signIn(@ModelAttribute MemberEmail memberEmail, Model model)
    {
        if(MemberDB.validate(memberEmail))
        {
            member = MemberDB.getMember(memberEmail.getEmail());
            return "redirect:/home/"+member.getFirstName();
        }
        else
        {
            System.out.println("Login  Failed");
            return "redirect:/devshub";
        }
    }

    //don't use gets
    @RequestMapping(method = RequestMethod.GET, path = "/user/cv")
    public String returnHome(@ModelAttribute MemberEmail memberEmail, Model model)
    {
        return "redirect:/home/"+member.getFirstName();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/editEducation")
    public String editEducation(@ModelAttribute Education education, Model model)
    {
        System.out.println(education);
        model.addAttribute("member", member);
        return "redirect:/home/"+member.getFirstName();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/profile")
    public String returnProfile(Model model)
    {
        model.addAttribute("member", member);
        return "profile";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/projects")
    public String returnProjects(Model model)
    {
        model.addAttribute("member", member);
        return "projects";
    }


    @RequestMapping(method = RequestMethod.GET, path = "/educationSaved")
    public String saveEducations(Education education, Model model)
    {
        model.addAttribute("listOfUserEducations", new ArrayList<Education>().add(education));
        return "educations";
    }
}