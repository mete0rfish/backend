package com.example.exception.service;

import com.example.exception.temp.TempRequest.*;

public interface TempService {
    String logic(TempLoginRequest data);
    void errorCheck(TempLoginRequest data);
    String searchEngine(String keyword);
    void errorCheck(String keyword);
}