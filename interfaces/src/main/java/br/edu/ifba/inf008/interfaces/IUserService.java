package br.edu.ifba.inf008.interfaces;

import java.time.LocalDate;

public interface IUserService {

	void createUser(int userId, String name);

	void borrowBook(int id, String isbn, LocalDate loanDate);
	
	public void returnBook(int id, String isbn);

	public IUser findById(int id);
}
