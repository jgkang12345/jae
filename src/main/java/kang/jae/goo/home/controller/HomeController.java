package kang.jae.goo.home.controller;

import kang.jae.goo.config.dto.Member;
import kang.jae.goo.home.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/custom_join")
    @ResponseBody
    public Map<String, Object> join(@RequestBody Member member) {
        Map<String, Object> result = new HashMap<>();
        try {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            member.setUser_pw(passwordEncoder.encode(member.getUser_pw()));
            memberMapper.save(member);

            result.put("result" , true);
        }
        catch (Exception e) {
            e.printStackTrace();
            result.put("result", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
