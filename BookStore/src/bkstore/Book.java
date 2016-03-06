package bkstore;

import java.math.BigDecimal;

public class Book {
	private String title;
	private String author;
	private BigDecimal price;
	private int quantity;
	public Book(String title, String author, BigDecimal price, int quantity)
	{
		this.title = title;
		this.author = author;
		this.price = price;
		this.quantity = quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
