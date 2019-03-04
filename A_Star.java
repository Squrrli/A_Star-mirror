/*
 * Austin Bohannon [18286119]
 * Niall Dillane [13132911]
 * Adam O'Mahony [16187504]
 *
 * This is the Interim Submission.
 */

import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* A_Star
 * Contains all logic to calculate the shortest solution to an 8-square game with
 * A*.
 */
public class A_Star {
	/* State
	 * Holds a single state of the game, calculating future moves and keeping
	 * track of the previous state.
	 */
	public static class State {
		// Board size constants
		public static final short BOARD_WIDTH = 3;
		public static final short BOARD_HEIGHT = 3;
		public static final short BOARD_SIZE = BOARD_WIDTH * BOARD_HEIGHT;

		// The goal state
		static short[] endBoard;

		// Private data
		State prev; // Previous board State (null if root)
		short[] board; // The board
		int depth; // Depth in the state tree (0 if root)
		int heuristic;

		/* Constructor
		 * Sets variables.
		 */
		public State(State prev, short[] board, int depth) {
			this.prev = prev;
			this.board = board;
			this.depth = depth;
			this.heuristic = heuristic();
		}

		/* getNextStates
		 * Calculates all possible moves and returns them as an ArrayList.
		 */
		public ArrayList<State> getNextStates() {
			// Find 0
			short index = -1;
			for(short i = 0; i < BOARD_SIZE; i++) {
				if (board[i] == 0) {
					index = i;
					break;
				}
			}
			if (index == -1) {
				throw new IndexOutOfBoundsException("Board does not contain a 0");
			}

			// Calculate next states
			ArrayList<State> possibleStates = new ArrayList<State>();
			// Check to switch with one above
			if (index > BOARD_WIDTH - 1) {
				short[] newBoard = new short[BOARD_SIZE];
				System.arraycopy(board, 0, newBoard, 0, board.length);
				newBoard[index] = newBoard[index - BOARD_WIDTH];
				newBoard[index - BOARD_WIDTH] = 0;
				possibleStates.add(new State(this, newBoard, depth + 1));
			}
			// Check to switch with one below
			if (index < BOARD_WIDTH * (BOARD_HEIGHT - 1)) {
				short[] newBoard = new short[BOARD_SIZE];
				System.arraycopy(board, 0, newBoard, 0, board.length);
				newBoard[index] = newBoard[index + BOARD_WIDTH];
				newBoard[index + BOARD_WIDTH] = 0;
				possibleStates.add(new State(this, newBoard, depth + 1));
			}
			// Check to switch with one to left
			if (index%BOARD_WIDTH > 0) {
				short[] newBoard = new short[BOARD_SIZE];
				System.arraycopy(board, 0, newBoard, 0, board.length);
				newBoard[index] = newBoard[index - 1];
				newBoard[index - 1] = 0;
				possibleStates.add(new State(this, newBoard, depth + 1));
			}
			// Check to switch with one to right
			if (index%BOARD_WIDTH < BOARD_WIDTH - 1) {
				short[] newBoard = new short[BOARD_SIZE];
				System.arraycopy(board, 0, newBoard, 0, board.length);
				newBoard[index] = newBoard[index + 1];
				newBoard[index + 1] = 0;
				possibleStates.add(new State(this, newBoard, depth + 1));
			}

			return possibleStates;
		}

		/* getEstimatedCost
		 * Returns f = g + h.
		 */
		public int getEstimatedCost() {
			return depth + heuristic;
		}

		/* setEndBoard
		 * Sets the goal board. Used to calculate heuristic.
		 */
		public static void setEndBoard(short[] endBoard) {
			State.endBoard = endBoard;
		}

		/* heuristic
		 * Calculates the heuristic function for this state.
		 * Uses number of tiles that are the same.
		 * Should only be called once by the constructor.
		 */
		int heuristic() {
			int h = 0;
			for (int i = 0; i < BOARD_SIZE; i++) {
				if (board[i] != endBoard[i]) {
					h++;
				}
			}
			return h;
		}

		/* toString
		 * Outputs the State as a String.
		 */
		@Override
		public String toString() {
			String output = "";
			for(short i = 0; i < BOARD_SIZE; i++) {
				output += board[i];
				if (i%BOARD_WIDTH == BOARD_WIDTH - 1)
					output += "\n";
				else
					output += " ";
			}
			output += "h = " + heuristic + "\n";
			return output;
		}
	}

	/* main
	 * Currently outputs the data for an example board.
	 */
	public static void main(String[] args) {
		// take input
		String inStart = JOptionPane.showInputDialog(null, 
			"Start State:\n* Numbers 0 through 8\n* In any order\n* Separated by spaces");
		String inEnd = JOptionPane.showInputDialog(null, 
			"End State:\n* Numbers 0 through 8\n* In any order\n* Separated by spaces");
		
		// pattern for 9 digits, 0-9, space separated
		String pattern = "^([0-9.]+\\s+){8}[0-9.]$";
		Pattern r = Pattern.compile(pattern);
		Matcher mS = r.matcher(inStart);
		Matcher mE = r.matcher(inEnd);
		
		if(! (mS.find() && mE.find()) ) {
			System.out.println("Invalid input, please try again.");
			System.exit(0);
		}

		// convert input into short arrays
		String[] startStrArr = inStart.split("\\s+");
		short[] startShortArr = new short[startStrArr.length];
		String[] endStrArr = inEnd.split("\\s+");
		short[] endShortArr = new short[endStrArr.length];
		for(int i = 0; i < startShortArr.length; i++){
			startShortArr[i] = Short.parseShort(startStrArr[i]);
		}
		for(int i = 0; i < endShortArr.length; i++){
			endShortArr[i] = Short.parseShort(endStrArr[i]);
		}

		// check for reused numbers 
		for(int i = 0; i < startShortArr.length; i++){
			if(startShortArr[i] < 0 || startShortArr[i] > 8 ||
				endShortArr[i] < 0 || endShortArr[i] > 8){
				System.out.println("Numbers outside 0-8 found, try again.");
				System.exit(0);
			}
			for(int j=0; j < startShortArr.length && i != j; j++){
				if(startShortArr[i] == startShortArr[j] &&
					endShortArr[i] == endShortArr[j]){
					System.out.println("Reused numbers, try again.");
					System.exit(0);
				}
			}
		}


		// Set goal board
		State.setEndBoard(new short[] {1, 2, 3, 4, 5, 6, 7, 8, 0});
		// Create starting State
		State state = new State(null, new short[] {1, 2, 3, 4, 0, 5, 6, 7, 8}, 0);
		// Print starting state
		System.out.println(state);
		System.out.println("=====\n");

		// Print all children states
		for(State i : state.getNextStates()) {
			System.out.println(i);
		}
	}
}
