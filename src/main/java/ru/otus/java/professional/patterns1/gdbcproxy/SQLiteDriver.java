package ru.otus.java.professional.patterns1.gdbcproxy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDriver implements Driver {
  @Override
  public Connection getConnection(String url, String username, String password) {
    try {
      return DriverManager.getConnection(url);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
