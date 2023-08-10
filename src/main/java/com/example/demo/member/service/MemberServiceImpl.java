package com.example.demo.member.service;

import com.example.demo.member.controller.form.request.MemberLoginForm;
import com.example.demo.member.controller.form.request.MemberRegisterForm;
import com.example.demo.member.entity.Member;
import com.example.demo.member.entity.MemberRole;
import com.example.demo.member.entity.Role;
import com.example.demo.member.entity.RoleType;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.repository.MemberRoleRepository;
import com.example.demo.member.repository.RoleRepository;
import com.example.demo.security.service.TokenProvider;
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
    final private PasswordEncoder encoder;
    final private RoleRepository roleRepository;
    final private MemberRoleRepository memberRoleRepository;
    final private TokenProvider tokenProvider;

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

        memberRepository.save(member);

        // 회원 타입 부여
        final Role role = roleRepository.findByRoleType(requestForm.getRoleType()).get();
        final MemberRole memberRole = new MemberRole(role, member);
        memberRoleRepository.save(memberRole);

        return true;
    }

    @Override
    public Boolean businessRegister(MemberRegisterForm requestForm) {

        final Optional<Member> maybeMember = memberRepository.findByEmail(requestForm.getEmail());

        if (maybeMember.isPresent()) {
            return false;
        }

        // 비밀번호 암호화
        String encryptedPassword = encoder.encode(requestForm.getPassword());

        final Long businessNumber = requestForm.getBusinessNumber();

        // 중복 사업자 번호 확인
        final Optional<MemberRole> maybeMemberRole =
                memberRoleRepository.findByBusinessNumber(businessNumber);

        if (maybeMemberRole.isPresent()) {
            return false;
        }

        // 계정 생성
        final Member member = requestForm.toMember();
        member.setPassword(encryptedPassword);

        memberRepository.save(member);

        // 회원 타입 부여
        final Role role = roleRepository.findByRoleType(requestForm.getRoleType()).get();
        final MemberRole memberRole = new MemberRole(role, member, businessNumber);
        memberRoleRepository.save(memberRole);

        return true;
    }

    // 로그인 - 같은 비밀번호 matches 사용하여 확인하도록
    // 로그인하면 토큰 발급
    @Override
    public String login(MemberLoginForm requestForm) {
        Optional<Member> maybeMember = memberRepository.findByEmail(requestForm.getEmail());

        if(maybeMember.isPresent()) {
            Member member = maybeMember.get();

            if (encoder.matches(requestForm.getPassword(), member.getPassword())) {
                String token = tokenProvider.createToken(String.format("%s", member.getEmail()));	// 토큰 생성

                return token;
            }
        }

        return "";
    }
}
