package com.bone.was.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface userTokenRepository extends JpaRepository<userToken,Long> {

    @Query("select u from userToken u where u.usertoken = :usertoken")
    Optional<userToken> findByUsertoken(String usertoken);



}
