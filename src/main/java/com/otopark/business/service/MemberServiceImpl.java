package com.otopark.business.service;

import com.otopark.business.model.Member;
import com.otopark.business.repository.MemberJPARepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl extends AbstractServiceImpl<Member> implements MemberService{
    MemberJPARepository memberJPARepository;
    public MemberServiceImpl(MemberJPARepository memberJPARepository) {
        super(memberJPARepository);
        this.memberJPARepository = memberJPARepository;
    }

    @Override
    public Optional<Member> findByLicensePlate(String licensePlate) {
        return memberJPARepository.findByLicensePlate(licensePlate);
    }
}
