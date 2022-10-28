package edu.ou.cs2334.project5.views;

import edu.ou.cs2334.project5.models.CellState;
import edu.ou.cs2334.project5.views.clues.LeftCluesView;
import edu.ou.cs2334.project5.views.clues.TopCluesView;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

//Worked on project with John C, Keeton, and Jared, this is consistent for the entire project
/**
* displays the row clues in the left position, the 
* column clues in the top position, and the cells in the middle position
* 
* @author Hadi Fawad
* @version 1.0
*/
public class NonogramView extends BorderPane {
	private String STYLE_CLASS = "nonogram-view";
	private String SOLVED_STYLE = "nonogram-view-solved";
	private LeftCluesView leftCluesView;
	private TopCluesView topCluesView;
	private CellGridView cellGridView;
	private HBox bottomHBox;
	private Button loadBtn;
	private Button resetBtn;
	
/**
 * Construct a NonogramView instance by add the style class "nonogram-view"	
 */
	public NonogramView() {
		super();
		getStyleClass().add(STYLE_CLASS);
	}
	
/**
 * Initialize the view
 * 	
 * @param rowClues
 * @param colClues
 * @param cellLength
 */
	public void initialize(int[][] rowClues, int[][] colClues, int cellLength) {
		int rowWidth = getLength(rowClues);
		int colHeight = getLength(colClues);
		leftCluesView = new LeftCluesView(rowClues, cellLength, rowWidth);
		topCluesView = new TopCluesView(colClues, cellLength, colHeight);
		initBottomHBox();
		cellGridView = new CellGridView(rowClues.length, colClues.length, cellLength);
		setLeft(leftCluesView);
		setTop(topCluesView);
		setCenter(cellGridView);
		setBottom(bottomHBox);
		setAlignment(getTop(), Pos.CENTER_RIGHT);
	}
	
/**
 * return length of clue
 * @param clues
 * @return value int
 */
	public int getLength(int[][] clues) {
		int value = clues[0].length;
		for(int i = 0; i < clues.length; ++i) {
			if(value < clues[i].length) {
				value = clues[i].length;
			}
		}
		return value;
	}
	
/**
 * Initialize the bottomHBox variable	
 */
	public void initBottomHBox() {
		bottomHBox = new HBox();
		bottomHBox.setAlignment(Pos.CENTER);
		loadBtn = new Button("Load");
		resetBtn = new Button("Reset");
		bottomHBox.getChildren().addAll(loadBtn, resetBtn);
	}

/**
 * Call getCellView on the CellGridView to get the CellView with the given indices	
 * 
 * @param rowIdx current index of the interacted row
 * @param colIdx current index of the interacted column
 * @return cellGridView
 */
	public CellView getCellView(int rowIdx, int colIdx) {
		return cellGridView.getCellView(rowIdx, colIdx);
	}

/**
 * 	Call setCellState on the CellGridView to update the state of the CellView with the given indices
 * 
 * @param rowIdx current index of the interacted row
 * @param colIdx current index of the interacted column
 * @param state 
 */
	public void setCellState(int rowIdx, int colIdx, CellState state) {
		cellGridView.setCellState(rowIdx, colIdx, state);
	}

/**
 * Call setRowState on the RowCluesView to update the state of the RowClueView with the given index
 * 	
 * @param rowIdx give the row index of a given cell
 * @param solved boolean state to determine if puzzle is completed or not
 */	
	public void setRowClueState(int rowIdx, boolean solved) {
		leftCluesView.setState(rowIdx, solved);
	}
	
/**
 * Call setColState on the ColCluesView to update the state of the ColClueView with the given index
 * 	
 * @param colIdx give the column index of a given cell
 * @param solved boolean state to determine if puzzle is completed or not
 */
	public void setColClueState(int colIdx, boolean solved) {
		topCluesView.setState(colIdx, solved);
	}

/**
 * if the puzzle is solved, add the style class "nonogram-view-solved"	
 * @param solved boolean state to determine if puzzle is completed or not
 */
	public void setPuzzleState(boolean solved) {
		if(solved) {
			getStyleClass().add(SOLVED_STYLE);
		}
		else {
			getStyleClass().remove(SOLVED_STYLE);
		}
	}
	
/**
 * Return the load button	
 * @return loadbtn
 */
	public Button getLoadButton() {
		return loadBtn;
	}

/**
 * Return the reset button	
 * @return resetbtn
 */
	public Button getResetButton() {
		return resetBtn;
	}
	
/**
 * Show a victory alert with a message, use txt and alert class	
 */
	public void showVictoryAlert() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Puzzle Solved");
		alert.setHeaderText("Congratulations!");
		ButtonType type = new ButtonType("OK", ButtonData.OK_DONE);
		alert.setContentText("You Win!");
		alert.getButtonTypes().setAll(type);
		alert.show();
	}
}
