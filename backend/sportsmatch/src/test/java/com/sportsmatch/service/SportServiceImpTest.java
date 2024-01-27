package com.sportsmatch.service;

import com.sportsmatch.dtos.SportDTO;
import com.sportsmatch.mappers.SportMapper;
import com.sportsmatch.models.Sport;
import com.sportsmatch.repos.SportRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
class SportServiceImpTest {

    @Mock
    private SportRepository sportRepository;

    @InjectMocks
    private SportServiceImp sportService;

    @Test
    void getAllSportsShouldReturnAllSportsWhenRequired() {
        // Arrange
        Pageable pageable = Mockito.mock(Pageable.class);

        Sport sport1 = new Sport("Football");
        Sport sport2 = new Sport("Basketball");

        List<Sport> sports = Arrays.asList(sport1, sport2);
        Page<Sport> sportsPage = new PageImpl<>(sports, pageable, sports.size());

        SportDTO sportDTO1 = new SportDTO("Football");
        SportDTO sportDTO2 = new SportDTO("Basketball");

        List<SportDTO> expectedSportDTOs = Arrays.asList(sportDTO1, sportDTO2);

        // Mocking repository
        Mockito.when(sportRepository.findAll(Mockito.any(Pageable.class))).thenReturn(sportsPage);

        // Act
        List<SportDTO> result = sportService.getAllSports(pageable);

        // Assert
        assertEquals(expectedSportDTOs, result);
    }
}