package com.onetool.server.qna.controller;

import com.onetool.server.global.exception.BaseResponse;
import com.onetool.server.qna.service.QnaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import static com.onetool.server.qna.dto.request.QnaRequest.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnaController {

    private final QnaService qnaService;

    @GetMapping
    public BaseResponse<?> qnaHome(){
        return BaseResponse.onSuccess(qnaService.getQnaBoard());
    }

    @PostMapping("/post")
    public BaseResponse<?> qnaWrite(Principal principal, @Valid @RequestBody PostQnaBoard request){
        qnaService.postQna(principal, request);
        return BaseResponse.onSuccess("문의사항 등록이 완료됐습니다.");
    }

    @GetMapping("/{boardId}")
    public BaseResponse<?> qnaDetails(@PathVariable Long boardId){
        return BaseResponse.onSuccess(qnaService.getQnaBoardDetails(boardId));
    }

}