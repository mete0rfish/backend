package com.onetool.server.api.qna.converter;

import com.onetool.server.api.qna.QnaReply;
import com.onetool.server.api.qna.dto.request.ModifyQnaReplyRequest;
import com.onetool.server.api.qna.dto.request.PostQnaReplyRequest;
import com.onetool.server.global.annotation.Converter;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class QnaReplyConverter {

    public QnaReply fromRequestTooQnaReply(PostQnaReplyRequest request){
        return QnaReply.builder()
                .content(request.content())
                .build();
    }
}
