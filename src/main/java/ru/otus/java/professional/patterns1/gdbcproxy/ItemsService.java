package ru.otus.java.professional.patterns1.gdbcproxy;

import java.math.BigDecimal;
import java.util.ArrayList;
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
      item.setPrice(BigDecimal.valueOf(item.getPrice().doubleValue() * 2.0));
      itemsDao.updateItem(item);
    }
  }

  private static final class ItemsServiceProxy implements ProcessingItems {
    private final ItemsDao itemsDao;
    private List<Item> items;

    public ItemsServiceProxy(ItemsDao itemsDao) {
      this.itemsDao = itemsDao;
    }

    @Override
    public void getItemsAndIncreasePrice() {
      if (items == null) {
        items = itemsDao.getAllItems();
      }
      for (Item item : items) {
        item.setPrice(BigDecimal.valueOf(item.getPrice().doubleValue() * 2.0));
        itemsDao.updateItem(item);
      }
    }

    @Override
    public void save100NewItems() {
      if (items == null) {
        items = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
          Item item = new Item(0L, "title" + (int) (Math.random() * 2000), BigDecimal.valueOf(i));
          itemsDao.addItem(item);
          items.add(item);
        }
      }
    }
  }
}
