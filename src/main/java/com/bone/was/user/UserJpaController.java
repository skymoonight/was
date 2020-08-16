package com.bone.was.user;

import com.bone.was.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
@RequiredArgsConstructor
@RestController
@RequestMapping("/jwt/users")
public class UserJpaController {
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/join")
    public Long join(@RequestBody Map<String,String> user) {
        return userRepository.save(User.builder()
                .authkey(user.get("authkey"))
                .roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
                .build()).getId();
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByAuthKey(user.get("authkey"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 authkey 입니다."));
                return jwtTokenProvider.createToken(member.getAuthkey(), member.getRoles());
    }


//    @GetMapping("/users")
//    public List<User> retrieveAllUsers() {
//        return userRepository.findAll();
//    }
//
//    @GetMapping("/users/{id}")
//    public Resource<User> retrieveUser(@PathVariable int id){
//        Optional<User> user = userRepository.findById(id);
//
//        if(!user.isPresent()){
//            throw new UserNotFoundException(String.format("ID{%s} not found",id));
//        }
//
//        Resource<User> resource = new Resource<>(user.get());
//        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
//        resource.add(linkTo.withRel("all-users"));
//
//        return resource;
//    }


//    @PostMapping("/join")
//    public void createUser(@RequestBody User user){
//        userService.save(user);
//    }
//
//    // 첫 oauth 인증 시 동작
//    @PostMapping("/login")
//    public ResponseEntity<TokenResponse> loginUser(@RequestBody LoginRequest loginRequest){
//        String token = userService.createToken(loginRequest);
//        return ResponseEntity.ok().body(new TokenResponse(token,"bearer"));

//        Optional<User> users = userService.findByAuthKey(user.getAuthkey());
//        System.out.println(users);
//
//        if(!users.isPresent()){
//           User mem = userService.save(user);
//            System.out.println("mem : ");
//           String token_a = jwtTokenProvider.createToken(mem.getAuthkey());
//
//            System.out.println(token_a);
//           //return ResponseEntity.ok().body(new TokenResponse(token_a,"bearer"));
//            return token_a;
//        }
//        String token = jwtTokenProvider.createToken("abc");
//        System.out.println("id 존재시 : "+token);
//        return token;
        //return ResponseEntity.ok().body(new TokenResponse(token,"bearer"));
    //}


}
