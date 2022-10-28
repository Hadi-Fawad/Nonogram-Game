package edu.ou.cs2334.project5.interfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface Openable {
	
	/**
	 * This method is a method that opens a file
	 * @param file this represents the file that is wanted to be open
	 * @throws FileNotFoundException is thrown when the file is not found
	 * @throws IOException 
	 */
	void open(File file) throws FileNotFoundException, IOException;
}