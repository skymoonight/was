package com.bone.was.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {


    @Query(nativeQuery = true, value="delete from users where join_date < (current_date-interval 1 month)")
    User deleteUserbyjoindate ();
}
