package edu.ou.cs2334.project5.handlers;

import javafx.stage.FileChooser;

import javafx.stage.Window;
/**
 * Abstract base class, used to help methods in OpenHandler and Openable
 * 
 * @author Hadi Fawad
 * Worked on project with Keeton, Jared, and John 
 * @version 4.0
 **/
public abstract class AbstractBaseHandler {

	protected Window window;
	protected FileChooser fileChooser;
		
	protected AbstractBaseHandler(Window window, FileChooser fileChooser) {
		this.window = window;
		this.fileChooser = fileChooser;
	}
}
