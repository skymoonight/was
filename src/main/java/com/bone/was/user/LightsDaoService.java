package com.bone.was.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LightsDaoService extends JpaRepository<Lights,Long> {
}
