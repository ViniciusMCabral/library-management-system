package br.edu.ifba.inf008.service;

import java.time.LocalDate;
import java.util.List;

import br.edu.ifba.inf008.entities.Book;
import br.edu.ifba.inf008.interfaces.IBook;
import br.edu.ifba.inf008.interfaces.IBookService;
import br.edu.ifba.inf008.interfaces.ILibrary;
import br.edu.ifba.inf008.interfaces.LibraryDao;
import br.edu.ifba.inf008.persistence.LibraryFileDao;

public class BookService implements IBookService{

	private final LibraryDao dao;
	private ILibrary library;

	public BookService(LibraryFileDao dao) {
		this.dao = dao;
		loadLibrary();
	}

	private void loadLibrary() {
		this.library = dao.loadLibraryFromFile();
	}

	public void createBook(String isbn, String title, String author, LocalDate yearOfPublication, String bookGenre) {
		IBook book = new Book(isbn, title, author, yearOfPublication, bookGenre, false);
		library.registerBook(book);
		dao.save(library);
	}

	public List<IBook> findByTitle(String title) {
		return library.findBookByTitle(title);
	}

	public IBook findByIsbn(String isbn) {
		return library.findBookByIsbn(isbn);
	}
}
