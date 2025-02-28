package com.onetool.server.api.member.business;

import com.onetool.server.api.member.domain.Member;
import com.onetool.server.api.member.dto.*;
import com.onetool.server.api.member.service.MemberService;
import com.onetool.server.global.annotation.Business;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
@Slf4j
public class MemberBusiness {

    private final PasswordEncoder encoder;
    private final MemberService memberService;

    @Transactional(readOnly = true)
    public String findEmail(MemberFindEmailRequest request) {
        Member member = memberService.findByNameAndPhoneNumber(request.name(), request.phone_num());
        return member.getEmail();
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo(Long userId) {
        Member member = memberService.findById(userId);
        return MemberInfoResponse.from(member);
    }

    @Transactional(readOnly = true)
    public List<BlueprintDownloadResponse> getPurchasedBlueprints(final Long userId) {
        final Member member = memberService.findById(userId);
        return member.getOrders().stream()
                .flatMap(order -> order.getOrderItems().stream())
                .map(BlueprintDownloadResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public MemberCreateResponse createMember(MemberCreateRequest request) {
        memberService.validateDuplicateEmail(request.email());
        Member member = memberService.save(request.toEntity(encoder.encode(request.password())));
        log.info("createMember(): {}", member.getEmail());
        return MemberCreateResponse.of(member);
    }

    @Transactional
    public void updateMember(Long id, MemberUpdateRequest request) {
        Member member = memberService.findById(id);
        member.updateWith(request, encoder);
        memberService.save(member);
    }

    @Transactional
    public void deleteMember(Long id) {
        Member member = memberService.findById(id);
        memberService.delete(member);
    }
}
