package tp1.logic;

/**
 * 
 * TODO: Immutable class to encapsulate and manipulate positions in the game board
 * 
 */
public class Position {

	
	// Atributos
		private int col;
		private int row;

		// Constructor
		public Position(int row, int col) {
			this.col = col;
			this.row = row;
		}

		// Getters
		
		
		// Metodo que clona la posicion
		public Position clone() {
			return new Position(row, col);
		}

		public int getCol() {
			return col;
		}

		public int getRow() {
			return row;
		}


		// Funci�n booleana respecto a si la posici�n esta en el borde del tablero
		public boolean isOnBorder() {
			
			boolean is = false;
			
			if (col == -1 || col == 30)
				is = true;
			
			return is;
		}
		
		// Funci�n booleana respecto a la equivalencia con otra posici�n pasada por parametro
		public boolean equals(Position pos) {

			// Variablees
			boolean result = false;
			
			// Si tienen la misma columna y fila son equivalentes
			if (col == pos.col && row == pos.row)
				result = true;
			return result;
		}

		// Funci�n booleana respecto a si la posici�n esta fuera del tablero
		public boolean isOut() {

			// Variablees
			boolean result = false;
			
			// Si tienen la misma columna y fila son equivalentes
			if (col < 0 || col > 29 || row < 0 || row > 14)
				result = true;
			return result;
		}

		@Override
		public String toString() {
			return "(" + col + ", " + row + ")";
		}
	

}
