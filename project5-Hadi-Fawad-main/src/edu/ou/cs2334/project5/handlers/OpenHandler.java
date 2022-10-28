package edu.ou.cs2334.project5.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ou.cs2334.project5.interfaces.Openable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * The Nonogram Maker Model Class develops a model for functionality of the project
 * 
 * @author Hadi Fawad
 * Worked on project with Keeton, Jared, and John 
 * @version 4.0
 **/

public class OpenHandler extends AbstractBaseHandler implements EventHandler<ActionEvent> {

	private Openable opener;
	
	/**
	 * Handler will create the open option to open a given file which will then be read
	 * 
	 * @param window create a window to interact with save, open, and exit
	 * @param fileChoose choose file to open or save
	 * @param opener implements openable
	 */
	public OpenHandler(Window window, FileChooser fileChooser, Openable opener) {
	super(window, fileChooser);
	this.opener = opener;
	}
	
	/**
	 * Handle method will read in a file txt and look for errors
	 * 
	 * @param event if open happens, this event will be called and search for a file
	 * 
	 * @throws FileNotFoundException throws error if no file found
	 * @throws IOException if file is empty/null
	 */
	@Override
	public void handle(ActionEvent event) {
		File txt = fileChooser.showOpenDialog(window);
		if(txt != null) {
			try { opener.open(txt);
			} catch (FileNotFoundException e) {
				e.printStackTrace();}
					catch (IOException e) {
						e.printStackTrace();
				}
			}
		}
}
