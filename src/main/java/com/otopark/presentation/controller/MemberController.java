package com.otopark.presentation.controller;

import com.otopark.business.model.Member;
import com.otopark.business.service.MemberService;
import com.otopark.presentation.controller.dto.MemberDTO;
import com.otopark.presentation.controller.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/api/v1/members")
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping
    @PreAuthorize("hasAuthority('PER_MEMBERS')")
    public ResponseDTO<List<MemberDTO>> getMembers(){
        List<Member> members = memberService.findAll();
        List<MemberDTO> memberDTOS = new ArrayList<>();
        for (Member m: members ) {
            memberDTOS.add(new MemberDTO(m));
        }
        return new ResponseDTO<List<MemberDTO>>("Members data has retrieved", memberDTOS);
    }
}
