package ru.otus.java.professional.patterns1.gdbcproxy;

import java.sql.Connection;

public interface Driver {
  Connection getConnection(String url, String username, String password);
}
