package com.workup.workup.models;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config")
public class ConfigProperties {

  private Map<String, String> jsKeys;

  public Map<String, String> getJsKeys() {
    return jsKeys;
  }

  public void setJsKeys(Map<String, String> jsKeys) {
    this.jsKeys = jsKeys;
  }
}
