package br.edu.ifba.inf008.plugins;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.ILibrary;
import br.edu.ifba.inf008.interfaces.ILoan;
import br.edu.ifba.inf008.interfaces.IPlugin;
import br.edu.ifba.inf008.interfaces.IUIController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OverDueBookReport implements IPlugin {

	private ILibrary library;

	public void generateReport() {

		library = ICore.getInstance().getLibraryDao().loadLibraryFromFile();

		Stage reportStage = new Stage();
        reportStage.setTitle("Overdue Books Report");

        TextArea reportArea = new TextArea();
        reportArea.setEditable(false);
        reportArea.setWrapText(true);

        StringBuilder sb = new StringBuilder();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (ILoan loan : library.getCurrentLoans()) {
            LocalDate loanDate = loan.getLoanDate();
        
            if (loan.isBookOverdue(loanDate)) {
                double fine = loan.calculateFine(loan);
                long daysLate = ChronoUnit.DAYS.between(loanDate, LocalDate.now()) - 14;
            
                String formattedLoanDate = loanDate.format(formatter);

                sb.append("ISBN: ").append(loan.getBook().getIsbn())
                    .append(" | Title: ").append(loan.getBook().getTitle())
                    .append(" | User Id: ").append(loan.getUser().getId())
                    .append(" | User Name: ").append(loan.getUser().getName())
                    .append(" | Loan Date: ").append(formattedLoanDate)
                    .append(" | Days late: ").append(daysLate)
                    .append(" | Fine: ").append(fine).append("\n");
        }
        }

        reportArea.setText(sb.toString());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(reportArea);

        Scene scene = new Scene(layout, 600, 300);
        reportStage.setScene(scene);
        reportStage.show();
	}

	@Override
	public boolean init() {
		IUIController uiController = ICore.getInstance().getUIController();
		MenuItem menuItem = uiController.createMenuItem("Reports", "Overdue Books");
		menuItem.setOnAction(e -> generateReport());
		return true;
	}
}
