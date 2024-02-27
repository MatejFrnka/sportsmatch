package com.sportsmatch.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@AllArgsConstructor
@Getter
@ConfigurationProperties(prefix = "initialization")
public class InitProperties {

  private Boolean databaseInit;
}
