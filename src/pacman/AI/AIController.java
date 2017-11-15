package pacman.AI;

import dataRecording.DataTuple;
import pacman.AI.DataExtraction.DataConverter;
import pacman.controllers.Controller;
import pacman.game.Constants;
import pacman.game.Game;

public class AIController extends Controller<Constants.MOVE> {
    private AIBuilder aiBuilder;
    private Node root;

    public AIController(){
        aiBuilder = new AIBuilder();
        root = aiBuilder.generate_tree();
    }

    @Override
    public Constants.MOVE getMove(Game game, long timeDue) {
        return getMove(root,new DataTuple(game, Constants.MOVE.NEUTRAL));
    }

    private Constants.MOVE getMove(Node node, DataTuple data){
        System.out.println("Recursion");

        if(node.isLeaf()){
            System.out.println("Selected Move: " + node.getMove().name() + "");
            return node.getMove();
        }else{
            Node link = node.getLink(DataConverter.convertDataTuple(node.getAttName(),data));
            
            return getMove(link,data);
        }
    }
}
