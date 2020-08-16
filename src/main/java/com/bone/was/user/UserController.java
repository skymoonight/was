//package com.bone.was.user;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//
//import java.net.URI;
//import java.util.List;
//
//@RestController
//public class UserController {
//    private UserDaoService service;
//
//    // 의존성 주입
//    public UserController(UserDaoService service){
//        this.service = service;
//    }
//
//    @GetMapping("/users")
//    public List<User> retrieveAllUsers(){
//        return service.findAll();
//    }
//
//    @GetMapping("/users/{id}")
//    public User retrieveUser(@PathVariable int id){
//        User user = service.findOne(id);
//
//        if(user == null){
//            throw new UserNotFoundException(String.format("ID[%s] not found",id));
//        }
//
//        return user;
//    }
//
//    @PostMapping("/users")
//    public ResponseEntity<User> createUser(@RequestBody User user){
//        User savedUser = service.save(user);
//
//        //사용자에게 요청값을 변경해 주기 위한 메소드 - ServletUriComponentsBuilder
//        //현재 요청된 요청값을 사용하겠다 - fromCurrentRequest()
//        //반환시킬 값 설정 - path()
//        //path에서 설정한 가변변수에 지정값 추가 - buildAndExpand()
//        // uri로 변환 - toUri()
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
//                .buildAndExpand(savedUser.getId()).toUri();
//
//        return ResponseEntity.created(location).build();
//    }
//
//    @DeleteMapping("/users/{id}")
//    public void deleteUser(@PathVariable int id){
//        User user = service.deleteById(id);
//
//        if(user == null){
//            throw new UserNotFoundException(String.format("ID[%s] not found",id));
//        }
//    }
//
//}
