package br.edu.ifba.inf008.service;

import java.time.LocalDate;

import br.edu.ifba.inf008.entities.Loan;
import br.edu.ifba.inf008.entities.User;
import br.edu.ifba.inf008.interfaces.IBook;
import br.edu.ifba.inf008.interfaces.IBookService;
import br.edu.ifba.inf008.interfaces.ILibrary;
import br.edu.ifba.inf008.interfaces.ILoan;
import br.edu.ifba.inf008.interfaces.IUser;
import br.edu.ifba.inf008.interfaces.IUserService;
import br.edu.ifba.inf008.interfaces.LibraryDao;

public class UserService implements IUserService {

	private final LibraryDao dao;
	private final IBookService bookService;
	private ILibrary library;

	public UserService(LibraryDao libraryDao, IBookService bookService) {
		this.dao = libraryDao;
		this.bookService = bookService;
		this.loadLibrary();
	}

	private void loadLibrary() {
		this.library = dao.loadLibraryFromFile();
	}

	public void createUser(int userId, String name) {
		User user = new User(userId, name);
		loadLibrary();
		library.registerUser(user);
		dao.save(library);
	}

	public void borrowBook(int id, String isbn, LocalDate loanDate) {
		IBook book = bookService.findByIsbn(isbn);
		IUser user = findById(id);
		book.setBorrowed(true);
		ILoan loan = new Loan(book, user, loanDate);
		user.getBorrowedBooks().add(book);
		library.startLoan(loan);
		dao.save(library);
	}

	public void returnBook(int id, String isbn) {
		IBook book = bookService.findByIsbn(isbn);
		IUser user = findById(id);
		book.setBorrowed(false);
		ILoan loan = library.findLoan(user, book);
		user.getBorrowedBooks().remove(book);
		library.finalizeLoan(loan);
		dao.save(library);
	}

	public IUser findById(int id) {
		return library.findUserById(id);
	}
}
