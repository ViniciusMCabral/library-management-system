package br.edu.ifba.inf008.interfaces;

import java.time.LocalDate;

public interface ILoan {
	
	IBook getBook();
	
	IUser getUser();

	LocalDate getLoanDate();

	double calculateFine(ILoan loan);

	boolean isBookOverdue(LocalDate loanDate);
}
