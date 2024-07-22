package com.onetool.server.qna.service;

import com.onetool.server.qna.QnaBoard;
import com.onetool.server.qna.dto.request.QnaRequest;

import java.security.Principal;
import java.util.List;

import static com.onetool.server.qna.dto.response.QnaResponse.*;

public interface QnaService {

    List<QnaBoardBriefResponse> getQnaBoard();
    void postQna(Principal principal, QnaRequest.PostQnaBoard request);
    QnaBoardDetailResponse getQnaBoardDetails(Long boardId);


    //에러 확인 메서드
    void hasErrorWithNoContent(List<QnaBoard> data);
}
