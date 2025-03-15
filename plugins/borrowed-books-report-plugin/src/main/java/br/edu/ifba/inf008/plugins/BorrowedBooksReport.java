package br.edu.ifba.inf008.plugins;

import java.time.format.DateTimeFormatter;

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

public class BorrowedBooksReport implements IPlugin {

    private ILibrary library;

    public void generateReport() {
        library = ICore.getInstance().getLibraryDao().loadLibraryFromFile();

        Stage reportStage = new Stage();
        reportStage.setTitle("Borrowed Books Report");

        TextArea reportArea = new TextArea();
        reportArea.setEditable(false);
        reportArea.setWrapText(true);

        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        for (ILoan loan : library.getCurrentLoans()) {
            String formattedDate = loan.getLoanDate().format(formatter);
            sb.append("ISBN: ").append(loan.getBook().getIsbn())
                .append(" | Title: ").append(loan.getBook().getTitle())
                .append(" | User Id: ").append(loan.getUser().getId())
                .append(" | User Name: ").append(loan.getUser().getName())
                .append(" | Loan Date: ").append(formattedDate).append("\n");
        }

        reportArea.setText(sb.toString());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(reportArea);

        Scene scene = new Scene(layout, 460, 300);
        reportStage.setScene(scene);
        reportStage.show();
    }

    @Override
    public boolean init() {
        IUIController uiController = ICore.getInstance().getUIController();
        MenuItem menuItem = uiController.createMenuItem("Reports", "Borrowed Books");
        menuItem.setOnAction(e -> generateReport());
        return true;
    }
}
