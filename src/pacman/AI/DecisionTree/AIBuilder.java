package pacman.AI.DecisionTree;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;

import dataRecording.DataSaverLoader;
import dataRecording.DataTuple;
import pacman.AI.DataExtraction.DataConverter;
import pacman.AI.DataExtraction.DataNameExtractor;
import pacman.game.Constants;
import pacman.game.Constants.MOVE;

/**
 * Managing the build of the classification tree
 * @author KEJ
 *
 */
public class AIBuilder {
	
	// Initialize attribute list
	String[] attributeList = DataNameExtractor.readExtractionList();
	DataTuple[] dataSet = DataSaverLoader.LoadPacManData();
	TreeNode root;
	
	/**
	 * Entry method for creating the classification tree
	 * @return the root of the classification tree
	 */
	public TreeNode generate_tree(){
		root = generate_tree(dataSet, attributeList);
		System.out.println("Tree");
		printTree(root, "");
		calculateAndPrintAccuracy(dataSet);
		return root;
	}
	
	/**
	 * Calculates and print the accuracy of the data set classifier combination
	 * Accuracyn is calculated using a holdout method.
	 * @param fullDataSet
	 */
	private void calculateAndPrintAccuracy(DataTuple[] fullDataSet) {
		System.out.println("PrintAccuracy");
		
		// Split in training set and test set
		int testDataLength = fullDataSet.length/3;
		int trainingDataLength = fullDataSet.length - testDataLength;
		DataTuple[] trainingData = new DataTuple[trainingDataLength];
		int trainingDataNextPos = 0;
		DataTuple[] testData = new DataTuple[testDataLength];
		int testDataNextPos = 0;
		
		int counter = 1;
		for(int i = 0; i < fullDataSet.length; i++ ){
			if(counter < 3){
				trainingData[trainingDataNextPos] = fullDataSet[i];
				trainingDataNextPos++;
				counter++;
			}else{
				counter = 1;
				testData[testDataNextPos] = fullDataSet[i];
				testDataNextPos++;
			}
		}
		
		//Debug
//		System.out.println("fullDataSet.length = " + fullDataSet.length);
//		for(int j = 0; j < fullDataSet.length; j++){
//			System.out.println(fullDataSet[j].getSaveString());
//		}
//		System.out.println("\ntrainingData.length = " + trainingData.length);
//		for(int j = 0; j < trainingData.length; j++){
//			System.out.println(trainingData[j].getSaveString());
//		}
//		System.out.println("\ntestData.length = " + testData.length);
//		for(int j = 0; j < testData.length; j++){
//			System.out.println(testData[j].getSaveString());
//		}
		//End debug
		
		// Create tree from the training data set
		TreeNode rootTraining = generate_tree(trainingData, attributeList);
	
		// Calculate the accuracy
		float accurate = 0;
		float inaccurate = 0;
		int errorInTuple = 0;
		
		for(int i = 0; i < testData.length; i++){
			Constants.MOVE testTupleMove = testData[i].DirectionChosen;
			try{
				Constants.MOVE classifiedMove = getMove(rootTraining, testData[i]);
				if(testTupleMove == classifiedMove){
					accurate++;
				}else{
					inaccurate++;
				}
			}catch (NullPointerException e){
				errorInTuple++;
			}
			
		}
		
		float accuracy = (accurate - inaccurate)/(accurate + inaccurate);
		System.out.println("\n================= Statistics ===============");
		System.out.println("Number of accurate classifications. = " + accurate);
		System.out.println("Number of inacurate classifications  = " + inaccurate);
		System.out.println("Nuber of tuples containing values not known in training data = " + errorInTuple);
		System.out.println("Accuracy = " + accuracy);
	}
	
	/**
	 * Via the classification tree, gets the move from the data tuple, i.e. how the tuple is classified
	 * by the classification tree.
	 * @param rootTraining
	 * @param dataTuple
	 * @return
	 */
	private MOVE getMove(TreeNode treeNode, DataTuple dataTuple) {
        if(treeNode.isLeaf()){
            return treeNode.getMove();
        }else{
            TreeNode link = treeNode.getLink(DataConverter.convertDataTuple(treeNode.getAttName(),dataTuple));
            
            return getMove(link,dataTuple);
        }

	}

	/**
	 * Prints the newly created classification tree
	 * @param node, the current tree node
	 * @param indent, current indent for the tree nodes "level". Used for creating the visible structure
	 */
	private void printTree(TreeNode node, String indent) {
		System.out.println(indent + "    " + "Node. Attribute = " + node.getAttName());
		
		if(!node.isLeaf()){
			Hashtable<String, TreeNode> values = node.getLinks();
			String indentedMore = indent + "        ";
			for(String key: values.keySet()){
				System.out.println(indentedMore + "Link. Value = " + key);
				printTree(values.get(key), indentedMore);
			}			
		}else{
			System.out.println(indent + "    " + "MOVE = " + node.getMove());
		}
	}

	/**
	 * Generates the decision tree
	 * @return a TreeNode.
	 */
	private TreeNode generate_tree(DataTuple[] dataSet, String[] attributeList){
		
		// Create treeNode N
		TreeNode treeNode = new TreeNode();
		
		// If every tuple in the data set has the same class (C), return N as a leaf treeNode labeled as C
		if(everyTupleSameClass(dataSet)){
			String direction = DataConverter.convertDataTuple("DirectionChosen", dataSet[0]);
			treeNode.setName(direction);
			treeNode.setMove(DataConverter.convertStringToMOVE(direction));
			
			// otherwise if the attribute list is empty, return N as a leaf treeNode labeled as majority class in data set
		}else if(attributeListEmpty(attributeList)){
			String direction = majorityClass(dataSet);
			treeNode.setName(direction);
			treeNode.setMove(DataConverter.convertStringToMOVE(direction));
			
		}else{  // Label N as A and remove A from the attribute list
			String attribute = ID3AttributeSelection.getNextAttribute(dataSet, attributeList);
			treeNode.setName(attribute);
			String[] reducedAttributeList = attributeListMinus(attribute, attributeList);
			
			// For each value aj in attribute A..
			String[] valuesInAttribute = allValuesInAttribute(attribute, dataSet);
			for(int a = 0; a < valuesInAttribute.length; a++){
				String valueAj = valuesInAttribute[a];
				
				// Creates a subset of D so that attribute A takes the value aj, creating the subset dj
				DataTuple[] subSetDj = createSubSet(dataSet, attribute, valueAj);
				
				// If Dj is empty, add a child treeNode to N labeled with the majority class in D
				if(subSetDj.length == 0){
					TreeNode childTreeNode = new TreeNode();
					String direction = majorityClass(dataSet);
					childTreeNode.setName(direction);
					childTreeNode.setMove(DataConverter.convertStringToMOVE(direction));
					treeNode.addNode(valueAj, childTreeNode);
				}else{
					treeNode.addNode(valueAj, generate_tree(subSetDj, reducedAttributeList));
				}
			}
		}
		return treeNode;
	}

	/**
	 * Creates a subset of D so that attribute A takes the value aj, creating the subset dj
	 * @param dataSet
	 * @param attribute
	 * @param valueAj
	 * @return
	 */
	private DataTuple[] createSubSet(DataTuple[] dataSet, String attribute, String valueAj) {
		LinkedList<DataTuple> tuplesDj = new LinkedList<DataTuple>();
		DataTuple[] dj = new DataTuple[0];
		
		for(int i = 0; i < dataSet.length; i++){
			if(DataConverter.convertDataTuple(attribute, dataSet[i]).equals(valueAj)){
				tuplesDj.add(dataSet[i]);
			}
		}
		
		if(tuplesDj.size() == 0){
			
		}else{
			
			dj = new DataTuple[tuplesDj.size()];
			for(int j = 0; j < dj.length; j++){
				dj[j] = tuplesDj.removeFirst();
			}			
		}
		
		return dj;
	}

	/**
	 * Recieves all the values in attribute specified in attribute.
	 * @param attribute
	 * @param dataSet, the whole data set from the recording.
	 * @return
	 */
	private String[] allValuesInAttribute(String attribute, DataTuple[] dataSet) {
		String[] valuesInAttribute = null;
		Hashtable<String, String> values = new Hashtable<String, String>();
		for(int i = 0; i < dataSet.length; i++){
			values.put(DataConverter.convertDataTuple(attribute, dataSet[i]), "");
		}
		
		valuesInAttribute = new String[values.size()];
		int index = 0;
		for(String key: values.keySet()){
			valuesInAttribute[index] = key;
			index++;
		}
		
		return valuesInAttribute;
	}

	/**
	 * Removes the attrubute i first parameter from the attribute list in the second parameter
	 * @param attribute
	 * @param attributeList2
	 * @return the attrubute list without the attrubute specified in the first parameter.
	 */
	private String[] attributeListMinus(String attribute, String[] attributeList) {
		
		String[] reducedAttributeList = new String[attributeList.length - 1];
		int ri = 0; 
		for(int i = 0; i < attributeList.length; i++){
			if(!attributeList[i].equals(attribute)){
//				System.out.println("attribute = " + attribute + " , attributeList[" + i + "]" + attributeList[i]);
				reducedAttributeList[ri] = attributeList[i];
				ri++;
			}
		}
		
		return reducedAttributeList;
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
