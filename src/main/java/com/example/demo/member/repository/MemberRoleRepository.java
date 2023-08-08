package com.example.demo.member.repository;

import com.example.demo.member.entity.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
    Optional<MemberRole> findByBusinessNumber(Long businessNumber);
}
