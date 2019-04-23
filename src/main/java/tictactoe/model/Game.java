package tictactoe.model;

import java.util.List;

public class Game {
	
	private Board board;
	private PlayerState playerState;
	private boolean playerGoFirst;
	private boolean nextMoveX;
	private final static int DEFAULT_ROW = 3;
    private final static int DEFAULT_COLUMN = 3;

	public Game() {
		startNew(DEFAULT_ROW, DEFAULT_COLUMN);
	}

	private void startNew(int rows, int columns) {
		playerGoFirst = true;
		nextMoveX = true;
		playerState = PlayerState.IN_PROGRESS;

        board = new Board(rows,columns);

//		if ( board == null ) {
//			board = new Board(rows,columns);
//		} else {
//			board.reset();
//		}
	}

	public Board getBoard() {
		return board;
	}

	public void markTile( String tileId ) {
		setTileValue( board.get( tileId ) );
	}
	
	public void markTileRandom() {
		setTileValue( board.getRandomAvailable() );
	}

	private void setTileValue( Tile tile ) {
		if ( isGameOver() || !tile.isEmpty() ) {
			return;
		}

		tile.setValue( nextMoveX ? Tile.Value.X : Tile.Value.O );
		nextMoveX = !nextMoveX;
		
		Tile.Value winValue = evaluateWinValue();
		if ( winValue != null ) {
			Tile.Value playerValue = playerGoFirst ? Tile.Value.X : Tile.Value.O;
			playerState = winValue == playerValue ? PlayerState.WIN : PlayerState.LOSS;
		} else {
			playerState = board.isFull() ? PlayerState.DRAW : PlayerState.IN_PROGRESS;
		}
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

	public void setPlayerGoFirst( boolean flag ) {
		this.playerGoFirst = flag;
	}

	public boolean isPlayerGoFirst() {
		return playerGoFirst;
	}
	
	public boolean isGameOver() {
		return playerState.isGameOver();
	}

	public void reset(int row, int col) {
		startNew(row,col);
	}
	
	public PlayerState getPlayerState() {
		return playerState;
	}
}
