package com.otopark.business.repository;

import com.otopark.business.model.Member;

import java.util.Optional;

public interface MemberJPARepository extends AbstractJPARepository<Member, Long> {
    Optional<Member> findByFirstName(String firstName);
    Optional<Member> findByLicensePlate(String licensePlate);
}