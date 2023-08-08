package com.example.demo.member.service;

import com.example.demo.member.controller.form.MemberLoginForm;
import com.example.demo.member.controller.form.MemberRegisterForm;
import com.example.demo.member.entity.Member;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    final private MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    // 회원가입 - 비밀번호 암호화
    @Override
    public Boolean register(MemberRegisterForm requestForm) {
        final Optional<Member> maybeMember = memberRepository.findByEmail(requestForm.getEmail());

        if (maybeMember.isPresent()) {
            return false;
        }

        // 비밀번호 암호화
        String encryptedPassword = encoder.encode(requestForm.getPassword());

        // 계정 생성
        final Member member = requestForm.toMember();
        member.setPassword(encryptedPassword);

        memberRepository.save(member);

        return true;
    }

    // 로그인 - 같은 비밀번호 matches 사용하여 확인하도록
    @Override
    public Boolean login(MemberLoginForm requestForm) {
        Optional<Member> maybeMember = memberRepository.findByEmail(requestForm.getEmail());

        if(maybeMember.isPresent()) {
            Member member = maybeMember.get();

            if (encoder.matches(requestForm.getPassword(), member.getPassword())) {
                return true;
            }
        }

        return false;
    }
}
