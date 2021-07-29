package com.workup.workup.controllers;

import com.workup.workup.models.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class KeysController {
  @Autowired ConfigProperties configProperties;

  /*
  Can use application properties like this.
  #Keys
  config.jsKeys.filestack=filestackkey12345
  config.jsKeys.mapbox=mapboxkey12345
  Or an environment vairable like this
  CONFIG_JSKEYS_GOOGLE=1234abcgoogle
   */
  @RequestMapping(path = "/keys.js", produces = "application/javascript")
  @ResponseBody
  public String apikey() {
    StringBuilder keysJs = new StringBuilder();
    for (Map.Entry<String, String> entry : configProperties.getJsKeys().entrySet()) {
      keysJs.append(
          String.format(
              "const %s_API_KEY = '%s';%n", entry.getKey().toUpperCase(), entry.getValue()));
    }

    return keysJs.toString();
  }
}
