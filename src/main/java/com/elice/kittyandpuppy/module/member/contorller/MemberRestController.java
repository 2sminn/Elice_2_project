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
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberRestController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @GetMapping("/members/{id}")
    public ResponseEntity<Member> findById(@PathVariable Long id) {
        Member member = memberService.findMemberById(id).orElse(null);
        if (member == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PostMapping("/member")
    public ResponseEntity<Member> joinMember(@RequestBody @Valid MemberSaveDto memberSaveDto, BindingResult bindingResult) {
        Member member = memberMapper.MemberDTOToMember(memberSaveDto);
        Member savedMember = memberService.join(member);

        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> findAllMember() {
        List<Member> members = memberService.findAll();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @PutMapping("/member/{id}")
    public ResponseEntity<Member> editMember(@PathVariable Long id, @RequestBody @Valid MemberSaveDto memberSaveDto) {
        Member member = memberMapper.MemberDTOToMember(memberSaveDto);
        Member editedMember = memberService.editMember(id, member);
        return new ResponseEntity<>(editedMember, HttpStatus.OK);
    }

    @GetMapping("/member/validation/email")
    public ResponseEntity<Boolean> checkEmail(@RequestParam("email") String email){
        boolean result;

        if(email.trim().isEmpty()){
            result = false;
        }else{
            result =  memberService.checkEmail(email);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping("/member/validation/name")
    public ResponseEntity<Boolean> checkName(@RequestParam("name") String name){
        boolean result;

        if(name.trim().isEmpty()){
            result = false;
        }else{
            result =  memberService.checkName(name);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping("/member/login")
    public ResponseEntity<Member> login(@RequestBody Map<String,String> map){
        String email = map.get("email");
        String password = map.get("password");
        Member member = memberService.loginMember(email, password);
        if(member==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(member,HttpStatus.OK);
    }

}
