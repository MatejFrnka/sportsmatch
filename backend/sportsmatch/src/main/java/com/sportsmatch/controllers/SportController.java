package com.sportsmatch.controllers;

import com.sportsmatch.dtos.SportDTO;
import com.sportsmatch.services.SportService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/sports")
public class SportController {

  private final SportService sportService;

  /**
   * This endpoint returns paginated list of SportDto.
   *
   * @param pageable contains tha page and size value
   * @return paginated list of SportDTO
   */
  @GetMapping("/all")
  public List<SportDTO> getSports(@ParameterObject final Pageable pageable) {
    return sportService.getAllSports(pageable);
  }
}
