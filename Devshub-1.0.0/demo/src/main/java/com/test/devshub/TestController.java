package com.test.devshub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;


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
    @Autowired
    private SimpleSender sender;
    @Autowired
    private Message message;
    @Autowired
    private SQL database;
    @Autowired
    private OnlineUsers onlineUsers;
    private ArrayList<Message> messages;
    private ArrayList<Articles> articles;

    private String path ="../images/";
    private String videoPath ="../videos/";

    private final String UPLOAD_DIR = "C:\\Users\\adams\\IdeaProjects\\demo\\src\\main\\resources\\static\\images\\";
    private final String VID_UPLOAD_DIR = "C:\\Users\\adams\\IdeaProjects\\demo\\src\\main\\resources\\static\\videos\\";
    private final String CLASS_DIR = "C:\\Users\\adams\\IdeaProjects\\demo\\target\\classes\\static\\videos\\";


    @RequestMapping(method = RequestMethod.GET, path="/devshub")
    public String foo(Model model)
    {
        model.addAttribute("memberEmail", new MemberEmail());
        return "foo";
    }
    @RequestMapping(method = RequestMethod.GET, path="/logout")
    public String logout(Model model)
    {
        onlineUsers.removeMember(member);
        member=null;
        return "redirect:/devshub";
    }

    @RequestMapping(method = RequestMethod.POST, path="/register")
    public String registerMember(@ModelAttribute Member m, Model model)
    {
        member.setFirstName(m.getFirstName());
        member.setLastName(m.getLastName());
        member.setLocation(m.getLocation());
        member.setImage(path+"person.png");
        member.setColor("#cad07c");

        database.addMember(member);
        onlineUsers.addMember(member);

        model.addAttribute("member", member);
        return "redirect:/home/"+member.getFirstName();
    }

    @RequestMapping(method = RequestMethod.POST, path="/saveMessage")
    public String saveMessage(@ModelAttribute Member m, Model model)
    {
        if(m.getMessage().isEmpty())
        {
            member.setMessage(null);
            database.setMessage(member, null);
        }
        else
        {
            member.setMessage(m.getMessage());
            database.setMessage(member, m.getMessage());
        }
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
        if(database.existAlready(memberEmail))
        {
            System.out.println(memberEmail.getEmail() + " already exists! Sign in instead;");
            return "redirect:/newaccount";
        }
        else
        {
            member = new Member(memberEmail, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new HashMap());
            model.addAttribute("member", member);
            return "nextpage";
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/saveEducation")
    public String saveEducationInformation(@ModelAttribute Education edu, Model model)
    {
        member.setEducation(edu);
        database.saveEducation(member, edu);

        model.addAttribute("member", member);
        model.addAttribute("education", new Education());
        return "redirect:/home/"+member.getFirstName();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/saveExperience")
    public String saveExperience(@ModelAttribute Experience experience, Model model)
    {
        member.setExperiences(experience);
        database.saveExperience(member, experience);

        model.addAttribute("member", member);
        model.addAttribute("experience", new Experience());
        return "redirect:/home/"+member.getFirstName();
    }

    @RequestMapping(method=RequestMethod.GET, path="/home/{user}")
    public String home(Model model)
    {
        model.addAttribute("color", member.getColor());
        model.addAttribute("member", member);
        model.addAttribute("myed", education);
        model.addAttribute("experience", experience);
        model.addAttribute("newExperience", new Experience());
        model.addAttribute("education", new Education());
        model.addAttribute("pl", new ProgrammingLanguages());
        return "home";
    }
    @RequestMapping(method = RequestMethod.POST, path = "/profile")
    public String signIn(@ModelAttribute MemberEmail memberEmail, Model model)
    {
        if(database.validateEmail(memberEmail))
        {
            member = database.getMember(memberEmail);
            onlineUsers.addMember(member);
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

    @RequestMapping(method = RequestMethod.GET, path = "/user/projects")
    public String returnProjects(Model model)
    {
        model.addAttribute("project", new Project());
        model.addAttribute("myprojects", member.getProjects());
        model.addAttribute("member", member);
        return "projects";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/user/save/image")
    public String uploadFileCv(@RequestParam("file") MultipartFile file) throws InterruptedException {

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
        member.setImage(path);
        database.setImagePath(fileName, member);

        return "redirect:/home/"+member.getFirstName();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/profile/save/image")
    public String uploadFileProfile(@RequestParam("file") MultipartFile file) throws InterruptedException {

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

        member.setImage(path);
        return "redirect:/user/profile";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/projects/save/project")
    public String addNewProject(@RequestParam("file") MultipartFile file, @ModelAttribute Project project) throws InterruptedException
    {
        if(!project.getVideo().isEmpty()) {
            project.setVideo(videoPath+project.getVideo());
        }

        String[] p = project.getLanguage().split("_");
        if(p.length > 0)
        {
            for(String i : p)
            {
                project.setLanguages(i.trim());
            }
        }
        String[] t = project.getTechnology().split("_");
        if(t.length > 0)
        {
            for(String i : t)
            {
                project.setTechnologies(i.trim());
            }
        }
        member.addProject(project);
        database.addNewProject(project, member);

        if (file.isEmpty()) {
            return "redirect:/user/projects";
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try(InputStream inputStream = file.getInputStream()) {
            Path path = Paths.get(VID_UPLOAD_DIR + fileName);
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

            File directory = new File(CLASS_DIR);
            if (!directory.exists()) {
                directory.mkdir();
                System.out.println("Making Dir");
            }
            Path classPath = Paths.get(CLASS_DIR + fileName);
            Files.copy(path, classPath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/user/projects";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/educationSaved")
    public String saveEducations(Education education, Model model)
    {
        model.addAttribute("listOfUserEducations", new ArrayList<Education>().add(education));
        return "educations";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/about")
    public String getAboutPage(Model model)
    {
        model.addAttribute("member", member);
        return "about";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/members")
    public String getMembersList(Model model)
    {
        ArrayList<Member> members = database.getMembers();
        int exp=0;
        ProgrammingLanguages pl=null;
        for(Member m : members)
        {
            for(ProgrammingLanguages p : m.getLanguages())
            {
                if(Integer.parseInt(p.getYearsExperience()) > exp)
                {
                    exp = Integer.parseInt(p.getYearsExperience());
                    pl=p;
                }
            }
            System.out.println(m.getEmail() + "<-->" + pl);
            pl=null;
            exp=0;
        }
        model.addAttribute("allMembers", members);
        return "members";
    }


    @RequestMapping(method = RequestMethod.GET, path = "/user/learn")
    public String getLearnPage(Model model)
    {
        model.addAttribute("member", member);
        return "learn";
    }


    @RequestMapping(method = RequestMethod.GET, path = "/user/profile")
    public String returnProfile(Model model)
    {
        articles = database.getArticles();

        model.addAttribute("articles", articles);
        model.addAttribute("member", member);
        model.addAttribute("color", member.getColor());
        return "profile";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/article/addlike")
    public String likeArticle(Model model, @RequestParam("id") int id)
    {
        for(Articles a : articles)
        {
            if(a.getId()==id)
                a.setLikes(a.getLikes()+1);
        }

        if(database.memberHasLikedArticles(member, id))
        {
            System.out.println("Removing");
            database.removeArticleLike(id);
            database.removeMemberLike(member, id);
        }
        else
        {
            System.out.println("Adding");
            database.addArticleLike(id);
            database.addMemberLike(member, id);
        }
        return "redirect:/user/profile";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/user/setcolor")
    public String changeBackground(Model model, @ModelAttribute Member member)
    {
        this.member.setColor(member.getColor());
        return "redirect:/home/"+this.member.getFirstName();
    }
    @RequestMapping(method = RequestMethod.POST, path = "/user/setcolor/profile")
    public String changeBackgroundProfile(Model model, @ModelAttribute Member member)
    {
        this.member.setColor(member.getColor());
        return "redirect:/user/profile";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/messages")
    public String getMessagesView(Model model)
    {
        model.addAttribute("member", member);
        model.addAttribute("senderName", member.getEmail());

        Map<String, ArrayList<Message>> m = member.getMessages();
        ArrayList<String> allusers = new ArrayList<>();
        ArrayList<Message> msgs = null;
        String defaultUser = null;

        for(String key : m.keySet())
        {
            allusers.add(key);
        }

        try
        {
            defaultUser = allusers.get(0);
            msgs = m.get(defaultUser);
            allusers.remove(0);
        }
        catch (Exception indexNotFoundException)
        {
            System.out.println(indexNotFoundException.getMessage());
        }

        model.addAttribute("mems", database.getMembers());
        model.addAttribute("user", defaultUser);
        model.addAttribute("userMsg", msgs);
        model.addAttribute("allusers", allusers);
        model.addAttribute("map", m);
        model.addAttribute("newMessage", new Message());
        model.addAttribute("message", message);
        model.addAttribute("showclass", MemberDB.message.getShowClass());
        model.addAttribute("hideclass", MemberDB.message.getHideClass());
        model.addAttribute("messages", messages);
        return "messages";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/user/sendMessage")
    public String sendMessage(Model model, @ModelAttribute Message message)
    {
        sender.sendSimpleMessage(message);
        return "redirect:/user/messages";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/createMessage")
    @ResponseBody
    public String createMessage(Model model, @RequestParam("sender") String sender,@RequestParam("receiver") String receiver,@RequestParam("message") String message)
    {
        Message m = new Message(sender,receiver,message,"show","hide");
        database.addMessageToInbox(m);
        sendMessage(model, m);
        return m.toString();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/list/members")
    @ResponseBody
    public String getUsersMatching(Model model, @RequestParam("email") String email)
    {
        if(email.trim().isEmpty())
            return null;
        String emails = database.getMembersMatching(email);
        return emails;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/list/languages")
    @ResponseBody
    public String getLanguageMatching(Model model, @RequestParam("language") String language)
    {
        System.out.println(language);
        if(language.trim().isEmpty())
            return null;
        String languageMatching = database.getLanguageMatching(language);
        return languageMatching;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/user/changeClass")
    public String changeClass(Model model, @ModelAttribute Message message)
    {
        messages = member.getMessages().get(message.getReceiver());
        System.out.println(message.getReceiver());
        messages.forEach(a -> System.out.println(a));

        if(this.message.getShowClass().equals("show"))
        {
            MemberDB.message.setShowClass("hide");
            MemberDB.message.setHideClass("show");
        }
        return "redirect:/user/messages";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/receiver/inbox")
    @ResponseBody
    public String getReceiverInbox(Model model)
    {
        String message=null;
        try
        {
            for(String name : member.getMessages().keySet())
            {
                int currentSize=0;
                int oldSize=0;

                try{
                    currentSize = member.getMessages().get(name).size();
                    oldSize = member.getMessages().get(name).get(currentSize-1).getSize();

                    System.out.printf("Current size is: %d and old size is: %d \n", currentSize, oldSize);
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }

                if(currentSize > oldSize)
                {
                    member.getMessages().get(name).get(currentSize-1).setSize(currentSize);
                    member.getMessages().get(name).get(currentSize-1).setId(database.getMessagesSize());

                    message = member.getMessages().get(name).get(currentSize-1).toString();
                    database.setSize(currentSize, database.getMessagesSize());
                    break;
                }
            }
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }
        return message;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/add/languages")
    public String addLanguages(@ModelAttribute ProgrammingLanguages languages)
    {
        System.out.println(languages);
        ArrayList<ProgrammingLanguages> langArray = new ArrayList<>();

        String[] lang = languages.getLanguage().split("_");
        String[] exp = languages.getYearsExperience().split("_");

        int length=lang.length;

        for(int i=0; i<length; i++)
        {
            ProgrammingLanguages pl = new ProgrammingLanguages(lang[i], exp[i]);
            langArray.add(pl);
        }
        member.setProgrammingLanguages(langArray);
        database.addProgrammingLanguages(member, languages);

        return "redirect:/home/"+member.getFirstName();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user/email")
    @ResponseBody
    public String getSenderEmail(Model model)
    {
        return member.getEmail();
    }
}