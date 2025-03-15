package br.edu.ifba.inf008.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import br.edu.ifba.inf008.interfaces.IBook;
import br.edu.ifba.inf008.interfaces.ILoan;
import br.edu.ifba.inf008.interfaces.IUser;

public class Loan implements Serializable, ILoan {

	private static final long serialVersionUID = 1L;
	private IBook book;
	private IUser user;
	private LocalDate loanDate;

	public Loan(IBook book, IUser user, LocalDate loanDate) {
		this.book = book;
		this.user = user;
		this.loanDate = loanDate;
	}

	public IBook getBook() {
		return book;
	}

	public IUser getUser() {
		return user;
	}

	public LocalDate getLoanDate() {
		return loanDate;
	}

	public double calculateFine(ILoan loan) {
    LocalDate loanDate = loan.getLoanDate();
    long daysLate = ChronoUnit.DAYS.between(loanDate, LocalDate.now()) - 14;
    return (daysLate > 0) ? daysLate * 0.5 : 0;
}

	public boolean isBookOverdue(LocalDate loanDate) {
    	return ChronoUnit.DAYS.between(loanDate, LocalDate.now()) > 14;
	}
}
