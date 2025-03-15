package br.edu.ifba.inf008.interfaces;


public abstract class ICore
{
    public static ICore getInstance() {
        return instance;
    }

    public abstract IUIController getUIController();
    public abstract IPluginController getPluginController();

    protected static ICore instance = null;

	public abstract LibraryDao getLibraryDao();
	
	public abstract IUserService getUserService();
	
	public abstract IBookService getIBookService();
	
	
}
