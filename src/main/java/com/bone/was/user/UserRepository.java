package com.bone.was.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.authkey = :authkey")
    Optional<User> findByAuthKey(@Param("authkey") String authkey);
}
