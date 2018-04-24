package com.test.example;

import java.io.IOException;
import java.util.Properties;

public class Configuration {
  public static Properties properties = new Properties();

  static {
    try {
      properties.load(Configuration.class.getClassLoader().getResourceAsStream("test.properties"));
    } catch (IOException e) {
      System.err.println("File is not found");
    }
  }
}
