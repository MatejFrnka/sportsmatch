package com.sportsmatch.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@AllArgsConstructor
@Getter
@ConfigurationProperties(prefix = "app.sportsmingle.initialization")
public class InitProperties {

  private Boolean databaseInit;
}
