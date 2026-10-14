package com.example.petclinic.service;

import com.example.petclinic.entity.Member;
import com.example.petclinic.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findMember(String memberId) {
        return verifyMemberExists(memberId);
    }

    private Member verifyMemberExists(String memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new NoSuchElementException("Member does not exist"));
    }
}
