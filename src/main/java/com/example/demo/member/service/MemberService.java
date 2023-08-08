package com.example.demo.member.service;

import com.example.demo.member.controller.form.MemberLoginForm;
import com.example.demo.member.controller.form.MemberRegisterForm;

public interface MemberService {
    Boolean normalRegister(MemberRegisterForm requestForm);
    Boolean businessRegister(MemberRegisterForm requestForm);
    Boolean login(MemberLoginForm requestForm);
}
