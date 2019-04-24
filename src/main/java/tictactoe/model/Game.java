package tictactoe.model;

import java.util.List;

public class Game {
	
	private Board board;
	private PlayerState playerState;
	private PlayerState playerTurn;
	private boolean playerGoFirst;
	private boolean nextMoveX;
	private final static int DEFAULT_ROW = 3;
    private final static int DEFAULT_COLUMN = 3;

	public Game() {
		initGame(DEFAULT_ROW, DEFAULT_COLUMN);
	}

	private void initGame(int rows, int columns) {
		playerGoFirst = true;
		nextMoveX = true;
		playerState = PlayerState.IN_PROGRESS;
		playerTurn = PlayerState.X_TURN;

        board = new Board(rows,columns);
	}

	public Board getBoard() {
		return board;
	}

	public void markTile( String tileId ) {
		setTileValue( board.get( tileId ) );
	}

	private void setTileValue( Tile tile ) {
		if ( isGameOver() || !tile.isEmpty() ) {
			return;
		}
		checkTurn();
		tile.setValue( nextMoveX ? Tile.Value.X : Tile.Value.O );
		nextMoveX = !nextMoveX;
		
		Tile.Value winValue = evaluateWinValue();
		if ( winValue != null ) {
			Tile.Value playerValue = playerGoFirst ? Tile.Value.X : Tile.Value.O;
			playerState = winValue == playerValue ? PlayerState.CROSS_WON : PlayerState.NOUGHT_WON;
		} else {
			if (board.isFull()){
				playerState = PlayerState.DRAW;
			}
		}
	}

	private void checkTurn(){
		if (nextMoveX)
			playerTurn = PlayerState.O_TURN;
		else
			playerTurn = PlayerState.X_TURN;
	}
	
	private Tile.Value evaluateWinValue() {

		List<List<Tile>> allLines = board.getAllLines();

		for ( List<Tile> line : allLines ) {
			Tile first = line.get( 0 );
			if ( first.isEmpty() ) {
				continue;
			}

			if ( line.stream().allMatch( t -> t.getValue() == first.getValue() ) ) {
				return first.getValue();
			}
		}
		return null;
	}

	public boolean isGameOver() {
		return playerState.isGameOver();
	}

	public void reset(int row, int col) {
		initGame(row,col);
	}
	
	public PlayerState getPlayerState() {
		return playerState;
	}

	public PlayerState getPlayerTurn() {
		return playerTurn;
	}
}
