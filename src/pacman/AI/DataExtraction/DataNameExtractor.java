package pacman.AI.DataExtraction;

import pacman.game.util.IO;

public class DataNameExtractor {

    private static String FileName = "data.txt";
    private static String selectionSign = " *";

    public static String[] readExtractionList(){
        String data = IO.loadFile(FileName);
        String[] dataLine = data.split("\n");
        return extractSpecifiedDataLines(dataLine,selectionSign);
    }

    private static int countSequences(String[] dataLine, String sign){
        int rSize = 0;
        for(int i = 0; i < dataLine.length; i++){
            if(dataLine[i].contains(sign)){
                rSize++;
            }
        }
        return rSize;
    }

    private static String[] extractSpecifiedDataLines(String[] dataLine, String sign){
        String[] rArr = new String[countSequences(dataLine,sign)];
        for(int i = 0,j = 0; i != dataLine.length;i++){
            if(dataLine[i].contains(sign)){
                rArr[j++] = dataLine[i].replace(" *","");
            }
        }
        return rArr;
    }
}