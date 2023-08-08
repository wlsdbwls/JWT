package com.example.demo.member.service;

import com.example.demo.member.controller.form.MemberRegisterForm;

public interface MemberService {
    Boolean register(MemberRegisterForm requestForm);
}
