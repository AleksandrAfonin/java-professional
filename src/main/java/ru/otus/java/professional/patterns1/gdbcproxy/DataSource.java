package ru.otus.java.professional.patterns1.gdbcproxy;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public final class DataSource {
  private static final Map<String, Driver> DRIVERS_BY_KEY = new HashMap<>();

  private DataSource() {
  }

  public static void registerDriver(String key, Driver driver) {
    DRIVERS_BY_KEY.put(key.toLowerCase(), driver);
  }

  public static Connection getConnection(String url, String username, String password) {
    String key = getKey(url);
    Driver driver = DRIVERS_BY_KEY.get(key);

    if (driver == null) {
      throw new RuntimeException("Database driver '" + key + "' is not registered");
    }

    return driver.getConnection(url, username, password);
  }

  private static String getKey(String url) {
    int lo = url.indexOf(":");
    int hi = url.indexOf(":", lo + 1);
    return url.substring(lo + 1, hi).toLowerCase();
  }
}
