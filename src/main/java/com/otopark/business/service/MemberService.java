package com.otopark.business.service;

import com.otopark.business.model.Member;

import java.util.Optional;

public interface MemberService extends AbstractService<Member> {
    Optional<Member> findByLicensePlate(String licensePlate);
}

