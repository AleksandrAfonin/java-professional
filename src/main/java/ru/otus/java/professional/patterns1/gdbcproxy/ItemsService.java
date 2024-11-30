package ru.otus.java.professional.patterns1.gdbcproxy;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ItemsService implements ProcessingItems {
  private final ItemsDao itemsDao;

  private ItemsService(ItemsDao itemsDao) {
    this.itemsDao = itemsDao;
  }

  public static ProcessingItems create(ItemsDao itemsDao) {
    return new ItemsServiceProxy(itemsDao);
  }

  public void save100NewItems() {
    for (int i = 1; i <= 100; i++) {
      itemsDao.addItem(new Item(0L, "title" + i, BigDecimal.valueOf(i)));
    }
  }

  public void getItemsAndIncreasePrice() {
    List<Item> itemList = itemsDao.getAllItems();
    for (Item item : itemList) {
      item.setPrice(item.getPrice().multiply(BigDecimal.valueOf(2.0)));
      itemsDao.updateItem(item);
    }
  }

  private static final class ItemsServiceProxy implements ProcessingItems {
    private final ItemsDao itemsDao;
    private final Connection connection;

    public ItemsServiceProxy(ItemsDao itemsDao) {
      this.itemsDao = itemsDao;
      this.connection = itemsDao.getConnection();
    }

    @Override
    public void getItemsAndIncreasePrice() {
      try {
        connection.setAutoCommit(false);
        List<Item> items = itemsDao.getAllItems();
        for (Item item : items) {
          item.setPrice(item.getPrice().multiply(BigDecimal.valueOf(2.0)));
          itemsDao.updateItem(item);
        }
        connection.commit();
      } catch (Exception e) {
        try {
          connection.rollback();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
        e.printStackTrace();
      } finally {
        try {
          connection.setAutoCommit(true);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }

    @Override
    public void save100NewItems() {
      try {
        connection.setAutoCommit(false);
        for (int i = 1; i <= 100; i++) {
          Item item = new Item(0L, "title" + (int) (Math.random() * 2000), BigDecimal.valueOf(i));
          itemsDao.addItem(item);
        }
        connection.commit();
      } catch (Exception e) {
        try {
          connection.rollback();
        } catch (SQLException ex) {
          ex.printStackTrace();
        }
        e.printStackTrace();
      } finally {
        try {
          connection.setAutoCommit(true);
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
