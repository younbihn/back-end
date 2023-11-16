package com.example.demo.siteuser.service;

import com.example.demo.entity.Auth;
import com.example.demo.entity.SiteUser;
import com.example.demo.siteuser.repository.SiteUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final SiteUserRepository siteUserRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.siteUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("다음 이메일을 찾을 수 없습니다 : " + email));
    }

    public SiteUser register(Auth.SignUp member) {
        boolean exists = this.siteUserRepository.existsByEmail(member.getEmail());
        if (exists) {
            throw new RuntimeException("이미 사용 중인 이메일입니다");
        }

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        var result = this.siteUserRepository.save(member.toUser());
        return result;
    }

    public SiteUser authenticate(Auth.SignIn member) {

        var user = this.siteUserRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다"));

        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }

        return user;
    }
}
