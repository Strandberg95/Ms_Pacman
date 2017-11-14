package pacman.AI;

import pacman.controllers.Controller;
import pacman.game.Constants;
import pacman.game.Game;

public class AIController extends Controller<Constants.MOVE> {
    private AIBuilder aiBuilder = new AIBuilder();

    @Override
    public Constants.MOVE getMove(Game game, long timeDue) {
        return null;
    }
}
