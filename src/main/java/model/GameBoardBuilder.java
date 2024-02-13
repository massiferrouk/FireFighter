package model;

import model.ModelElements.*;

import java.util.ArrayList;
import java.util.List;

public class GameBoardBuilder {

    Immovables mountains;
    ModelElements clouds;
    FireElement fires ;
    FireExtinguisherElement firefighters;
    FireExtinguisherElement firefightersmotorized ;
    GameBoard gameBoard;
    Immovables roads;
    List<ModelElements> elements= new ArrayList<>();

    public GameBoardBuilder(int columnCount, int rowCount,int initialFiresCount ){
        gameBoard= new GameBoard(columnCount,rowCount);
        fires = new Fires(initialFiresCount,gameBoard);
    }

    public GameBoardBuilder setClouds(int initialCloudsCount) {
        this.clouds= new Clouds(initialCloudsCount, gameBoard, fires);
        elements.add(clouds);
        return this;

    }


    public GameBoardBuilder setFirefighters(int initialFireFightersCount) {
        firefighters = new FireFighters(initialFireFightersCount, gameBoard, fires);
        elements.add(firefighters);
        return this;
    }

    public GameBoardBuilder setFirefightersMotorized(int initialFireFightersCount) {
        firefightersmotorized = new MotorizedFireFighters(initialFireFightersCount, gameBoard, fires);
        elements.add(firefightersmotorized);
        return this;
    }



    public GameBoardBuilder setMountains(int initialMountainsCount) {
        mountains = new Mountains(initialMountainsCount, gameBoard);
        elements.add(mountains);
        return this;
    }

    public GameBoardBuilder setRoads(int initialMountainsCount) {
        roads = new Roads(initialMountainsCount, gameBoard);
        elements.add(roads);
        return this;
    }

    public GameBoard build()
    {

        elements.add(fires);
        gameBoard.initializeElements(elements);
        return gameBoard;
    }


}