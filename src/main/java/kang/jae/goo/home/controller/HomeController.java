package kang.jae.goo.home.controller;

import kang.jae.goo.config.dto.Member;
import kang.jae.goo.home.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @Autowired
    private  MemberMapper memberMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("home");
        return mv;
    }

    @GetMapping("/custom_login")
    public ModelAndView logins() {
        ModelAndView mv = new ModelAndView("custom_login");
        return mv;
    }

    @PostMapping("/join")
    public String join(Member member) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setUser_pw(passwordEncoder.encode(member.getUser_pw()));
        memberMapper.save(member);
        return "redirect:/";
    }

}
