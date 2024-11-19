package ru.otus.java.professional.patterns1.gdbcproxy;

import java.sql.Connection;

public class Application {
  public static void main(String[] args) {
    DataSource.registerDriver("sqlite", new SQLiteDriver());
    Connection connection = DataSource.getConnection(
            "jdbc:sqlite:database/database.sqlite",
            "",
            "");

    ItemsDao itemsDao = new ItemsDao(connection);
    ProcessingItems processingItems = ItemsService.create(itemsDao);

    processingItems.save100NewItems();
    processingItems.getItemsAndIncreasePrice();
  }
}
