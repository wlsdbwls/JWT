package com.example.demo.member.controller.form.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginForm {

    private String email;
    private String password;
    private String token;
}
