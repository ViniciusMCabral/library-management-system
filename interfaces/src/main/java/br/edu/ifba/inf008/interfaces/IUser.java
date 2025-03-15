package br.edu.ifba.inf008.interfaces;

import java.util.List;

public interface IUser {
	
	int getId();

	String getName();

	List<IBook> getBorrowedBooks();
}
