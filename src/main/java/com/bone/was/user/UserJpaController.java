package com.bone.was.user;

import com.bone.was.Valid.userToken;
import com.bone.was.Valid.userTokenRepository;
import com.bone.was.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.crypto.*;
import javax.validation.constraints.NotBlank;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;

import static org.apache.commons.codec.digest.DigestUtils.sha256;


@RequiredArgsConstructor
@RestController
@RequestMapping("/jwt/users")
public class UserJpaController {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private final userTokenRepository userTokenRepository;

    // 회원가입 - id 해시처리하여 db 저장
    @PostMapping("/join")
    public Long join(@NotBlank @RequestBody Map<String,String> user) {
        return userRepository.save(User.builder()
                .authkey(user.get("authkey"))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getId();
    }

    // 로그인
    @PostMapping("/login")
    public JSONObject login(@NotBlank @RequestBody Map<String, String> user) {
        JSONObject result = new JSONObject();
        User member = userRepository.findByAuthKey(user.get("authkey"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 authkey 입니다."));
        String JWT = jwtTokenProvider.createToken(member.getAuthkey(), member.getRoles());
        result.put("JWT",JWT);
        saveToken(JWT);
        System.out.println("JWT:"+JWT);
        return result;
    }

    @PostMapping("/delete")
    public String delete(@NotBlank @RequestBody String deleteRequest){
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject logoutreq = (JSONObject) jsonParser.parse(deleteRequest);
            String authkey = (String) logoutreq.get("authkey2");
            User usered = userRepository.findByAuthKey(authkey).orElseThrow(() -> new IllegalArgumentException("없는 회원"));
            userRepository.delete(usered);
            return "delete success";
        }catch(ParseException e){
            return "Parse failed";
        }
    }

    public Long saveToken(String jwtToken){
        return userTokenRepository.save(userToken.builder()
                .usertoken(jwtToken)
                .build()).getId();
    }

}
