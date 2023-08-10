package com.example.demo.member.controller.form.request;

import com.example.demo.member.entity.Member;
import com.example.demo.member.entity.RoleType;
import com.example.demo.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberRegisterForm {

    private String email;
    private String password;
    private Long businessNumber;
    private RoleType roleType;

    public MemberRegisterForm(String email, String password, RoleType roleType) {
        this.email = email;
        this.password = password;
        this.roleType = roleType;
    }

    public MemberRegisterForm(String email, String password, Long businessNumber, RoleType roleType) {
        this.email = email;
        this.password = password;
        this.businessNumber = businessNumber;
        this.roleType = roleType;
    }

    public Member toMember () {
        return new Member(email, password);
    }
}
