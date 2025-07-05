package com.ugetsidfashion.vot;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {
    Optional<UserProfile> findByEmail(String email);
}
