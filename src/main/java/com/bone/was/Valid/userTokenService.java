package com.bone.was.Valid;

import com.bone.was.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

@Service
public class userTokenService {
    @Autowired
    private userTokenRepository userTokenRepository;

    public userToken save(@NotBlank userToken userToken) {
        //public method로부터 반환된 private
        // public <-> private  접근 관련한 코드 확인 및 수정
        return userTokenRepository.save(userToken);
    }
}
