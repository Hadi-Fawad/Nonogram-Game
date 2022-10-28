package edu.ou.cs2334.project5.views;

import edu.ou.cs2334.project5.models.CellState;

import javafx.scene.layout.GridPane;
// Worked on with John C, Keeton, and Jared, consistent throughout the project

/**
 * This class is a GridPane that displays the cell states
 * 
 * @author Hadi Fawad
 * @version 1.0
 */
public class CellGridView extends GridPane {
	
	private String STYLE_CLASS = "cell-grid-view";
	CellView[][] cellViews;

/**
 *  Create a two-dimensional array of CellViews and add them to 
 *  the GridPane at the positions with the same row and column indices
 *  
 * @param numRows count the number of rows in the file
 * @param numCols count the number of columns in the file
 * @param cellLength count the length of the cell
 */
	public CellGridView(int numRows, int numCols, int cellLength) {
		super();
		initCells(numRows, numCols, cellLength);
		getStyleClass().add(STYLE_CLASS);
	}
	
	
/**
 *  Initialize the cells of this view
 *  
 * @param numRows count the number of rows in the file
 * @param numCols count the number of columns in the file
 * @param cellLength count the length of the cell
 */
	public void initCells(int numRows, int numCols, int cellLength) {
		getChildren().clear();
		cellViews = new CellView[numRows][numCols];
		for(int i = 0; i < numRows; ++i) {
			for(int j = 0; j < numCols; ++j) {
				CellView thing = new CellView(cellLength);
				cellViews[i][j] = thing;
				thing.setSize(cellLength);
			}
			addRow(i, cellViews[i]);
		}
	}
	
/**
 * Get the CellView using the given indices	
 * 
 * @param rowIdx index of the row at a given cell
 * @param colIdx index of the column at a given cell
 * @return CellView view
 */
	public CellView getCellView(int rowIdx, int colIdx) {
		CellView view = cellViews[rowIdx][colIdx];
		return view;
	}
	
/**
 * 	Update the state of the CellView with the given indices
 * 
 * @param rowIdx index of the row at a given cell
 * @param colIdx index of the column at a given cell
 * @param state return cellview with given indices
 */
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		cellViews[rowIdx][colIdx].setState(state);
	}
}
