package pacman.AI;

import com.sun.jdi.connect.spi.TransportService;
import dataRecording.DataTuple;
import pacman.AI.DataExtraction.DataConverter;

import java.lang.reflect.Array;
import java.util.*;

public class ID3AttributeSelection {


    private static final String CLASS = "DirectionChosen";

    public static String getNextAttribute(DataTuple[] data, String[] attributes){
        //Calculate Info(D)
        double infoD = infoD(data);

        for(int i = 0; i < attributes.length; i++){

        }
        //For every A, calculate InfoA(D)
    }

    private static double InfoAD(DataTuple[] data, DataTuple[] subset, double infoD){

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

            gain += (-(value/amount)*log2(value/amount));

            it.remove(); // avoids a ConcurrentModificationException
        }
        return gain;
    }

    private static double log2(double n){
        return (Math.log(n) / Math.log(2));
    }

    private static ArrayList<LinkedList<DataTuple>> extractSubsets(DataTuple[] data, String attribute){

        ArrayList<LinkedList<DataTuple>> rList = new ArrayList<>();

        LinkedList<String> values = new LinkedList<>();

        for(int i = 0; i < data.length; i++){

            String s = DataConverter.convertDataTuple(attribute,data[i]);

            if(!values.contains(s)){
                values.add(s);
            }
        }

        for(int i = 0; i < values.size(); i++){

            LinkedList<DataTuple> list = new LinkedList<>();

            for(int j = 0; j < data.length; j++){

                String s = DataConverter.convertDataTuple(attribute,data[i]);

                if(values.get(i).equals(s)){
                    list.add(data[i]);
                }
            }
            rList.add(list);
        }

        return rList;
    }


    /*
    private static String calculateAttribute(DataTuple[] data, String[] attributes, double gain, double average){
        for(int i = 0; i < attributes.length; i++){
            average += calculateClassGain(data,attributes[i]);
        }

    }




*/
}
