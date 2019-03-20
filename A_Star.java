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
		 * Uses sum of distances from tiles to end locations
		 * Should only be called once by the constructor.
		 */
		int heuristic() {
			/* Board where the location of each index's no. is stored at that index
			 * e.g. Location of no. 3 in endBoard is stored at element 3 of refBoard
			 */
			short [] refBoard = new short[BOARD_SIZE];
			for(short i = 0; i < BOARD_SIZE; i++)
				refBoard[endBoard[i]] = i;

			int h = 0, dest=0, delta_x=0, delta_y=0;
			for (int curr = 0; curr < BOARD_SIZE; curr++) { 
				if (board[curr] != endBoard[curr] && board[curr] != 0) {
					dest = refBoard[board[curr]];
					delta_x = Math.abs(dest%BOARD_WIDTH - curr%BOARD_WIDTH);
					delta_y = Math.abs(dest/BOARD_WIDTH - curr/BOARD_WIDTH);
					h += delta_x + delta_y;
				}
			}
			return h; // + depth for f (but he wants h for now)
		}

		/* toString
		 * Outputs the State as a String.
		 */
		@Override
		public String toString() {
			String output = "";
			for(short i = 0; i < BOARD_SIZE; i++) {
				if (board[i] == 0)
					output += ' ';
				else
					output += board[i];

				if (i%BOARD_WIDTH == BOARD_WIDTH - 1)
					output += "\n";
				else
					output += " ";
			}
			for (short i = 0; i < (2 * BOARD_WIDTH) - 1; i++) {
				output += '-';
			}
			output += "\nh: " + heuristic + "\n";
			return output;
		}
	}

	/* Validator
	 * Validate and handle user input.
	 */
	protected static class Validator {
		private static String inputPat = "^([0-9.]\\s+){8}[0-8.]$";
		private static Pattern r = Pattern.compile(inputPat);
		private static Matcher stringMatcher;

		// TODO: write test cases

		/* parse
		 * Parses a string with the inputPat regex to confirm the user has adhered to the format.
		 */
		private static Boolean parse(String input) {
			stringMatcher= r.matcher(input);
			if (!stringMatcher.find()){
				return false;
			}

			return true;
		}

		/* formatInput
		 * Converts a string to a short[]
		 */
		private static short[] formatInput(String input){
			String[] strArr = input.split("\\s+");
			short[] shortArr = new short[strArr.length];
			for(int i = 0; i < shortArr.length; i++){
				shortArr[i] = Short.parseShort( strArr[i] );
			}

			return shortArr;
		}

		/* containsDuplicates
		 * Checks whether a short[] has duplicates in it.
		 */
		private static Boolean containsDuplicates(short[] arr){
			boolean[] seen = new boolean[State.BOARD_SIZE];
			for(int i = 0; i < arr.length; i++){
				short val = arr[i];
				if(val >= State.BOARD_SIZE || val < 0) {
					return true;
				}
				if(seen[val]) {
					return true;
				}

				seen[val] = true;
			}

			return false;
		}

		/* getBoard
		 * Takes the name of the board to show to the user ("Start"/"End") and returns a fully validated board.
		 */
		public static short[] getBoard(String name) {
			while(true) {
				String input = JOptionPane.showInputDialog(null,
					name + " State:\n* Numbers 0 through 8\n* In any order\n* Separated by spaces");
				if(input == null) {
					// The user hit "Cancel"
					System.exit(0);
				}
				if(!parse(input)) {
					JOptionPane.showMessageDialog(null, "Incorrect format. Try again.");
					continue;
				}
				short[] output = formatInput(input);
				if(containsDuplicates(output)) {
					JOptionPane.showMessageDialog(null, "Must contain all numbers [0, 8] and cannot have duplicates. Try again.");
					continue;
				}

				return output;
			}
		}
	}

	/* main
	 * Takes input from the user, validates it, and prints the state and its children to the screen.
	 */
	public static void main(String[] args) {
		// Get boards
		short start[] = Validator.getBoard("Start");
		short end[] = Validator.getBoard("End");

		// Set goal board
		State.setEndBoard(end);
		// Create starting State
		State state = new State(null, start, 0);
		// Print starting state
		System.out.println(state);
		for (short i = 0; i < (2 * State.BOARD_WIDTH) -1; i++) { // (BOARD_WIDTH numbers) + (BOARD_WIDTH - 1 spaces)
			System.out.print('=');
		}
		System.out.println('\n');

		// Print all children states
		for(State i : state.getNextStates()) {
			System.out.println(i);
		}
	}
}
