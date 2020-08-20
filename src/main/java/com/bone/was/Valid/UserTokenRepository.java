package com.bone.was.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken,Long> {

    // Query sql injection 방지. ' " 같은 것 필터링.
    @Query("select u from UserToken u where u.usertoken = :usertoken")
    Optional<UserToken> findByUsertoken(@NotBlank String usertoken);

    @Query("delete from UserToken where usertoken = :token")
    void deleteuserTokenBy(@NotBlank String token);



}
