package tictactoe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import tictactoe.model.Game;

@Controller
@SessionAttributes("game")
public class TicTacToeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index( @ModelAttribute("game" ) Game game ) {
		return "index";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String markTile( 
			@ModelAttribute("game") Game game,
			@RequestParam("tile_id") String tileId,
			@RequestParam(value = "board_size", defaultValue = "3") String size,
			@RequestParam(value = "new_game", required = false, defaultValue = "false") boolean newGame
			) {
		if ( newGame ) {
			game.reset(validateSize(size),validateSize(size));
		} else {
			game.markTile( tileId );
		}
		return "index";
	}

	private int validateSize(String size){
		// Size must be int, less than 8, and more than 2
		if(size.matches("\\d+") && Integer.valueOf(size) < 8 && Integer.valueOf(size) > 2) {
			return Integer.valueOf(size);
		} else {
			return 3;
		}
	}
	
	@ModelAttribute("game")
	public Game populateGame() {
		// populate object for first time if null (new session)
		return new Game(); 
	}

}