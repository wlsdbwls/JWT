package com.example.demo.member.service;

import com.example.demo.member.controller.form.MemberRegisterForm;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    final private MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 - 비밀번호 암호화
    @Override
    public Boolean register(MemberRegisterForm requestForm) {
        final Optional<Member> maybeMember = memberRepository.findByEmail(requestForm.getEmail());

        if (maybeMember.isPresent()) {
            return false;
        }

        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(requestForm.getPassword());

        // 계정 생성
        final Member member = requestForm.toMember();
        member.setPassword(encryptedPassword);

        memberRepository.save(member);

        return true;
    }
}
