package br.edu.ifba.inf008.shell;

import java.time.LocalDate;
import java.util.List;

import br.edu.ifba.inf008.interfaces.IBook;
import br.edu.ifba.inf008.interfaces.IBookService;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.interfaces.IUser;
import br.edu.ifba.inf008.interfaces.IUserService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UIController extends Application implements IUIController
{
	private ICore core;
	private MenuBar menuBar;
	private TabPane tabPane;
	private static UIController uiController = new UIController();

	private IBookService bookService = Core.getInstance().getIBookService(); 
	private IUserService userService = Core.getInstance().getUserService();


	public UIController() {
	}

	@Override
	public void init() {
		uiController = this;
	}

	public static UIController getInstance() {
		return uiController;
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Library System");

		Button buttonBook = new Button("Register Book");
		Button buttonUser = new Button("Register User");
		Button buttonSearchBook = new Button("Search Book By Title");
		Button buttonBorrowBook = new Button("Borrow Book");
		Button buttonReturnBook = new Button("Return Book");

		buttonBook.setOnAction(e -> bookRegistration());
		buttonUser.setOnAction(e -> userRegistration());
		buttonSearchBook.setOnAction(e -> searchBookByTitle());
		buttonBorrowBook.setOnAction(e -> borrowBook());
		buttonReturnBook.setOnAction(e -> returnBook());

		menuBar = new MenuBar();
		VBox vBox = new VBox(menuBar);

		tabPane = new TabPane();
		tabPane.setSide(Side.BOTTOM);

		VBox layout = new VBox(15);
		layout.setPadding(new Insets(20));
		layout.setAlignment(Pos.CENTER);
		layout.getChildren().addAll(buttonBook, buttonUser, buttonSearchBook, buttonBorrowBook, buttonReturnBook);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(vBox);   
		borderPane.setCenter(layout);
		borderPane.setBottom(tabPane); 

		Scene scene = new Scene(borderPane, 960, 600);
		primaryStage.setScene(scene);
		primaryStage.show();

		Core.getInstance().getPluginController().init();
	}

	@Override
	public MenuItem createMenuItem(String menuText, String menuItemText) {

		Menu newMenu = null;
		for (Menu menu : menuBar.getMenus()) {
			if (menu.getText().equals(menuText)) {
				newMenu = menu;
				break;
			}
		}
		if (newMenu == null) {
			newMenu = new Menu(menuText);
			menuBar.getMenus().add(newMenu);
		}

		MenuItem menuItem = new MenuItem(menuItemText);
		newMenu.getItems().add(menuItem);

		return menuItem;
	}

   @Override
	public boolean createTab(String tabText, Node contents) {
		Tab tab = new Tab();
		tab.setText(tabText);
		tab.setContent(contents);
		tabPane.getTabs().add(tab);

		return true;
	}

	private void userRegistration() {
		Stage userStage = new Stage();
		userStage.setTitle("Register User");
   
		TextField idField = new TextField();
		idField.setPromptText("User Id");
		TextField nameField = new TextField();
		nameField.setPromptText("Name");
		Button saveButton = new Button("Save");
   
		saveButton.setOnAction(e -> {
			try {
				int id = Integer.parseInt(idField.getText());
				String name = nameField.getText();
				
				if (userService.findById(id) != null) {
					alert("Error", "User Id already exists!");
					return;
				}
				if (name.isEmpty()) {
					alert("Error", "Name can't be empty!");
					return;
				}
		
				userService.createUser(id, name); 
				alertSuccessfull("Information", "User "  + name + " registered successfully!");
				userStage.close();
			} catch (NumberFormatException ex) {
				alert("Error", "User Id can only be an integer!");
			}
		});
   
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20));
		layout.getChildren().addAll(idField, nameField, saveButton);
   
		Scene scene = new Scene(layout, 300, 200);
		userStage.setScene(scene);
		userStage.show();
	}

	private void bookRegistration() {
		Stage bookStage = new Stage();
		bookStage.setTitle("Register Book");

		TextField isbnField = new TextField();
		isbnField.setPromptText("ISBN");

		TextField titleField = new TextField();
		titleField.setPromptText("Title");

		TextField authorField = new TextField();
		authorField.setPromptText("Author");

		DatePicker publishDatePicker = new DatePicker();
    	publishDatePicker.setPromptText("Publish Date");

		TextField bookGenreField = new TextField();
		bookGenreField.setPromptText("Book Genre");

		Button saveButton = new Button("Save");

		saveButton.setOnAction(e -> {
			try {
				String isbn = isbnField.getText();
				String title = titleField.getText();
				String author = authorField.getText();
				String bookGenre = bookGenreField.getText();
				LocalDate yearOfPublication = publishDatePicker.getValue();

				if (bookService.findByIsbn(isbn) != null) {
					alert("Error", "This ISBN already exists!");
					return;
				}
		
				if (title.isEmpty() || author.isEmpty() || yearOfPublication == null || bookGenre.isEmpty()) {
					alert("Error", "All fields must be filled in!");
					return;
				}
		
				LocalDate currentDate = LocalDate.now();
            	if (yearOfPublication.isAfter(currentDate)) {
                	alert("Error", "The publish date can't be in the future!");
                	return;
           		}
				
				bookService.createBook(isbn, title, author, yearOfPublication, bookGenre);
				alertSuccessfull("Information", "Book "  + title + " registered successfully!");
				bookStage.close();
			} catch (Exception ex) {
				alert("Error", "An unexpected error occurred!");
			}
		});
			

		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20));
		layout.getChildren().addAll(isbnField, titleField, authorField, publishDatePicker, bookGenreField, saveButton);

		Scene scene = new Scene(layout, 320, 250);
		bookStage.setScene(scene);
		bookStage.show();
	}

	private void searchBookByTitle () {
		Stage reportStage = new Stage();
		reportStage.setTitle("Search Book By Title");

		TextField titleField = new TextField();
		titleField.setPromptText("Enter the title of the book");

		TextArea reportArea = new TextArea();
		reportArea.setEditable(false);
		reportArea.setWrapText(true);
		
		Button searchButton = new Button("Search");

		searchButton.setOnAction(event -> {
			String title = titleField.getText().trim();
            List<IBook> foundBooks = bookService.findByTitle(title);
            StringBuilder sb = new StringBuilder();

            if (foundBooks.isEmpty()) {
                sb.append("No books with the title ").append(title).append("\n");
            } else {
                for (IBook book : foundBooks) {
                    sb.append("ISBN: ").append(book.getIsbn())
                    .append(" | Title: ").append(book.getTitle()).append("\n");
                }
            }
            reportArea.setText(sb.toString());
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(titleField, searchButton, reportArea);

        Scene scene = new Scene(layout, 400, 300);
        reportStage.setScene(scene);
        reportStage.show();
    }
    
    private void borrowBook() {
        Stage loanStage = new Stage();
        loanStage.setTitle("Borrow Book");

        TextField userIdField = new TextField();
        userIdField.setPromptText("User Id");

        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");

        DatePicker loanDatePicker = new DatePicker();
    	loanDatePicker.setPromptText("Loan Date");

        Button loanButton = new Button("Borrow");
        loanButton.setOnAction(e -> {
            try {
                int userId = Integer.parseInt(userIdField.getText().trim());
                String isbn = isbnField.getText();
                LocalDate loanDate = loanDatePicker.getValue();

                IUser user = userService.findById(userId);
                IBook book = bookService.findByIsbn(isbn);

                if (user == null) {
                    alert("Error", "User Id not found!");
                    return;
                }

                if (book == null) {
                    alert("Error", "Book not found!");
                    return;
                }

                if (user.getBorrowedBooks().size() >= 5) {
                    alert("Error", "The user " + user.getName() + " has already borrowed the maximum number of books (5)!");
                    return;
                }

                if (book.isBorrowed()) {
                    alert("Error", "The book " + book.getTitle() + " is already borrowed!");
                    return;
                }

                if (loanDate == null) {
					alert("Error", "The loan date field must be filled in!");
					return;
				}
	
				if (loanDate.isAfter(LocalDate.now())) {
					alert("Error", "The loan date can't be in the future!");
					return;
				}

                userService.borrowBook(userId, isbn, loanDate);
                alertSuccessfull("Information", "Book " + book.getTitle() + " borrowed successfully by " + user.getName() + "!");
                loanStage.close();
            } catch (NumberFormatException ex) {
                alert("Error", "User Id can only be integer!");
            } catch (Exception ex) {
                alert("Error", "An unexpected error occurred!");
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(userIdField, isbnField, loanDatePicker, loanButton);

        Scene scene = new Scene(layout, 300, 200);
        loanStage.setScene(scene);
        loanStage.show();
    }

    private void returnBook() {
        Stage returnStage = new Stage();
        returnStage.setTitle("Return Book");

        TextField userIdField = new TextField();
        userIdField.setPromptText("User Id");
        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");

        Button returnButton = new Button("Return");
        returnButton.setOnAction(e -> {
            try {
                int userId = Integer.parseInt(userIdField.getText());
                String isbn = isbnField.getText();
        
                IUser user = userService.findById(userId);
                IBook book = bookService.findByIsbn(isbn);
    
                if (user == null) {
                    alert("Error", "User Id not found!");
                    return;
                }

                if (book == null) {
                    alert("Error", "Book ISBN not found!");
                    return;
                }
                
                if (user.getBorrowedBooks().stream().noneMatch(b -> b.getIsbn().equals(book.getIsbn()))) {
                    alert("Error", "The user " + user.getName() + " didn't borrow the book " + book.getTitle() + "!");
                    return;
                }

                userService.returnBook(userId, isbn);
                alertSuccessfull("Information", "Book " + book.getTitle() + " returned successfully by " + user.getName() + "!");
                returnStage.close();
            } catch (NumberFormatException ex) {
                alert("Error", "User Id and ISBN can only be integers!");
            }
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(userIdField, isbnField, returnButton);

        Scene scene = new Scene(layout, 300, 200);
        returnStage.setScene(scene);
        returnStage.show();
    }

     private void alert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void alertSuccessfull(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}