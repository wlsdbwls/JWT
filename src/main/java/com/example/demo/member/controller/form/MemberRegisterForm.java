package com.example.demo.member.controller.form;

import com.example.demo.member.entity.Member;
import com.example.demo.member.entity.RoleType;
import com.example.demo.member.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegisterForm {

    private String email;
    private String password;
    private Long businessNumber;
    private RoleType roleType;

    public Member toMember () {
        return new Member(email, password);
    }
}
