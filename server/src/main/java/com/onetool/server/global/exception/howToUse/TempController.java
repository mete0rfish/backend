package com.onetool.server.global.exception.howToUse;

import com.onetool.server.global.exception.BaseResponse;
import com.onetool.server.global.exception.howToUse.service.TempService;
import com.onetool.server.global.exception.howToUse.temp.TempRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TempController {

    private final TempService tempService;

    @GetMapping("/binding")
    public BaseResponse<?> bindingTest(@Validated @RequestBody TempRequest.TempLoginRequest request){
        return BaseResponse.onSuccess(tempService.logic(request));
    }

    @GetMapping("/search")
    public BaseResponse<?> validationTest(@RequestParam String keyword){
        return BaseResponse.onSuccess(tempService.searchEngine(keyword));
    }
}