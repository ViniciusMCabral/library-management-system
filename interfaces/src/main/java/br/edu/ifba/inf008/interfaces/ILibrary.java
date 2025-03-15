package br.edu.ifba.inf008.interfaces;

import java.util.List;
import java.util.Set;

public interface ILibrary {
	
	void registerUser(IUser user);

	void registerBook(IBook book);

	void startLoan(ILoan loan);

	void finalizeLoan(ILoan loan);

	ILoan findLoan(IUser user, IBook book);

	List<IBook> findBookByTitle(String nome);

	IBook findBookByIsbn(String isbn);	

	IUser findUserById(int userId);

	Set<ILoan> getCurrentLoans();

	List<IUser> getUsers();

	Set<IBook> getBooks();

	Set<ILoan> getLoans();
}
