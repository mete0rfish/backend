package com.onetool.server;

import com.onetool.server.blueprint.Blueprint;
import com.onetool.server.blueprint.InspectionStatus;
import com.onetool.server.blueprint.repository.BlueprintRepository;
import com.onetool.server.member.domain.Member;
import com.onetool.server.member.enums.UserRole;
import com.onetool.server.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class TestDataLoader implements CommandLineRunner {
    @Autowired
    private BlueprintRepository blueprintRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        Member member = memberRepository.save(
                Member.builder()
                        .name("admin")
                        .password(passwordEncoder.encode("1234"))
                        .email("admin@example.com")
                        .role(UserRole.ROLE_ADMIN)
                        .build()
        );

        final Blueprint building1 = blueprintRepository.save(
                Blueprint.builder()
                        .blueprintName("대한민국 마을")
                        .blueprintDetails("대한민국의 어느 마을의 청사진입니다.")
                        .creatorName("윤성원 작가")
                        .standardPrice(50000L)
                        .salePrice(40000L)
                        .program("CAD")
                        .downloadLink("https://onetool.com/download")
                        .extension(".exe")
                        .blueprintImg("https://s3.bucket.image.com/")
                        .categoryId(1L)
                        .secondCategory("주거")
                        .inspectionStatus(InspectionStatus.NONE)
                        .build()
        );
        final Blueprint civil1 = blueprintRepository.save(
                Blueprint.builder()
                        .blueprintName("일본 마을")
                        .blueprintDetails("일본의 어느 마을의 청사진입니다.")
                        .creatorName("성원 작가")
                        .standardPrice(20000L)
                        .salePrice(8000L)
                        .program("CAD")
                        .downloadLink("https://onetool.com/download")
                        .extension(".exe")
                        .secondCategory("공공")
                        .blueprintImg("https://s3.bucket.image.com/")
                        .categoryId(2L)
                        .inspectionStatus(InspectionStatus.NONE)
                        .build()
        );
        final Blueprint interior1 = blueprintRepository.save(
                Blueprint.builder()
                        .blueprintName("일본 마을")
                        .blueprintDetails("일본의 어느 마을의 청사진입니다.")
                        .creatorName("성원 작가")
                        .standardPrice(20000L)
                        .salePrice(8000L)
                        .program("CAD")
                        .downloadLink("https://onetool.com/download")
                        .extension(".exe")
                        .secondCategory("도로")
                        .blueprintImg("https://s3.bucket.image.com/")
                        .categoryId(3L)
                        .inspectionStatus(InspectionStatus.PASSED)
                        .build()
        );
        final Blueprint machine1 = blueprintRepository.save(
                Blueprint.builder()
                        .blueprintName("일본 마을")
                        .blueprintDetails("일본의 어느 마을의 청사진입니다.")
                        .creatorName("성원 작가")
                        .standardPrice(20000L)
                        .salePrice(8000L)
                        .program("CAD")
                        .downloadLink("https://onetool.com/download")
                        .extension(".exe")
                        .secondCategory("기계부품")
                        .blueprintImg("https://s3.bucket.image.com/")
                        .categoryId(4L)
                        .inspectionStatus(InspectionStatus.NONE)
                        .build()
        );
        final Blueprint electric1 = blueprintRepository.save(
                Blueprint.builder()
                        .blueprintName("일본 마을")
                        .blueprintDetails("일본의 어느 마을의 청사진입니다.")
                        .creatorName("성원 작가")
                        .standardPrice(20000L)
                        .salePrice(8000L)
                        .program("CAD")
                        .downloadLink("https://onetool.com/download")
                        .extension(".exe")
                        .secondCategory("전기")
                        .blueprintImg("https://s3.bucket.image.com/")
                        .categoryId(5L)
                        .inspectionStatus(InspectionStatus.NONE)
                        .build()
        );
        final Blueprint etc1 = blueprintRepository.save(
                Blueprint.builder()
                        .blueprintName("일본 마을")
                        .blueprintDetails("일본의 어느 마을의 청사진입니다.")
                        .creatorName("성원 작가")
                        .standardPrice(20000L)
                        .salePrice(8000L)
                        .program("CAD")
                        .downloadLink("https://onetool.com/download")
                        .extension(".exe")
                        .blueprintImg("https://s3.bucket.image.com/")
                        .categoryId(6L)
                        .inspectionStatus(InspectionStatus.NONE)
                        .build()
        );
    }
}