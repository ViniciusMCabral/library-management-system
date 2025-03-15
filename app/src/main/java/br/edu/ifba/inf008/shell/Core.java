package br.edu.ifba.inf008.shell;

import br.edu.ifba.inf008.interfaces.IBookService;
import br.edu.ifba.inf008.interfaces.ICore;
import br.edu.ifba.inf008.interfaces.IPluginController;
import br.edu.ifba.inf008.interfaces.IUIController;
import br.edu.ifba.inf008.interfaces.IUserService;
import br.edu.ifba.inf008.interfaces.LibraryDao;
import br.edu.ifba.inf008.persistence.LibraryFileDao;
import br.edu.ifba.inf008.service.BookService;
import br.edu.ifba.inf008.service.UserService;


public class Core extends ICore {

	private LibraryFileDao dao = new LibraryFileDao();
	private final BookService bookService = new BookService(dao);
	private final UserService service = new UserService(dao, bookService);
	private IPluginController pluginController = new PluginController();

	private Core() {
	}

	public static boolean init() {
		if (instance != null) {
			System.out.println("Fatal error: core is already initialized!");
			System.exit(-1);
		}
		
		instance = new Core();
		UIController.launch(UIController.class);

		return true;
	}

	public IUIController getUIController() {
		return UIController.getInstance();
	}
	
	public IUserService getUserService() {
		return service;
	}

	public IPluginController getPluginController() {
		return pluginController;
	}

	public LibraryDao getLibraryDao() {
		return this.dao;
	}

	public IBookService getIBookService() {
		return bookService;
	}
}
