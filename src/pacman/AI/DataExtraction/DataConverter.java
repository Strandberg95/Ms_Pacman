package pacman.AI.DataExtraction;

import dataRecording.DataTuple;

public class DataConverter {

    public static String convertDataTuple(String attribute, DataTuple tuple){
        String rString = null;
        switch(attribute){
            case("DirectionChosen"):
                rString = tuple.DirectionChosen + "";
                break;
            case("mazeIndex"):
                rString = tuple.mazeIndex + "";
                break;
            case("currentLevel"):
                rString = tuple.currentLevel + "";
                break;
            case("pacmanPosition"):
                rString = tuple.pacmanPosition + "";
                break;
            case("pacmanLives"):
                rString = tuple.pacmanLivesLeft + "";
                break;
            case("currentScore"):
                rString = tuple.discretizeCurrentScore(tuple.currentScore) + "";
                break;
            case("totalGameTime"):
                rString = tuple.discretizeTotalGameTime(tuple.totalGameTime) + "";
                break;
            case("currentLevelTime"):
                rString = tuple.discretizeCurrentLevelTime(tuple.currentLevelTime) + "";
                break;
            case("numOfPillsLeft"):
                rString = tuple.numOfPillsLeft + "";
                //rString = tuple.discretizeNumberOfPills(tuple.numOfPillsLeft) + "";
                break;
            case("numOfPowerPillsLeft"):
                rString = tuple.numOfPowerPillsLeft + "";
                break;
            case("isBlinkyEdible"):
                rString = tuple.isBlinkyEdible + "";
                break;
            case("isInkyEdible"):
                rString = tuple.isInkyEdible + "";
                break;
            case("isPinkyEdible"):
                rString = tuple.isPinkyEdible + "";
                break;
            case("isSueEdible"):
                rString = tuple.isSueEdible + "";
                break;
            case("blinkyDist"):
                rString = tuple.discretizeDistance(tuple.blinkyDist) + "";
                break;
            case("inkyDist"):
                rString = tuple.discretizeDistance(tuple.inkyDist) + "";
                break;
            case("pinkyDist"):
                rString = tuple.discretizeDistance(tuple.pinkyDist) + "";
                break;
            case("sueDist"):
                rString = tuple.discretizeDistance(tuple.sueDist) + "";
                break;
            case("blinkyDir"):
                rString = tuple.blinkyDir + "";
                break;
            case("inkyDir"):
                rString = tuple.inkyDir + "";
                break;
            case("pinkyDir"):
                rString = tuple.pinkyDir + "";
                break;
            case("sueDir"):
                rString = tuple.sueDir + "";
                break;
            case("numberOfNodesInLevel"):
                rString = tuple.numberOfNodesInLevel + "";
                break;
            case("numberOfTotalPillsInLevel"):
                rString = tuple.discretizeNumberOfPills(tuple.numberOfTotalPillsInLevel) + "";
                break;
            case("numberOfTotalPowerPillsInLevel"):
                rString = tuple.discretizeNumberOfPowerPills(tuple.numberOfTotalPowerPillsInLevel) + "";
                break;
            case("maximumDistance"):
                //Maximum distance was not contained in the datatuple-class. Therefore
                //We have decided to neglect it.
                break;
        }
        return rString;
    }
}
