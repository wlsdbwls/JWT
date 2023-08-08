package com.example.demo.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_Id")
    private Role role;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_Id")
    private Member member;

    // 사업자 회원
    private Long businessNumber;

    public MemberRole(Role role, Member member) {
        this.role = role;
        this.member = member;
    }

    public MemberRole(Role role, Member member, Long businessNumber) {
        this.role = role;
        this.member = member;
        this.businessNumber = businessNumber;
    }
}
