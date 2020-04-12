package com.scs.web.peopleInfo;

import com.scs.po.Activity;
import com.scs.po.User;
import com.scs.service.*;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/peopleInfo")
public class PeopleInfoController {

    @Autowired
    private UserService userService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private AcademyService academyService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ClubService clubService;

    @GetMapping("/index")
    public String index(){
        return  "peopleInfo/index";
    }

    @GetMapping("/info")
    public  String getPeopleInfo(HttpSession httpSession, Model model){
        User user = (User) httpSession.getAttribute("user");
        User newUser = userService.getUserById(user.getId());
        model.addAttribute("user",newUser);
        model.addAttribute("grades",gradeService.listGrades());
        model.addAttribute("academies",academyService.listAcademy());
        return "peopleInfo/info";
    }

    @GetMapping("/joinActivities")
    public  String joinActivities(HttpSession httpSession, Model model,
                                  @PageableDefault(size = 1,sort = "activitytime",direction = Sort.Direction.DESC) Pageable pageable){
        User sessionUser = (User) httpSession.getAttribute("user");
        model.addAttribute("page",activityService.listActivitiesByUserId(pageable,sessionUser.getId()));
        return "peopleInfo/joinActivities";
    }

    @GetMapping("/joinActivities/{id}/delete")
    public String deleteActivity(@PathVariable Long id ,Model model,HttpSession httpSession ,RedirectAttributes attributes){
        Long userid = ((User)httpSession.getAttribute("user")).getId();
        activityService.deleteUser(userid,id);
        attributes.addFlashAttribute("message","删除活动成功！");
        return "redirect:/peopleInfo/joinActivities";
    }

    @GetMapping("/joinClubs")
    public  String joinClubs(HttpSession httpSession,Model model,
                             @PageableDefault(size = 1,sort = "id",direction = Sort.Direction.DESC) Pageable pageable){
        User sessionUser = (User) httpSession.getAttribute("user");
        model.addAttribute("page",clubService.listClubsByUserId(pageable,sessionUser.getId()));
        return "peopleInfo/joinClubs";
    }

    @GetMapping("/joinClubs/{id}/delete")
    public String deleteClub(@PathVariable Long id ,Model model,HttpSession httpSession,RedirectAttributes attributes){
        Long userid = ((User)httpSession.getAttribute("user")).getId();
        clubService.removeUser(userid,id);
        attributes.addFlashAttribute("message","删除社团成功！");
        return "redirect:/peopleInfo/joinClubs";
    }

    @PostMapping("/info/input")
    public  String postPeopleInfo(@ModelAttribute User user , HttpSession httpSession){
        user.setGrade(gradeService.getGradeById(user.getGrade().getId()));
        user.setAcademy(academyService.getAcademyById(user.getAcademy().getId()));
        userService.saveUser(user);
        httpSession.removeAttribute("user");
        httpSession.setAttribute("user",user);
        return "redirect:/peopleInfo/info";
    }


}
