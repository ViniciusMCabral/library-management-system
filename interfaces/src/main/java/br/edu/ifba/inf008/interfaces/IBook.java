package br.edu.ifba.inf008.interfaces;

import java.time.LocalDate;

public interface IBook {
	
	String getIsbn();

	String getTitle();

	LocalDate getYearOfPublication();

	public String getAuthor();

	public String getGenre();

	boolean isBorrowed();

	void setBorrowed(boolean borrowed);
}
