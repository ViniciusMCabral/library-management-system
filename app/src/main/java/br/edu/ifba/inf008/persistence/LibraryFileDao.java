package br.edu.ifba.inf008.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import br.edu.ifba.inf008.entities.Library;
import br.edu.ifba.inf008.interfaces.ILibrary;
import br.edu.ifba.inf008.interfaces.LibraryDao;

public class LibraryFileDao implements LibraryDao {

    private static final String DATA_FILE = "library.bin";

    public void save(ILibrary library) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE));) {
            out.writeObject(library);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public ILibrary loadLibraryFromFile() {
        File file = new File(DATA_FILE);

        if (!file.exists()) {
            return new Library();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DATA_FILE));) {
            Library library = (Library) in.readObject();
            return library;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            System.out.println("Error loading library data: " + e.getMessage());
            return null;
        }
    }
}