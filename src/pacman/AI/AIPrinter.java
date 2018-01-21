package pacman.AI;


import pacman.AI.DecisionTree.TreeNode;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class AIPrinter {
    /*
    private Graph graph;

    private final String GRAPH_NAME = "DECISION TREE GRAPH";
    private final String STYLESHEET =
            "node {" +
                    "	fill-color: green;" +
                    "}" +
                    "node.marked {" +
                    "	fill-color: red;" +
                    "}";


    public AIPrinter(TreeNode root){
        graph = this.createSingleGraph(GRAPH_NAME,STYLESHEET);
        addNodes(graph,root);
        System.out.println(root == null);
        addAttributesToNodes(graph);
//        graph.display();
    }

    private Graph createSingleGraph(String name, String styleSheet){
        Graph graph = new SingleGraph(name);
        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.setStrict(false);
        graph.setAutoCreate(true);
        return graph;
    }

    private void addAttributesToNodes(Graph graph){
        for (Node node : this.graph) {
            node.addAttribute("ui.label", node.getId());
        }
    }

    private void addNodes(Graph graph, TreeNode node){
//        System.out.println("Child size: " + node.getLinks().size());
        if(!node.isLeaf()){
            Hashtable<String,TreeNode> nodes = node.getLinks();
            graph.addNode(node.getAttName());

            Iterator it = nodes.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                addNodes(graph,(TreeNode)pair.getValue());
                graph.addEdge((String)pair.getKey(),node.getAttName(),((TreeNode) pair.getValue()).getAttName());
                it.remove(); // avoids a ConcurrentModificationException
            }
        }
    }
/*
    private void addEdges(TreeNode node){
        Hashtable<String,TreeNode> nodes = node.getLinks();
        graph.addNode(node.getAttName());
        Iterator it = nodes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            addNodes(graph,(TreeNode)pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
*/
}
