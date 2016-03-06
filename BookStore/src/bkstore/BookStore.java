package bkstore;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class BookStore implements BookList{
	public static final int OK = 0;
	public static final int NOT_IN_STOCK = 1;
	public static final int DOES_NOT_EXIST = 2; 
	private BigDecimal price =  new BigDecimal(0);
	
	Book[] books;
	ArrayList<Book>  cart = new ArrayList<Book>();
	
	public BookStore()
	{
		ArrayList<Book>  bookList = new ArrayList<Book>();
		try {
			URL url = new URL("http://www.contribe.se/bookstoredata/bookstoredata.txt");
			Scanner s = new Scanner(url.openStream());
			while(s.hasNextLine())
			{
				String eachBook = s.nextLine();
				String[] bookDetails = eachBook.split(";");
				bookDetails[2]=bookDetails[2].replace(",", "");
				bookList.add(new Book(bookDetails[0], bookDetails[1], new BigDecimal(bookDetails[2]), Integer.parseInt(bookDetails[3])));
			}
			s.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		books = new Book[bookList.size()];
		books = bookList.toArray(new Book[bookList.size()] );
		
		//DISPLAY BOOKS
		for (int i=0;i<books.length;i++)
		{
			System.out.println("Title: "+ books[i].getTitle()+", Author: "+books[i].getAuthor()+", Price: "+books[i].getPrice());
		}
		
		//SEARCH
		Book[] results = list("generic");
		for (int i=0;i<results.length;i++)
		{
			System.out.println("Title: "+ results[i].getTitle()+", Author: "+results[i].getAuthor()+", Price: "+results[i].getPrice());
		}
		
		//ADD TO CART
		add(books[2],2);
		add(books[0],1);
		Book[] cartArray = cart.toArray(new Book[cart.size()]);
		for (int i=0;i<cartArray.length;i++)
		{
			System.out.println("Title: "+ cartArray[i].getTitle()+", Author: "+cartArray[i].getAuthor()+", Price: "+cartArray[i].getPrice());
		}
		
		//BUY
		int[] bookStatus = buy(cartArray);
		System.out.println(price);
		
	}
 
	public Book[] list(String searchString) {
		ArrayList<Book>  result = new ArrayList<Book>();
		for (int i=0;i<books.length;i++)
		{
			if(books[i].getAuthor().toLowerCase().contains(searchString.toLowerCase()) || books[i].getTitle().toLowerCase().contains(searchString.toLowerCase()) )
				result.add(books[i]);
		}
		return result.toArray(new Book[result.size()]);
	}

	
	public boolean add(Book book, int amount) {
		if (amount > 0) {
			for (int i = 1; i <= amount; i++) {
				cart.add(book);
			}
		}
		else if (amount<0) {
			for (int i = amount; i < 0; i++) {
				cart.remove(cart.indexOf(book));
			}
		}
		return true;
	}

	
	public int[] buy(Book... books) {
		int[] status = new int[books.length];
		for (int i=0;i<books.length;i++) {
			if(!Arrays.asList(this.books).contains(books[i]))
				status[i] = DOES_NOT_EXIST;
			else if(books[i].getQuantity()>0) {
				status[i] = OK;
				books[i].setQuantity(books[i].getQuantity()-1);
				price = price.add(books[i].getPrice());
			}
			else {
				status[i] = NOT_IN_STOCK;
			}
		}
		return status;
	}
	public static void main(String[] args) {
		BookStore bs = new BookStore();
	}
}
