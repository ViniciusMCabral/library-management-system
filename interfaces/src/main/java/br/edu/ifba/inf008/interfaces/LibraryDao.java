package br.edu.ifba.inf008.interfaces;

public interface LibraryDao {
	
	void save(ILibrary library);

	ILibrary loadLibraryFromFile();
}
