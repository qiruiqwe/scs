package com.scs.web;

import com.scs.po.Club;
import com.scs.po.User;
import com.scs.service.AcademyService;
import com.scs.service.ClubService;
import com.scs.service.GradeService;
import com.scs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class LoginController {
    @Autowired
    private  UserService userService;

    @Autowired
    private AcademyService academyService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private ClubService clubService;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes)  {
       User  user = userService.checkUser(username,password);
        if (user != null)
        {
            user.setPassword(null);
            session.setAttribute("user",user);
            List<Club> club = clubService.getClubByCreaterId(user.getId());
            session.setAttribute("myclub",club);
            return "peopleInfo/index";
        }else{
            attributes.addFlashAttribute("message","账号或者密码有错误！");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return  "redirect:/login";
    }

    @GetMapping("/register")
    public String  register(Model model, HttpSession httpSession)  {
        if (httpSession.getAttribute("user")!= null)
        {
            return "index";
        }else {
            model.addAttribute("academies",academyService.listAcademy());
            model.addAttribute("user",new User());
            model.addAttribute("grades",gradeService.listGrades());
            return "register";
        }
    }

    @Transactional
    @PostMapping("/register/input")
    public String editregister(@ModelAttribute User user, RedirectAttributes attributes,
                               HttpSession session){
        if (userService.checkUser(user.getUsername(),user.getPassword()) != null)
        {
            attributes.addFlashAttribute("message","账号和密码已经被注册");
            attributes.addFlashAttribute("user",user);
            return "redirect:/register";
        }else{
            user.setGrade(gradeService.getGradeById(user.getGrade().getId()));
            user.setAcademy(academyService.getAcademyById(user.getAcademy().getId()));
            userService.saveUser(user);
            session.removeAttribute("user");
            session.setAttribute("user",user);
            return "peopleInfo/index";
        }
    }
}
