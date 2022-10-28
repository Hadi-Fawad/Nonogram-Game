package edu.ou.cs2334.project5.models;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Worked on with John C, Keeton and Jared

/**
 *
 * This class encapsulates the state and rules of the game.
 * It stores arrays with the row clues, column clues, and
 * cell states (EMPTY, FILLED, or MARKED).
 * 
 * @author Hadi Fawad
 * @version 1.0
 */
public class NonogramModel {

	private static final String DELIMITER = " ";
	private static final int IDX_NUM_ROWS = 0;
	private static final int IDX_NUM_COLS = 1;

	private int[][] rowClues;
	private int[][] colClues;
	private CellState[][] cellStates;
	
/**
 *  Initialize the object using the given arrays of row and column clues
 *  
 *  @param rowClues capture row clues from given arrays
 *  @param colClues capture column clues from given arrays
 */
	public NonogramModel(int[][] rowClues, int[][] colClues) {
		
		this.rowClues = deepCopy(rowClues);
		this.colClues = deepCopy(colClues);
		
		cellStates = initCellStates(getNumRows(), getNumCols());
	}
/**
 *  Initialize the object using the row and column clues in the given file
 *  
 *  @param file read in file.txt
 */	
	public NonogramModel(File file) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String header = reader.readLine();
		String[] fields = header.split(DELIMITER);
		
		int numRows = Integer.parseInt(fields[IDX_NUM_ROWS]);
		int numCols = Integer.parseInt(fields[IDX_NUM_COLS]);
		cellStates = initCellStates(numRows, numCols);
		rowClues = readClueLines(reader, numRows);
		colClues = readClueLines(reader, numCols);
		reader.close();
	}

/**
*  Initialize the object using the row and column clues in the text file with the given name
*   
*  @param filename read in filename and capture information 
*/		
	public NonogramModel(String filename) throws IOException {
		NonogramModel temp = new NonogramModel(new File(filename));
		this.rowClues = deepCopy(temp.getRowClues());
		this.colClues = deepCopy(temp.getColClues());
		cellStates = initCellStates(getNumRows(), getNumCols());
	}
	
	/* Helper methods */
	
	// This is implemented for you
	private static CellState[][] initCellStates(int numRows, int numCols) {
		// Create a 2D array to store numRows * numCols elements
		CellState[][] cellStates = new CellState[numRows][numCols];
		
		// Set each element of the array to empty
		for (int rowIdx = 0; rowIdx < numRows; ++rowIdx) {
			for (int colIdx = 0; colIdx < numCols; ++colIdx) {
				cellStates[rowIdx][colIdx] = CellState.EMPTY;
			}
		}
		
		// Return the result
		return cellStates;
	}
	
	private static int[][] deepCopy(int[][] array) {
		//Code created with help from "Rorick".
		if (array == null) {
	        return null;
	    }

	    int[][] copy = new int[array.length][];
	    for (int i = 0; i < array.length; ++i) {
	        copy[i] = Arrays.copyOf(array[i], array[i].length);
	        
	    }
	    return copy; //Rorick a real one.
	}
	
	// This method is implemented for you. You need to figure out how it is useful.
	private static int[][] readClueLines(BufferedReader reader, int numLines) throws IOException {
		// Create a new 2D array to store the clues
		int[][] clueLines = new int[numLines][];
		
		// Read in clues line-by-line and add them to the array
		for (int lineNum = 0; lineNum < numLines; ++lineNum) {
			// Read in a line
			String line = reader.readLine();
			
			// Split the line according to the delimiter character
			String[] tokens = line.split(DELIMITER);
			
			// Create new int array to store the clues in
			int[] clues = new int[tokens.length];
			for (int idx = 0; idx < tokens.length; ++idx) {
				clues[idx] = Integer.parseInt(tokens[idx]);
			}
			
			// Store the processed clues in the resulting 2D array
			clueLines[lineNum] = clues;
		}
		
		// Return the result
		return clueLines;
	}
	
/**
 * Return the number of rows in the nonogram.
 * 
 * @return rowClues.length length of rowclues
 */
	public int getNumRows() {
		return rowClues.length;
	}

/**
 * Return the number of columns in the nonogram.
 * 
 * @return colClues.length length of collues
 */	
	public int getNumCols() {
		return colClues.length;
	}
	
/**
 * Return the state of the cell with the given row and column indices.
 * 
 * @param rowIdx return the state of cell at given row index
 * @param colIdx return the state of cell at given col index
 */
	public CellState getCellState(int rowIdx, int colIdx) {
		return cellStates[rowIdx][colIdx];
	}
	
/**
 * Return the boolean state of the cell with the given row and column indices
 * 
 * @param rowIdx return the state of cell at given row index
 * @param colIdx return the state of cell at given col index
 * @return boolean value representing a true(filled) or false 
 */
	public boolean getCellStateAsBoolean(int rowIdx, int colIdx) {
		return CellState.toBoolean(cellStates[rowIdx][colIdx]);
	}
	
/**
 * Set the state of the cell with the given indices
 * 
 * @param rowIdx return the state of cell at given row index
 * @param colIdx return the state of cell at given col index
 * @param state current state of cell from enumeration class
 * @return boolean value to represent if indice has changed
 */
	public boolean setCellState(int rowIdx, int colIdx, CellState state) {
		if(state == null || isSolved() || getCellState(rowIdx, colIdx) == state) {
			return false;
		}
		cellStates[rowIdx][colIdx] = state;
		return true;
	}
	
/**
 * Return a deep copy of the row clues
 * 
 * @return deepcopy from constructor
 */
	public int[][] getRowClues() {
		return deepCopy(rowClues);
	}

/**
 * Return a deep copy of the col clues
 * 
 * @return deepcopy from the constructor
 */
	public int[][] getColClues() {
		return deepCopy(colClues);
	}
	
/**
 * Return a copy of the row clue with the given index
 * 
 * @param rowIdx current index of cell row
 * @return a copy of the indiced row clues
 */
	public int[] getRowClue(int rowIdx) {
		int[] array = Arrays.copyOf(rowClues[rowIdx], rowClues[rowIdx].length);
		return array;
	}

/**
 * Return a copy of the column clue with the given index
 * 
 * @param colIdx current index of cell column
 * @return int[] a copy of the indiced col clues
 */
	public int[] getColClue(int colIdx) {
		int[] array = Arrays.copyOf(colClues[colIdx], colClues[colIdx].length);
		return array;
	}

/**
 * A row is solved if the projected cellStates row matches the row's clue
 * 
 * @param rowIdx current index of the row
 * @return boolean true if the given row is solved, false if not
 */
	public boolean isRowSolved(int rowIdx) {
		int[] curr = Arrays.copyOf(rowClues[rowIdx], rowClues[rowIdx].length);
		if(curr.length != projectCellStatesRow(rowIdx).length) {
			return false;
		}
		for(int i = 0; i < curr.length; ++i) {
			if(curr[i] != projectCellStatesRow(rowIdx)[i]) {
				return false;
			}
		}
		return true;
	}

/**
 * A column is solved if the projected cellStates column matches the column's clue
 * 
 * @param colIdx current index of the column
 * @return boolean true if the given column is solved, false if not
 */
	public boolean isColSolved(int colIdx) {
		int[] temp = Arrays.copyOf(colClues[colIdx], colClues[colIdx].length);
		if(temp.length != projectCellStatesCol(colIdx).length) {
			return false;
		}
		for(int i = 0; i < temp.length; ++i) {
			if(temp[i] != projectCellStatesCol(colIdx)[i]) {
				return false;
			}
		}
		return true;
	}
	
/**
 * Return true if the puzzle is solved; otherwise, return false
 * 
 * @return boolean
 */
	public boolean isSolved() {
		for(int i = 0; i < rowClues.length; ++i) {
			if(!isRowSolved(i)) {
				return false;
			}
		}
		for(int i = 0; i < colClues.length; ++i) {
			if(!isColSolved(i)) {
				return false;
			}
		}
		return true;
	}

/**
 * Change the state of all cells to EMPTY
 */
	public void resetCells() {
		for(int i = 0; i < cellStates.length; ++i) {
			for(int j = 0; j < cellStates[i].length; ++j) {
				cellStates[i][j] = CellState.EMPTY;
			}
		}
	}
	
/**
 * return the nonogram numbers of the given array of cell states
 * 
 * @param cells nonogram numbers of array cell states
 * @return arrayList given cell states
 */
	public static List<Integer> project(boolean[] cells) {
		ArrayList<Integer> stuff = new ArrayList<Integer>();
		int trueCount = 0;
		for(int i = 0; i < cells.length; ++i) {
			if(cells[i]) {
				++trueCount;
			}
			else {
				if(trueCount > 0) {
					stuff.add(trueCount);
					trueCount = 0;
				}
			}
		}
		if(trueCount > 0) {
			stuff.add(trueCount);
		}
		if(stuff.size() == 0) {
			stuff.add(0);
		}
		return stuff;
	}

/**
 * Return the projection of the cellStates row with the given index
 * 
 * @param rowIdx current index of the clicked row
 * @return int[] projection of cellStates
 */
	public int[] projectCellStatesRow(int rowIdx) {
		boolean[] temp = new boolean[colClues.length];
		for(int i = 0; i < temp.length; ++i) {
			temp[i] = getCellStateAsBoolean(rowIdx, i);
		}
		List<Integer> stuff = project(temp);
		int[] result = new int[stuff.size()];
		for(int i = 0; i < result.length; ++i) {
			result[i] = stuff.get(i);
		}
		return result;
	}

/**
 * Return the projection of the cellStates column with the given index
 * 
 * @param colIdx current index of the clicked column
 * @return int[] projection of cellStates
 */
	public int[] projectCellStatesCol(int colIdx) {
		boolean[] temp = new boolean[rowClues.length];
		for(int i = 0; i < temp.length; ++i) {
			temp[i] = getCellStateAsBoolean(i, colIdx);
		}
		List<Integer> stuff = project(temp);
		int[] result = new int[stuff.size()];
		for(int i = 0; i < result.length; ++i) {
			result[i] = stuff.get(i);
		}
		return result;
	}
}