package com.onetool.server.member.controller;

import com.onetool.server.member.service.MemberService;
import com.onetool.server.member.dto.LoginRequest;
import com.onetool.server.member.dto.MemberCreateRequest;
import com.onetool.server.member.dto.MemberCreateResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@RequestMapping("/users")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginRequest request
    ) {
        String token = memberService.login(request);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);

        return new ResponseEntity<>("유저가 로그인되었습니다.",headers, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberCreateResponse> createMember(@RequestBody MemberCreateRequest request) {
        MemberCreateResponse response = memberService.createMember(request);
        return ResponseEntity.created(URI.create("/users/" + response.id())).body(response);
    }

    @PostMapping("/emails/verification-requests")
    public ResponseEntity sendMessage(@RequestParam("email") @Valid String email) {
        memberService.sendCodeToEmail(email);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/emails/verifications")
    public ResponseEntity verificationEmail(@RequestParam("email") @Valid String email,
                                            @RequestParam("code") String authCode) {
        boolean response = memberService.verifiedCode(email, authCode);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
