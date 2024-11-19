package ru.otus.java.professional.patterns1.gdbcproxy;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemsDao {
  private PreparedStatement getItemStatement;
  private PreparedStatement addItemStatement;
  private PreparedStatement updateItemStatement;
  private PreparedStatement deleteItemStatement;
  private PreparedStatement returnIdItemStatement;
  private PreparedStatement getAllItemsStatement;

  public ItemsDao(Connection connection) {
    try {
      getItemStatement = connection.prepareStatement("SELECT * FROM item WHERE id = ?");
      addItemStatement = connection.prepareStatement("INSERT INTO item (title, price) VALUES (?, ?)");
      updateItemStatement = connection.prepareStatement("UPDATE item SET title = ?, price = ? WHERE id = ?");
      deleteItemStatement = connection.prepareStatement("DELETE FROM item WHERE id = ?");
      returnIdItemStatement = connection.prepareStatement("SELECT id FROM item WHERE title = ? AND price = ?");
      getAllItemsStatement = connection.prepareStatement("SELECT * FROM item");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void addItem(Item item) {
    try {
      addItemStatement.setString(1, item.getTitle());
      addItemStatement.setBigDecimal(2, item.getPrice());
      addItemStatement.execute();
      returnIdItemStatement.setString(1, item.getTitle());
      returnIdItemStatement.setBigDecimal(2, item.getPrice());
      ResultSet resultSet = returnIdItemStatement.executeQuery();
      item.setId(resultSet.getLong("id"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Item getItem(long id) {
    try {
      getItemStatement.setLong(1, id);
      ResultSet resultSet = getItemStatement.executeQuery();
      Item item = new Item(
              resultSet.getLong("id"),
              resultSet.getString("title"),
              resultSet.getBigDecimal("price"));
      return item;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public boolean updateItem(long id, String title, BigDecimal price) {
    try {
      updateItemStatement.setString(1, title);
      updateItemStatement.setBigDecimal(2, price);
      updateItemStatement.setLong(3, id);
      updateItemStatement.execute();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean updateItem(Item item) {
    return updateItem(item.getId(), item.getTitle(), item.getPrice());
  }

  public boolean deleteItem(long id) {
    try {
      deleteItemStatement.setLong(1, id);
      deleteItemStatement.execute();
      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  public List<Item> getAllItems() {
    List<Item> items = new ArrayList<>();
    try {
      ResultSet resultSet = getAllItemsStatement.executeQuery();
      while (resultSet.next()) {
        items.add(new Item(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getBigDecimal("price")));
      }
      return items;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
