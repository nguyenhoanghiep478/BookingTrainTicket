package com.booksms.authentication.interfaceLayer.service;

public interface IJwtBlackListService {

    Boolean isBlackList(String jwtToken);
    void addToBlackList(String jwtToken);
}
