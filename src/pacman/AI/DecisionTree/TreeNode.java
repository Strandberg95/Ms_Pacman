package pacman.AI.DecisionTree;

import java.util.*;

import pacman.game.Constants.MOVE;

/**
 * Representing a node in the decision tree
 * @author KEJ
 *
 */
public class TreeNode {
	
	private String attName;
	private Hashtable<String, TreeNode>  attValues;
	private MOVE move;

	public TreeNode(){
		attName = null;
		attValues = new Hashtable<String, TreeNode>();
		move = null;
	}
	
	public String getAttName(){
		return attName;
	}
	
	/**
	 * Links a treeNode to this treeNode.
	 * @param value
	 * @param treeNode
	 */
	public void addNode(String value, TreeNode treeNode){
		attValues.put(value, treeNode);
	}
	
	public void setName(String name){
		attName = name;
	}


	public MOVE getMove(){
		return this.move;
	}
	/**
	 * Assigns a MOVE to the node. Assumes the node is a leaf node.
	 * @param move
	 */
	public void setMove(MOVE move){
		this.move = move;
	}
	
	/**
	 * Returns the links to the child nodes.
	 * @return
	 */
	public Hashtable<String, TreeNode> getLinks(){
		return attValues;
	}

	/**
	 * Get and return the tree node which has the link passed as parameter.
	 * @param value, is the value of the attribute which leads to the next tree node.
	 * @return
	 */
	public TreeNode getLink(String value){

		if(attValues.get(value) == null){
			Random generator = new Random();
			Object[] values = attValues.values().toArray();
			Object randomValue = values[generator.nextInt(values.length)];
			return (TreeNode)randomValue;
		}else{
			return attValues.get(value);
		}


		//return attValues.get(value);

	}

	public boolean isLeaf(){
		if(move != null){
			return true;
		}else{
			return false;
		}
	}
	
}
