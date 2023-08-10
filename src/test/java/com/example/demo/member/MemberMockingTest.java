package com.example.demo.member;

import com.example.demo.member.controller.form.request.MemberRegisterForm;
import com.example.demo.member.entity.Member;
import com.example.demo.member.entity.MemberRole;
import com.example.demo.member.entity.Role;
import com.example.demo.member.entity.RoleType;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.repository.MemberRoleRepository;
import com.example.demo.member.repository.RoleRepository;
import com.example.demo.member.service.MemberServiceImpl;
import com.example.demo.security.service.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MemberMockingTest {

    @Mock
    private MemberRepository mockMemberRepository;
    @Mock
    private RoleRepository mockRoleRepository;
    @Mock
    private MemberRoleRepository mockMemberRoleRepository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private MemberServiceImpl mockMemberService;

    @BeforeEach
    public void setup () throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Mocking: 개인회원 회원가입 테스트")
    public void 개인_회원이_회원가입을_진행합니다 () {
        // 프론트에서 받아올 회원 등록 폼
        final MemberRegisterForm registerForm = new MemberRegisterForm(
                "test@test.com", "1234", RoleType.NORMAL);

        // 폼 기반 계정 생성
        final Member member = registerForm.toMember();

        // 해당 이메일이 있으면 객체가 생성되지 않아야 함
        when(mockMemberRepository.findByEmail(registerForm.getEmail()))
                .thenReturn(Optional.empty());

        when(mockMemberRepository.save(member))
                .thenReturn(new Member("test@test.com", "1234"));

        // 회원 타입 부여
        final Role role = new Role(RoleType.NORMAL);
        final MemberRole memberRole = new MemberRole(role, member);

        when(mockRoleRepository.findByRoleType(registerForm.getRoleType()))
                .thenReturn(Optional.of(role));
        when(mockMemberRoleRepository.save(memberRole))
                .thenReturn(new MemberRole(role, member));

        // 실제 구동 테스트
        final MemberServiceImpl sut = new MemberServiceImpl(
                mockMemberRepository, encoder, mockRoleRepository,mockMemberRoleRepository, tokenProvider);
        final Boolean actual = sut.normalRegister(registerForm);

        // 예측 결과와 실제 데이터의 비교
        assertTrue(actual);
        System.out.println(actual);
        System.out.println(member.getEmail());
        System.out.println(member.getPassword());
        System.out.println(memberRole.getRole().getRoleType());
        }

    @Test
    @DisplayName("Mocking: 사업자회원 회원가입 테스트")
    public void 사업자_회원이_회원가입을_진행합니다 () {
        // 프론트에서 받아올 회원 등록 폼
        final MemberRegisterForm registerForm = new MemberRegisterForm(
                "test@test.com", "1234",1111111111L, RoleType.BUSINESS);

        final Long businessNumber = registerForm.getBusinessNumber();

        // 이메일 중복검사
        when(mockMemberRepository.findByEmail(registerForm.getEmail()))
                .thenReturn(Optional.empty());

        // 사업자번호 중복검사
        when(mockMemberRoleRepository.findByBusinessNumber(businessNumber))
                .thenReturn(Optional.empty());

        // 폼 기반 계정 생성
        final Member member = registerForm.toMember();

        when(mockMemberRepository.save(member))
                .thenReturn(new Member("test@test.com", "1235"));

        // 회원 타입 부여
        final Role role = new Role(RoleType.BUSINESS);
        final MemberRole memberRole = new MemberRole(role, member, businessNumber);

        when(mockRoleRepository.findByRoleType(registerForm.getRoleType()))
                .thenReturn(Optional.of(role));
        when(mockMemberRoleRepository.save(memberRole))
                .thenReturn(new MemberRole(role, member));

        // 실제 구동 테스트
        final MemberServiceImpl sut = new MemberServiceImpl(
                mockMemberRepository, encoder, mockRoleRepository,mockMemberRoleRepository, tokenProvider);
        final Boolean actual = sut.businessRegister(registerForm);

        // 예측 결과와 실제 데이터의 비교
        assertTrue(actual);
        System.out.println(actual);
        System.out.println(member.getEmail());
        System.out.println(member.getPassword());
        System.out.println(memberRole.getRole().getRoleType());
        System.out.println(memberRole.getBusinessNumber());
        }
}
