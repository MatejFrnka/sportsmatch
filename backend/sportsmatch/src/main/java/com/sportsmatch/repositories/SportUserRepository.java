package com.sportsmatch.repositories;

import com.sportsmatch.models.SportUser;
import com.sportsmatch.models.SportUserKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportUserRepository extends JpaRepository<SportUser, SportUserKey> {
}
