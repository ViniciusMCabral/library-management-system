package br.edu.ifba.inf008.interfaces;

import java.time.LocalDate;
import java.util.List;

public interface IBookService {
    
	void createBook(String isbn, String title, String author, LocalDate yearOfPublication, String bookGenre);

    List<IBook> findByTitle(String titulo);

    IBook findByIsbn(String isbn);
}
