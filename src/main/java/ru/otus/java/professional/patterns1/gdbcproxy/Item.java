package ru.otus.java.professional.patterns1.gdbcproxy;

import java.math.BigDecimal;

public class Item {
  private long id;
  private String title;
  private BigDecimal price;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Item(long id, String title, BigDecimal price) {
    this.id = id;
    this.title = title;
    this.price = price;
  }

  @Override
  public String toString() {
    return "Item{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", price=" + price +
            '}';
  }
}
