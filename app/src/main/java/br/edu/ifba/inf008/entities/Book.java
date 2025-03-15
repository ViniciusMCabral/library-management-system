package br.edu.ifba.inf008.entities;

import java.io.Serializable;
import java.time.LocalDate;

import br.edu.ifba.inf008.interfaces.IBook;

public class Book implements Serializable, IBook {
	private static final long serialVersionUID = 1L;
	private String isbn;
	private String title;
	private String author;
	private LocalDate yearOfPublication;
	private String genre;
	private boolean borrowed;

	public Book(String isbn, String title, String author, LocalDate yearOfPublication, String genre, boolean borrowed) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.yearOfPublication = yearOfPublication;
		this.genre = genre;
		this.borrowed = borrowed;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public LocalDate getYearOfPublication() {
		return yearOfPublication;
	}

	public String getGenre() {
		return genre;
	}

	public boolean isBorrowed() {
		return borrowed;
	}

	public void setBorrowed(boolean borrowed) {
		this.borrowed = borrowed;
	}
}
