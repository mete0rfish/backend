package com.onetool.server.api.qna.controller;

import com.onetool.server.api.qna.business.QnaBoardBusiness;
import com.onetool.server.api.qna.dto.request.PostQnaBoardRequest;
import com.onetool.server.api.qna.dto.request.QnaBoardRequest;
import com.onetool.server.api.qna.dto.response.QnaBoardBriefResponse;
import com.onetool.server.api.qna.dto.response.QnaBoardDetailResponse;
import com.onetool.server.api.qna.dto.response.QnaBoardResponse;
import com.onetool.server.api.qna.service.QnaBoardService;
import com.onetool.server.global.exception.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class QnaBoardController {

    private final QnaBoardBusiness qnaBoardBusiness;

    @GetMapping("/qna/list")
    public ApiResponse<List<QnaBoardBriefResponse>> qnaHome() {
        return ApiResponse.onSuccess(qnaBoardBusiness.getQnaBoardBriefList());
    }

    @PostMapping("/qna/post")
    public ApiResponse<String> qnaWrite(
            Principal principal,
            @Valid @RequestBody PostQnaBoardRequest request) {
        log.info("쓰기");
        qnaBoardBusiness.postQnaBoard(principal, request);
        return ApiResponse.onSuccess("문의사항 등록이 완료됐습니다.");
    }

    @GetMapping("/{qnaId}")
    public ApiResponse<QnaBoardDetailResponse> qnaDetails(
            Principal principal,
            @PathVariable Long qnaId) {
        return ApiResponse.onSuccess(qnaBoardBusiness.getOneQnaBoardDetail(principal, qnaId));
    }

    @PostMapping("/{qnaId}/delete")
    public ApiResponse<String> qnaDelete(
            Principal principal,
            @PathVariable Long qnaId) {
        qnaBoardBusiness.deleteQnaBoard(principal, qnaId);
        return ApiResponse.onSuccess("게시글이 삭제되었습니다.");
    }

    //TODO : 게시글 수정 방법 : 게시글 상세 페이지 -> 게시글 수정 클릭 -> 수정 페이지 -> 수정 완료

    @GetMapping("/{qnaId}/update")
    public ApiResponse<String> qnaUpdate(
            Principal principal,
            @PathVariable Long qnaId,
            @Valid @RequestBody PostQnaBoardRequest request) {

        qnaBoardBusiness.updateQnaBoard(principal, qnaId, request);
        return ApiResponse.onSuccess("게시글이 수정되었습니다.");
    }
}