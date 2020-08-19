package com.bone.was.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.authkey = :authkey")
    Optional<User> findByAuthKey(String authkey);

}
