package pacman.AI;

import dataRecording.DataTuple;
import pacman.AI.DataExtraction.DataConverter;
import pacman.AI.DecisionTree.AIBuilder;
import pacman.AI.DecisionTree.TreeNode;
import pacman.controllers.Controller;
import pacman.game.Constants;
import pacman.game.Game;

public class AIController extends Controller<Constants.MOVE> {
    private AIBuilder aiBuilder;
    private AIPrinter aiPrinter;
    private TreeNode root;

    public AIController(){
        aiBuilder = new AIBuilder();
        root = aiBuilder.generate_tree();
        aiPrinter = new AIPrinter(root);
    }

    @Override
    public Constants.MOVE getMove(Game game, long timeDue) {
        return getMove(root,new DataTuple(game, Constants.MOVE.NEUTRAL));
    }

    private Constants.MOVE getMove(TreeNode treeNode, DataTuple data){
        System.out.println("Recursion");

        if(treeNode.isLeaf()){
            System.out.println("Selected Move: " + treeNode.getMove().name() + "");
            return treeNode.getMove();
        }else{
            TreeNode link = treeNode.getLink(DataConverter.convertDataTuple(treeNode.getAttName(),data));
            
            return getMove(link,data);
        }
    }
}
