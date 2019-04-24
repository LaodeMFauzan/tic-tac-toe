package tictactoe.model;

public enum PlayerState {
	X_TURN,
	O_TURN,
	IN_PROGRESS,
	CROSS_WON,
	NOUGHT_WON,
	DRAW;
	

	public boolean isXTurn(){
		return this == X_TURN;
	}

	public boolean isOTurn(){
		return this == X_TURN;
	}

	public boolean isXWin() {
		return this == CROSS_WON;
	}
	
	public boolean isOWin() {
		return this == NOUGHT_WON;
	}
	
	public boolean isDraw() {
		return this == DRAW;
	}
	
	public boolean isGameOver() {
		return this == CROSS_WON || this == NOUGHT_WON || this == DRAW;
	}
	
}