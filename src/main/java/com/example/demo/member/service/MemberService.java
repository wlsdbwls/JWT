package com.example.demo.member.service;

import com.example.demo.member.controller.form.request.MemberLoginForm;
import com.example.demo.member.controller.form.request.MemberRegisterForm;

public interface MemberService {
    Boolean normalRegister(MemberRegisterForm requestForm);
    Boolean businessRegister(MemberRegisterForm requestForm);
    String login(MemberLoginForm requestForm);
}
