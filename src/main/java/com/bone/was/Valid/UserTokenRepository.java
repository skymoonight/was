package com.bone.was.Valid;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken,Long> {

    @Query("select u from UserToken u where u.usertoken = :usertoken")
    Optional<UserToken> findByUsertoken(@NotBlank @Param("usertoken") String usertoken);

    @Query("delete from UserToken where usertoken = :token")
    void deleteuserTokenBy(@NotBlank @Param("token") String token);



}
