package com.example.demo.member.service;

import com.example.demo.member.controller.form.MemberLoginForm;
import com.example.demo.member.controller.form.MemberRegisterForm;
import com.example.demo.member.entity.Member;
import com.example.demo.member.entity.MemberRole;
import com.example.demo.member.entity.Role;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.repository.MemberRoleRepository;
import com.example.demo.member.repository.RoleRepository;
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
    final private PasswordEncoder encoder;
    final private RoleRepository roleRepository;
    final private MemberRoleRepository memberRoleRepository;

    // 회원가입 - 비밀번호 암호화
    @Override
    public Boolean normalRegister(MemberRegisterForm requestForm) {
        final Optional<Member> maybeMember = memberRepository.findByEmail(requestForm.getEmail());

        if (maybeMember.isPresent()) {
            return false;
        }

        // 비밀번호 암호화
        String encryptedPassword = encoder.encode(requestForm.getPassword());

        // 계정 생성
        final Member member = requestForm.toMember();
        member.setPassword(encryptedPassword);

        // 회원 타입 부여
        final Role role = roleRepository.findByRoleType(requestForm.getRoleType()).get();
        final MemberRole accountRole = new MemberRole(role, member);
        memberRoleRepository.save(accountRole);

        memberRepository.save(member);

        return true;
    }

    @Override
    public Boolean businessRegister(MemberRegisterForm requestForm) {
        return null;
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
