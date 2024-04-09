package com.elice.kittyandpuppy.module.member.contorller;

import com.elice.kittyandpuppy.module.member.entity.Member;
import com.elice.kittyandpuppy.module.member.dto.MemberSaveDto;
import com.elice.kittyandpuppy.module.member.mapper.MemberMapper;
import com.elice.kittyandpuppy.module.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @GetMapping("/{id}")
    public ResponseEntity<Member> findById(@PathVariable Long id) {
        Member member = memberService.findMemberById(id).orElse(null);
        if (member == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Member> joinMember(@RequestBody @Valid MemberSaveDto memberSaveDto, BindingResult bindingResult) {
        Member member = memberMapper.MemberDTOToMember(memberSaveDto);
        Member savedMember = memberService.join(member);

        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Member>> findAllMember() {
        List<Member> members = memberService.findAll();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @PostMapping("/{id}/edit")
    public ResponseEntity<Member> editMember(@PathVariable Long id, @RequestBody @Valid MemberSaveDto memberSaveDto) {
        Member member = memberMapper.MemberDTOToMember(memberSaveDto);
        Member editedMember = memberService.editMember(id, member);
        return new ResponseEntity<>(editedMember, HttpStatus.OK);
    }

    @PostMapping("/email")
    public ResponseEntity<Boolean> checkEmail(@RequestBody String email){
        boolean result;

        if(email.trim().isEmpty()){
            result = false;
        }else{
            result =  memberService.checkEmail(email);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/name")
    public ResponseEntity<Boolean> checkName(@RequestBody String name){
        boolean result;

        if(name.trim().isEmpty()){
            result = false;
        }else{
            result =  memberService.checkName(name);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
}
