package edu.ou.cs2334.project5.presenters;

import java.io.File;
import java.io.IOException;

import edu.ou.cs2334.project5.handlers.OpenHandler;
import edu.ou.cs2334.project5.interfaces.Openable;
import edu.ou.cs2334.project5.models.CellState;
import edu.ou.cs2334.project5.models.NonogramModel;
import edu.ou.cs2334.project5.views.NonogramView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
// Worked on project with John C, Keeton, and Jared, this is consistent for the entire project
/**
 * Present the View of the MVC Model through JavaFx
 * 
 * @author Hadi Fawad
 * @version 1.0
 */

public class NonogramPresenter implements Openable {
	private NonogramView view;
	private NonogramModel model;
	private int cellLength;
	private static final String DEFAULT_PUZZLE = "puzzles/space-invader.txt";

/**
 * Construct and initialize the presenter
 * 
 * @param cellLength length of the cells
 * @throws IOException throws exception if file is null
 */
	public NonogramPresenter(int cellLength) throws IOException {
		this.cellLength = cellLength;
		model = new NonogramModel(DEFAULT_PUZZLE);
		view = new NonogramView();
		initializePresenter();
	}
	
	private void initializePresenter() {
		initializeView();
		bindCellViews();
		synchronise();
		configureButtons();
	}

/**
 * Use helper methods to initialize the presenter
 */
	public void initializeView() {
		view.initialize(model.getRowClues(), model.getColClues(), cellLength);
		if(getWindow() != null) {
			getWindow().sizeToScene();
		}
	}

/**
 * For each cell, add an EventHandler to handle mouse clicks	
 */
	public void bindCellViews() {
		for(int row = 0; row < model.getNumRows(); ++row) {
			for(int col = 0; col < model.getNumCols(); ++col) {
				final var rowFinal = row;
				final var colFinal = col;
				view.getCellView(row, col).setOnMouseClicked((MouseEvent event)->{
					if(event.getButton() == MouseButton.PRIMARY) {
						handleLeftClick(rowFinal, colFinal);
					}
					else if(event.getButton() == MouseButton.SECONDARY) {
						handleRightClick(rowFinal, colFinal);
					}
				});
			}
		}
	}
	
/**
 * Update the cell state at this grid position
 * 
 * @param rowIdx current index of the interacted row
 * @param colIdx current index of the interacted column
 */
	public void handleLeftClick(int rowIdx, int colIdx) {
		if(model.getCellStateAsBoolean(rowIdx, colIdx)) {
			updateCellState(rowIdx, colIdx, CellState.EMPTY);
		}
		else {
			updateCellState(rowIdx, colIdx, CellState.FILLED);
		}
	}

/**
 * Determine the new state of the cell based on the model's current cell state	
 * 
 * @param rowIdx current index of the interacted row
 * @param colIdx current index of the interacted column
 */
	public void handleRightClick(int rowIdx, int colIdx) {
		if(model.getCellStateAsBoolean(rowIdx, colIdx) || model.getCellState(rowIdx, colIdx) == CellState.EMPTY) {
			updateCellState(rowIdx, colIdx, CellState.MARKED);
		}
		else {
			updateCellState(rowIdx, colIdx, CellState.EMPTY);
		}
	}

/**
 * Update the model cell State
 * 
 * @param rowIdx current index of the interacted row
 * @param colIdx current index of the interacted column
 * @param state current state of the interacted cell(E,M,F)
 */
	public void updateCellState(int rowIdx, int colIdx, CellState state) {
		if(model.setCellState(rowIdx, colIdx, state)) {
			view.setCellState(rowIdx, colIdx, state);
			view.setRowClueState(rowIdx, model.isRowSolved(rowIdx));
			view.setColClueState(colIdx, model.isColSolved(colIdx));
		}
		
		if(model.isSolved()) {
			processVictory();
		}
	}
	
/**
 * Synchronize the state of the model and the view	
 */
	public void synchronise() {
		for(int row = 0; row < model.getNumRows(); ++row) {
			for(int col = 0; col < model.getNumCols(); ++col) {
				view.setCellState(row, col, model.getCellState(row, col));
				view.setRowClueState(row, model.isRowSolved(row));
				view.setColClueState(col, model.isColSolved(col));
				view.setPuzzleState(model.isSolved());
				if(model.isSolved()) {
					processVictory();
				}
			}
		}
	}
	
/**
 * Handle player victory and call on the method to display victory message and box	
 */
	public void processVictory() {
		removeCellViewMarks();
		view.showVictoryAlert();
	}
	
/**
 * Remove all marks (red X's) from the view	
 */
	public void removeCellViewMarks() {
		for(int row = 0; row < model.getNumRows(); ++row) {
			for(int col = 0; col < model.getNumCols(); ++col) {
				if(model.getCellState(row, col) == CellState.MARKED) {
					view.setCellState(row, col, CellState.EMPTY);
				}
			}
		}
	}
	
/**
 * Set the actions for the load and reset buttons	
 */
	public void configureButtons() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Load");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"));
		fileChooser.setInitialDirectory(new File("."));
		view.getLoadButton().setOnAction(new OpenHandler(getWindow(), fileChooser, this));
		view.getResetButton().setOnAction((ActionEvent event) -> {
			resetPuzzle();
		});
	}

/**
 *Clear the model using the reset method and synchronize the model/view	
 */
	public void resetPuzzle() {
		model.resetCells();
		synchronise();
	}

/**
 *get the pane associated with this presenter	
 */
	public Pane getPane() {
		return view;
	}
	
/**
 * get window pane associated with the presenter
 * 
 * @return a null pointer error or the window pane
 */
	public Window getWindow() {
		try {
			return view.getScene().getWindow();
		}
		catch(NullPointerException e) {
			return null;
		}
	}
	
/**
 * Re-initialize the model variable by calling the appropriate NonogramModel constructor
 * 
 * @param file get file.txt
 * @throws IOException throw IOException if file is null
 */
	public void open(File file) throws IOException {
		model = new NonogramModel(file);
		initializePresenter();
	}
	

}


