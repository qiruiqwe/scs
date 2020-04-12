package com.scs.web.peopleInfo;

import com.scs.po.Activity;
import com.scs.po.Club;

import com.scs.po.New;
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


import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/peopleInfo/clubManager")
public class MyclubController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private NewService newService;

    @Autowired
    private ClubService clubService;

    private Long getClubId(HttpSession httpSession){
        List<Club> myclub = (List<Club>) httpSession.getAttribute("myclub");
        return myclub.get(0).getId();
    }

    @GetMapping("/members")
    public  String members(@PageableDefault(size = 10,sort = {"grade"},direction = Sort.Direction.ASC) Pageable pageable,
                           HttpSession httpSession, Model model){
        Long myClubId = getClubId(httpSession);
        model.addAttribute("page",userService.getUsersByClubId(myClubId,pageable));
        return "peopleInfo/clubManager/members";
    }

    @GetMapping("/members/{userid}/delete")
    public String deleteMember(@PathVariable Long userid,RedirectAttributes attributes,HttpSession httpSession){
        Long clubId = getClubId(httpSession);
        clubService.removeUser(userid,clubId);
        attributes.addFlashAttribute("message","操作成功！");
        return "redirect:/peopleInfo/clubManager/members";
    }



    @GetMapping("/activitys")
    public String activitys(@PageableDefault(size = 1,sort = {"activitytime"},direction = Sort.Direction.DESC) Pageable pageable,
                            HttpSession httpSession, Model model){
        Long myClubId = getClubId(httpSession);
        model.addAttribute("page",activityService.listActivitiesByClubId(myClubId,pageable));
        return "peopleInfo/clubManager/activitys";
    }


    @GetMapping("/activity-input")
    public String activity(Model model){
        model.addAttribute("activity",new Activity());
        return "peopleInfo/clubManager/activity-input";
    }

    @PostMapping("/activity/input")
    public String activityInput(@ModelAttribute Activity activity , RedirectAttributes attributes, HttpSession httpSession){
        Long clubId = getClubId(httpSession);
        activity.setClub(clubService.getClubByClubId(clubId));
        Activity a;
        a= activityService.saveActivity(activity);
        if (a == null) {
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/peopleInfo/clubManager/activitys";
    }

    @GetMapping("/activity/{id}")
    public String activityEdit(@PathVariable Long id,Model model){
        model.addAttribute("activity",activityService.getActivity(id));
        return "peopleInfo/clubManager/activity-input";
    }

    @GetMapping("/activity/{id}/delete")
    public String activityDelete(@PathVariable Long id ,RedirectAttributes attributes){
        activityService.deleteActivity(id);
        attributes.addFlashAttribute("message","操作成功");
        return "redirect:/peopleInfo/clubManager/activitys";
    }


    @GetMapping("/news")
    public String news(@PageableDefault(size = 1,sort = {"publishtime"},direction = Sort.Direction.DESC) Pageable pageable,
                       HttpSession httpSession, Model model){
        Long myClubId = getClubId(httpSession);
        model.addAttribute("page",newService.listNewsByClubId(myClubId,pageable));
        return "peopleInfo/clubManager/news";
    }

    @GetMapping("/new-input")
    public String newInput(Model model){
        model.addAttribute("mynew",new New());
        return "peopleInfo/clubManager/new-input";
    }

    @PostMapping("/new/input")
    public String newInput(@ModelAttribute New mynew , RedirectAttributes attributes, HttpSession httpSession){
        Long clubId = getClubId(httpSession);
        mynew.setClub(clubService.getClubByClubId(clubId));
        New a;
        a= newService.saveNew(mynew);
        if (a == null) {
            attributes.addFlashAttribute("message","操作失败");
        }else {
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/peopleInfo/clubManager/news";
    }

    @GetMapping("/new/{id}")
    public String newEdit(@PathVariable Long id,Model model){
        model.addAttribute("mynew",newService.getActivity(id));
        return "peopleInfo/clubManager/new-input";
    }

    @GetMapping("/new/{id}/delete")
    public String newDelete(@PathVariable Long id ,RedirectAttributes attributes){
        newService.deleteActivity(id);
        attributes.addFlashAttribute("message","操作成功");
        return "redirect:/peopleInfo/clubManager/news";
    }



}
