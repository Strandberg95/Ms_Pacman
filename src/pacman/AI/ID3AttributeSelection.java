package pacman.AI;

import dataRecording.DataTuple;
import pacman.AI.DataExtraction.DataConverter;
import java.util.*;

public class ID3AttributeSelection {


    private static final String CLASS = "DirectionChosen";

    public static String getNextAttribute(DataTuple[] data, String[] attributes){

        HashMap<String,Double> gainA = new HashMap<>();
        String nextAttribute = "";

        double infoD = infoD(data);

        for(int i = 0; i < attributes.length; i++){
            LinkedList<LinkedList<DataTuple>> subset = extractSubsets(data,attributes[i]);
            double currInf = infoAD(data,subset);
            double currGain = (infoD - currInf);

            System.out.println("Info: " + infoD);
            System.out.println("Gain for " + attributes[i] + " : " + currGain);

            gainA.put(attributes[i],currGain);
        }

        Iterator it = gainA.entrySet().iterator();
        while (it.hasNext()) {
            double highestValue = Double.MIN_VALUE;

            Map.Entry pair = (Map.Entry)it.next();
            double value = (double)pair.getValue();


            if(value > highestValue){
                highestValue = value;
                nextAttribute = (String)pair.getKey();
                System.out.println("New attribute: " + nextAttribute);
            }

            it.remove(); // avoids a ConcurrentModificationException
        }
        return nextAttribute;

    }

    private static double infoAD(DataTuple[] data, LinkedList<LinkedList<DataTuple>> subset){
        double info = 0;
        int D = data.length;
        for(int i = 0; i < subset.size(); i++){

            LinkedList<DataTuple> list = subset.get(i);

            double infoDj = infoD(list.toArray(new DataTuple[0]));

            info += ((double)(list.size() / (double)D) * infoDj);
        }
        return info;
    }

    private static double infoD(DataTuple[] data){
        HashMap<String,Integer> map = new HashMap<>();

        int amount = 0;
        double gain = 0;

        for(int i = 0; i < data.length; i++){

            String key = DataConverter.convertDataTuple(CLASS,data[i]);

            if(map.containsKey(key)){
                map.put(key,(map.get(key)+1));
            }else{
                map.put(key,1);
            }

            amount++;
        }

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {

            Map.Entry pair = (Map.Entry)it.next();

            int value = (int)pair.getValue();

            gain += (-((double)value/(double)amount)*log2((double)value/(double)amount));

            it.remove(); // avoids a ConcurrentModificationException
        }

        return gain;
    }

    private static double log2(double n){
        return (Math.log(n) / Math.log(2));
    }

    private static LinkedList<LinkedList<DataTuple>> extractSubsets(DataTuple[] data, String attribute){
        LinkedList<LinkedList<DataTuple>> rList = new LinkedList<>();

        String[] values = extractValueVariety(data,attribute);

        for(int i = 0; i < values.length; i++){
            LinkedList<DataTuple> list = new LinkedList<>();
            for(int j = 0; j < data.length; j++){
                if(values[i].equals(DataConverter.convertDataTuple(attribute,data[j]))){
                    list.add(data[j]);
                }
            }
            rList.add(list);
        }
        return rList;
    }

    private static String[] extractValueVariety(DataTuple[] data, String attribute){
        LinkedList<String> rList = new LinkedList<>();
        for(int i = 0; i < data.length; i++){
            String s = DataConverter.convertDataTuple(attribute,data[i]);
            if(!rList.contains(s)){
                rList.add(s);
            }
        }
        return rList.toArray(new String[0]);
    }
}
