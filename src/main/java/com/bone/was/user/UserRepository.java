package com.bone.was.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.authkey = :authkey")
    Optional<User> findByAuthKey(@NotBlank String authkey); // 수정

}
