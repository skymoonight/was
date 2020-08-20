package com.bone.was.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.validation.constraints.NotBlank;

@Service
public class UserTokenService {
    @Autowired
    private UserTokenRepository userTokenRepository;

    public UserToken save(@NotBlank UserToken userToken) {
                return userTokenRepository.save(userToken);
    }
}
