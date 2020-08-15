package com.bone.was.user;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/jwt/users")
public class UserJpaController {
    @Autowired
    private UserRepository userRepository;

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


    @PostMapping("/users")
    public void createUser(@Valid @RequestBody User user){
        Optional<User> users = userRepository.findByAuthKey(user.getAuthkey());

        if(!users.isPresent()){
           User member = userRepository.save(user);

        }
        createToken(user)
    }

    public String createToken(User user){
        User mem = userRepository.
    }
}
