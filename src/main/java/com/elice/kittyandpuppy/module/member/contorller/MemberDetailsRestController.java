package com.elice.kittyandpuppy.module.member.contorller;

import com.elice.kittyandpuppy.global.jwt.TokenProvider;
import com.elice.kittyandpuppy.module.member.dto.MemberDetailsDto;
import com.elice.kittyandpuppy.module.member.entity.MemberDetails;
import com.elice.kittyandpuppy.module.member.service.MemberDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MemberDetailsRestController {
    private final MemberDetailService memberDetailService;
    private final TokenProvider tokenProvider;

    @PostMapping("/address/member")
    public ResponseEntity<MemberDetails> findByToken(@RequestBody Map<String,String> map){
        String token = map.get("token");
        if(tokenProvider.validToken(token)){
            Long id = tokenProvider.getMemberId(token);
            MemberDetails memberDetails = memberDetailService.findByMemberID(id);
            return new ResponseEntity<>(memberDetails,HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/details")
    public ResponseEntity<Boolean> updateDetails(@RequestBody MemberDetailsDto memberDetailsDto){
        String token = memberDetailsDto.getToken();
        if(tokenProvider.validToken(token)){
            Long id = tokenProvider.getMemberId(token);
            MemberDetails memberDetails = memberDetailsDto.toEntity();
            memberDetailService.update(id,memberDetails);

            return new ResponseEntity<>(true,HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }
}
