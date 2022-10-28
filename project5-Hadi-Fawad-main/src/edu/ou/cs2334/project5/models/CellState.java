package edu.ou.cs2334.project5.models;

/**
 * This enum is used to represent the state of the NonogramModel grid.
 * 
 * @author Hadi Fawad
 * @version 1.0
 */


public enum CellState {

	EMPTY,
	FILLED,
	MARKED;
	
	/**
	 * create enumerations for all possible cell states,
	 * an empty cell, a marked(incorrect cell), and a filled(correct) cell
	 * 
	 * @param state current state of cell(E/F/M)
	 * @return boolean if state of cell true
	 */
	public static boolean toBoolean(CellState state) {
		return state.equals(FILLED);
	}
}
