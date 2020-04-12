package com.scs.web;

import com.scs.po.Activity;
import com.scs.po.Club;
import com.scs.po.New;
import com.scs.po.User;
import com.scs.service.ActivityService;
import com.scs.service.ClubService;
import com.scs.service.NewService;
import com.scs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    private  ClubService clubService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private NewService newService;

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String index(Model model){
        List<Activity> activities =  activityService.listActivityTop(9);
        List<New> news = newService.listNewTop(12);
        List<Club> clubs = clubService.listClubTop(6);
        model.addAttribute("activities",activities);
        model.addAttribute("mynews",news);
        model.addAttribute("clubs",clubs);
        return "index";
    }

    @GetMapping("/allActivities")
    public String allActivities(@PageableDefault(size = 10,sort = "activitytime",direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",activityService.listActivityies(pageable));
        return "allActivities";
    }

    @GetMapping("/activity/{id}")
    public String activity(@PathVariable Long id,Model model){
        model.addAttribute("activity",activityService.contentMarkdownToHtml(id));
        return "activity";
    }


    @GetMapping("/activity/{id}/enlist")
    public String activityEnlist(@PathVariable Long id, HttpSession httpSession, RedirectAttributes attributes){
        Long userid = ((User)httpSession.getAttribute("user")).getId();
//        userService.addActivity(userid,id);
        activityService.addUser(userid,id);
        attributes.addFlashAttribute("message","添加活动成功！");
        return "redirect:/peopleInfo/joinActivities";
    }

    @GetMapping("/club/{id}/enlist")
    public String clubEnlist(@PathVariable Long id, HttpSession httpSession, RedirectAttributes attributes){
        Long userid = ((User)httpSession.getAttribute("user")).getId();
//        userService.addClub(userid,id);
        clubService.addUser(userid,id);
        attributes.addFlashAttribute("message","添加社团成功！");
        return "redirect:/peopleInfo/joinClubs";
    }


    @GetMapping("/allNews")
    public String allNews(@PageableDefault(size = 10,sort = "publishtime",direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",newService.listNews(pageable));
        return "allNews";
    }

    @GetMapping("/new/{id}")
    public String newinfo (@PathVariable Long id,Model model){
        model.addAttribute("new",newService.contentMarkdownToHtml(id));
        return "new";
    }

    @GetMapping("/allClubs")
    public String allClubs(@PageableDefault(size = 3,sort = "creattime",direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",clubService.listClubs(pageable));
        return "allClubs";
    }

    @GetMapping("/club/{id}")
    public String club(@PathVariable Long id,Model model){
        model.addAttribute("club",clubService.contentMarkdownToHtml(id));
        return "club";
    }

    @GetMapping("/club-create")
    public String creat(Model model, HttpSession httpSession){
        if(httpSession.getAttribute("user") != null){
            model.addAttribute("club",new Club());
            return "club-create";
        }
        return "login";
    }

    @PostMapping("/club/create")
    public String creatClub(@ModelAttribute Club club,HttpSession httpSession){
        User user = (User) httpSession.getAttribute("user");
        clubService.saveClub(user.getId(),club);
        return "peopleInfo/index";
    }

}
