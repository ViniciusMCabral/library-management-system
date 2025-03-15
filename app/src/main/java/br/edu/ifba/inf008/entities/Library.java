package br.edu.ifba.inf008.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.edu.ifba.inf008.interfaces.IBook;
import br.edu.ifba.inf008.interfaces.ILibrary;
import br.edu.ifba.inf008.interfaces.ILoan;
import br.edu.ifba.inf008.interfaces.IUser;

public class Library implements Serializable, ILibrary {
	private static final long serialVersionUID = 1L;
	private Set<IBook> books = new HashSet<>();
	private List<IUser> users = new ArrayList<>();
	private Set<ILoan> loans = new HashSet<>();

	public void registerUser(IUser user) {
		users.add(user);
	}

	public void registerBook(IBook book) {
		books.add(book);
	}

	public void startLoan(ILoan loan) {
		IBook book = loan.getBook();
		if (books.contains(book)) {
			books.remove(book);
			book.setBorrowed(true);
			books.add(book);
		}
		
		if(!loans.contains(loan)) {		
			this.loans.add(loan);
		}
	}

	public void finalizeLoan(ILoan loan) {
		IBook book = loan.getBook();
		if (books.contains(book)) {
			books.remove(book);
			book.setBorrowed(false);
			books.add(book);
		}
		loans.removeIf(l ->
		l.getUser().getId() == loan.getUser().getId() &&
		l.getBook().getTitle().equals(loan.getBook().getTitle())
	);
	}

	public ILoan findLoan(IUser user, IBook book) {
		if (loans == null)
			return null;

		for (ILoan loan : loans) {
			if (loan.getUser().getId() == user.getId() && loan.getBook().getIsbn().equals(book.getIsbn())) {
				return loan;
			}
		}
		return null;
	}

	public List<IBook> findBookByTitle(String nome) {
		List<IBook> foundBooks = new ArrayList<>();
		for (IBook book : books) {
			if (book.getTitle().toLowerCase().contains(nome.toLowerCase()) && !book.isBorrowed()) {
				foundBooks.add(book);
			}
		}
		return foundBooks;
	}

	public IBook findBookByIsbn(String isbn) {
		for (IBook book : books) {
			if (isbn.equalsIgnoreCase(book.getIsbn())) {
				return book;
			}
		}
		return null;
	}

	public IUser findUserById(int id) {
		for (IUser user : users) {
			if (id == user.getId()) {
				return user;
			}
		}
		return null;

	}

	public Set<ILoan> getCurrentLoans() {
		Set<ILoan> currentLoans = new HashSet<>();
		for (ILoan loan : loans) {
			if (loan.getBook().isBorrowed()) {
				currentLoans.add(loan);
			}
		}
		return currentLoans;
	}

	public Set<IBook> getBooks() {
		return books;
	}

	public List<IUser> getUsers() {
		return users;
	}

	public Set<ILoan> getLoans() {
		return loans;
	}
}
