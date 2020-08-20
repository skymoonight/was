package com.bone.was.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface userTokenRepository extends JpaRepository<userToken,Long> {

    // Query sql injection 방지. ' " 같은 것 필터링.
    @Query("select u from userToken u where u.usertoken = :usertoken")
    Optional<userToken> findByUsertoken(@NotBlank String usertoken);



}
