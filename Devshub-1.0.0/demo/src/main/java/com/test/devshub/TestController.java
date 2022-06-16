package com.test.devshub;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
    @Autowired
    private Experience experience;


    private final String UPLOAD_DIR = "C:\\Users\\adams\\IdeaProjects\\demo\\src\\main\\resources\\static\\images\\";
    private final String VID_UPLOAD_DIR = "C:\\Users\\adams\\IdeaProjects\\demo\\src\\main\\resources\\static\\videos\\";
    private final String CLASS_DIR = "C:\\Users\\adams\\IdeaProjects\\demo\\target\\classes\\static\\videos\\";

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


    @RequestMapping(method = RequestMethod.POST, path = "/saveExperience")
    public String saveExperience(@ModelAttribute Experience experience, Model model)
    {
        member.setExperiences(experience);
        model.addAttribute("member", member);
        model.addAttribute("experience", new Experience());
        return "redirect:/home/"+member.getFirstName();
    }

    @RequestMapping(method=RequestMethod.GET, path="/home/{user}")
    public String home(Model model)
    {
        model.addAttribute("member", member);
        model.addAttribute("myed", education);
        model.addAttribute("experience", experience);
        model.addAttribute("newExperience", new Experience());
        model.addAttribute("education", new Education());
        return "home";
    }
    @RequestMapping(method = RequestMethod.POST, path = "/profile")
    public String signIn(@ModelAttribute MemberEmail memberEmail, Model model)
    {
        if(MemberDB.validate(memberEmail))
        {
            member = MemberDB.getMember(memberEmail.getEmail());
            model.addAttribute("member", member);
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

    @RequestMapping(method = RequestMethod.POST, path = "/editExperience")
    public String editExperience(@ModelAttribute Experience experience, Model model)
    {
        System.out.println(experience);
        model.addAttribute("member", member);
        return "redirect:/home/"+member.getFirstName();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/profile")
    public String returnProfile(Model model)
    {
        model.addAttribute("article", MemberDB.articles.get(0));
        model.addAttribute("member", member);
        return "profile";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/projects")
    public String returnProjects(Model model)
    {
        model.addAttribute("member", member);
        return "projects";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/user/save/image")
    public String uploadFileCv(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws InterruptedException {

        // check if file is empty
        if (file.isEmpty()) {
            System.out.println("No file");
            return "redirect:/home/"+member.getFirstName();
        }
        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // save the file on the local file system
        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path ="../images/"+fileName;

        // return success response
        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');
        member.setImage(path);

        return "redirect:/home/"+member.getFirstName();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/profile/save/image")
    public String uploadFileProfile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws InterruptedException {

        // check if file is empty
        if (file.isEmpty()) {
            System.out.println("No file");
            return "redirect:/user/profile";
        }

        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // save the file on the local file system
        try {
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path ="../images/"+fileName;

        // return success response
        attributes.addFlashAttribute("message", "You successfully uploaded " + fileName + '!');
        member.setImage(path);
        return "redirect:/user/profile";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/projects/save/video")
    public String uploadVideo(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) throws InterruptedException {
        // check if file is empty
        if (file.isEmpty()) {
            System.out.println("No file");
            return "redirect:/user/projects";
        }
        // normalize the file path
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // save the file on the local file system
        try(InputStream inputStream = file.getInputStream()) {
            Path path = Paths.get(VID_UPLOAD_DIR + fileName);
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

            File directory = new File(CLASS_DIR);
            if (! directory.exists()) {
                directory.mkdir();
                System.out.println("Making Dir");
            }
            Path classPath = Paths.get(CLASS_DIR + fileName);
            Files.copy(path, classPath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String part="../videos/"+fileName;
        System.out.println(file.getSize() + " <---[]---> " + part);

        member.setVideo(part);
        return "redirect:/user/projects";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/educationSaved")
    public String saveEducations(Education education, Model model)
    {
        model.addAttribute("listOfUserEducations", new ArrayList<Education>().add(education));
        return "educations";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/about")
    public String getAboutPage(Model model)
    {
        model.addAttribute("member", member);
        return "about";
    }
}