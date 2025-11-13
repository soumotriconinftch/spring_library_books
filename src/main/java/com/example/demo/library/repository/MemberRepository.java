package com.example.demo.library.repository;

import com.example.demo.library.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNameIgnoreCase(String name);
}
