package kang.jae.goo.config.service;

import kang.jae.goo.config.dto.Member;
import kang.jae.goo.home.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private  MemberMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = mapper.findByMemberId(username);
        if (member == null)
            throw new UsernameNotFoundException("Could not found user" + username);


        return User.builder()
                .username(member.getUser_id())
                .password(member.getUser_pw())
                .roles("USER")
                .build();
    }

}