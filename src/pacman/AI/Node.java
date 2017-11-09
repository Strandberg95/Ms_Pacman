package pacman.AI;

import java.util.Hashtable;

import pacman.game.Constants.MOVE;

/**
 * Representing a node in the decision tree
 * @author KEJ
 *
 */
public class Node {
	
	private String attName;
	private Hashtable<String, Node>  attValues = new Hashtable<String, Node>();
	private MOVE move;
	
	public Node(String attName){
		this.attName = attName;
	}
	
	public String getAttName(){
		return attName;
	}
	
	/**
	 * Links a node to this node.
	 * @param value
	 * @param node
	 */
	public void addNode(String value, Node node){
		attValues.put(value, node);
	}
	
	/**
	 * Assigns a MOVE to the node. Assumes the node is a leaf node.
	 * @param move
	 */
	public void setMove(MOVE move){
		this.move = move;
	}
	
}
