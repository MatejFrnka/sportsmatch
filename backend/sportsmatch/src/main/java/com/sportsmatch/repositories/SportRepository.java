package com.sportsmatch.repositories;

import com.sportsmatch.models.Sport;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {

  Optional<Sport> findSportByName(String name);
}
