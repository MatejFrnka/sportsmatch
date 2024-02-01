package com.sportsmatch.services;

import com.sportsmatch.dtos.SportDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SportService {
    List<SportDTO> getAllSports(final Pageable pageable);

}
