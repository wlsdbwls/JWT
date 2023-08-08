package com.example.demo.member.controller;

import com.example.demo.member.controller.form.MemberRegisterForm;
import com.example.demo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    final private MemberService memberService;

    @PostMapping("/register")
    public Boolean memberRegister (@RequestBody MemberRegisterForm requestForm) {
        log.info("memberRegister()");

        return memberService.register(requestForm);
    }

}
