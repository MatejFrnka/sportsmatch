package com.sportsmatch.services;

import com.sportsmatch.dtos.SportDTO;
import com.sportsmatch.mappers.SportMapper;
import com.sportsmatch.repositories.SportRepository;
import com.sportsmatch.services.SportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SportServiceImp implements SportService {

    private final SportRepository sportRepository;
    private final SportMapper sportMapper;


    /**
     * This method returns a paginated list of SportsDTO.
     *
     * @param pageable contains the page and size values for pagination.
     * @return paginated list of SportDTO.
     */
    public List<SportDTO> getAllSports(final Pageable pageable) {
        return sportRepository.findAll(pageable).stream()
                .map(SportMapper::toDTO)
                .collect(Collectors.toList());
    }
}
