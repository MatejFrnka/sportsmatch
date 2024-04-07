package com.sportsmatch.services;

import com.sportsmatch.BaseTest;
import com.sportsmatch.dtos.SportDTO;
import com.sportsmatch.mappers.SportMapper;
import com.sportsmatch.models.Sport;
import com.sportsmatch.repositories.SportRepository;
import com.sportsmatch.services.SportServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SportServiceImpTest extends BaseTest {

  @Mock private SportRepository sportRepository;

  @Mock private SportMapper sportMapper;

  @InjectMocks private SportServiceImp sportService;

  @Test
  void getAllSportsShouldReturnAllSportsWhenRequired() {
    // Arrange
    Pageable pageable = Mockito.mock(Pageable.class);

    String footballEmoji = "\uD83E\uDD45"; // Goal net emoji
    String basketballEmoji = "\uD83C\uDFC0"; // basketball emoji orange ball

    Sport sport1 = new Sport("Football", footballEmoji, "urlFootball");
    Sport sport2 = new Sport("Basketball", basketballEmoji, "urlBasketball");

    List<Sport> sports = Arrays.asList(sport1, sport2);
    Page<Sport> sportsPage = new PageImpl<>(sports, pageable, sports.size());

    SportDTO sportDTO1 = new SportDTO("Football", footballEmoji, "urlFootball");
    SportDTO sportDTO2 = new SportDTO("Basketball", basketballEmoji, "urlBasketball");

    when(sportMapper.toDTO(sport1)).thenReturn(sportDTO1);
    when(sportMapper.toDTO(sport2)).thenReturn(sportDTO2);

    List<SportDTO> expectedSportDTOs = Arrays.asList(sportDTO1, sportDTO2);

    // Mocking repository
    when(sportRepository.findAll(any(Pageable.class))).thenReturn(sportsPage);

    // Act
    List<SportDTO> result = sportService.getAllSports(pageable);

    // Assert
    assertEquals(expectedSportDTOs, result);
  }
}
