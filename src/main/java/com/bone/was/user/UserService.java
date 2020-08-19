package com.bone.was.user;

import com.bone.was.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByAuthKey(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

// 이거 사용해요? -> 안하면 제거


//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private JwtTokenProvider jwtTokenProvider;
//
//    public Optional<User> findByAuthKey(String authkey) {
//        return userRepository.findByAuthKey(authkey);
//    }
//
//

//
//    public String createToken(LoginRequest loginRequest){
//        User user = userRepository.findByAuthKey(loginRequest.getAuthkey())
//                .orElseThrow(IllegalArgumentException::new);
//        return jwtTokenProvider.createToken(user.getAuthkey());
//    }
}
