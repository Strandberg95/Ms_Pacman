package pacman.AI;

import java.util.Arrays;

import dataRecording.DataSaverLoader;
import dataRecording.DataTuple;
import pacman.AI.DataExtraction.DataConverter;
import pacman.AI.DataExtraction.DataNameExtractor;
import pacman.game.Constants.MOVE;

public class AIBuilder {
	
	// Initialize attribute list
	String[] attributeList = DataNameExtractor.readExtractionList();
	DataTuple[] dataSet = DataSaverLoader.LoadPacManData();
	
	/**
	 * Generates the decision tree
	 * @return
	 */
	public Node generate_tree(DataTuple[] dataSet, String[] attributeList){
		
		// Create node N
		Node node = new Node();
		
		// If every tuple in the data set has the same class (C), return N as a leaf node labeled as C
		if(everyTupleSameClass(dataSet)){
			String direction = DataConverter.convertDataTuple("DirectionChosen", dataSet[0]);
			node.setName(direction);
			node.setMove(DataConverter.convertStringToMOVE(direction));
			
			// otherwise if the attribute list is empty, return N as a leaf node labeled as majority class in data set
		}else if(attributeListEmpty(attributeList)){
			String direction = majorityClass(dataSet);
			node.setName(direction);
			node.setMove(DataConverter.convertStringToMOVE(direction));
			
		}else{
			
		}
		
		
		
		return null;
	}

	/**
	 * Finds the majority class in the data set.
	 * @param dataSet
	 * @return, UP, DOWN, LEFT, RIGHT or NEUTRAL
	 */
	private String majorityClass(DataTuple[] dataSet) {
		String majority = null;
		ClassificationCounter[] ccs = new ClassificationCounter[5];
		
		ccs[0] = new ClassificationCounter("UP");
		ccs[1] = new ClassificationCounter("DOWN");
		ccs[2] = new ClassificationCounter("LEFT");
		ccs[3] = new ClassificationCounter("RIGHT");
		ccs[4] = new ClassificationCounter("NEUTRAL");
		
		for(int i = 0; i < dataSet.length; i++){
			String direction = DataConverter.convertDataTuple("DirectionChosen", dataSet[i]);
			switch(direction){
				case("UP"):
					ccs[0].increment();
					break;
				case("DOWN"):
					ccs[1].increment();
					break;
				case("LEFT"):
					ccs[2].increment();
					break;
				case("RIGHT"):
					ccs[3].increment();
					break;
				case("NEUTRAL"):
					ccs[4].increment();
					break;				
			}
		}
		Arrays.sort(ccs);
		
		return ccs[4].classification();
	}

	/**
	 * Checks if the attribute list is empty
	 * @return
	 */
	private boolean attributeListEmpty(String[] attributeList) {
		return attributeList.length == 0;
	}

	/**
	 * Checks if every tuple in the data set is classified as the same class
	 * @return
	 */
	private boolean everyTupleSameClass(DataTuple[] dataSet) {
		
		boolean sameClass = true;
		String cls = DataConverter.convertDataTuple("DirectionChosen", dataSet[0]);
		
		for(int i = 0; i < dataSet.length; i++){
			if(!cls.equals(DataConverter.convertDataTuple("DirectionChosen", dataSet[i]))){
				sameClass = false;
				break;
			}
		}
		
		return sameClass;
	}
	
	/**
	 * Helper class for storing nbr of occurences of a certain direction (classification)
	 * @author KEJ
	 *
	 */
	private class ClassificationCounter implements Comparable{

		private String classification;
		private int nbr = 0;
		
		public ClassificationCounter(String classification){
			this.classification = classification;
		}
		
		public void increment() {
			nbr++;
		}

		public int frequency(){
			return nbr;
		}
		
		public String classification(){
			return classification;
		}
		
		@Override
		public int compareTo(Object arg0) {
			ClassificationCounter cc = (ClassificationCounter)arg0;
			return nbr - cc.frequency();
		}
		
	}

}
