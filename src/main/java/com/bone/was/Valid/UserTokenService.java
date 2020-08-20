package com.bone.was.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

@Service
public class UserTokenService {
    @Autowired
    private UserTokenRepository userTokenRepository;

    public UserToken save(@NotBlank UserToken userToken) {
        //public method로부터 반환된 private
        // public <-> private  접근 관련한 코드 확인 및 수정
        return userTokenRepository.save(userToken);
    }
}
