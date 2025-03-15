package br.edu.ifba.inf008.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.inf008.interfaces.IBook;
import br.edu.ifba.inf008.interfaces.IUser;

public class User implements Serializable, IUser{

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private List<IBook> borrowedBooks = new ArrayList<>();

	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<IBook> getBorrowedBooks() {
		return borrowedBooks;
	}
}
