package com.example.exception.controller;

import com.example.exception.common.BaseResponse;
import com.example.exception.service.TempService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.example.exception.temp.TempRequest.*;

@RestController
@RequiredArgsConstructor
public class TempController {

    private final TempService tempService;

    @GetMapping("/binding")
    public BaseResponse<?> bindingTest(@Validated @RequestBody TempLoginRequest request){
        return BaseResponse.onSuccess(tempService.logic(request));
    }

    @GetMapping("/search")
    public BaseResponse<?> validationTest(@RequestParam String keyword){
        return BaseResponse.onSuccess(tempService.searchEngine(keyword));
    }
}