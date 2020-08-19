package com.bone.was.Valid;

import com.bone.was.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userTokenService {
    @Autowired
    private userTokenRepository userTokenRepository;

    public userToken save(userToken userToken) {
        return userTokenRepository.save(userToken);
    }
}
