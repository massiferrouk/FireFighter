package model.ModelElements;

import model.GameBoard;
import model.ModelElement.*;
import model.Visitors.*;
import util.Position;
import java.util.ArrayList;
import java.util.List;

public class Roads implements Immovables {
    private List<Road> roads;

    private final int initialRoadsCount;
    private final GameBoard gameBoard;


    public Roads(int initialRoadsCount, GameBoard gameBoard)
    {
        this.gameBoard=gameBoard;
        this.initialRoadsCount=initialRoadsCount;
    }
    @Override
    public List<Position> positions() {
        List<Position> roadsPositions= new ArrayList<>();
        for(Road road:roads)
            roadsPositions.add(road.getPosition());
        return roadsPositions;
    }

    @Override
    public void initializeElements() {
        List<Position> unvisitablePositions= new ArrayList<>();
        roads= new ArrayList<>();
        int i=0;
        while (i<initialRoadsCount)
        {
            Position position= gameBoard.randomPosition();
            if(unvisitablePositions.contains(position))
                continue;
            if(isVisitable(position,new RoadVisitor()))
            {
                roads.add(new Road(position));
                i++;
            }
                unvisitablePositions.add(position);

        }
    }
    public boolean  isVisitable(Position position,ModelElementVisitor modelElementVisitor)
    {
        List<ModelElement> modelElements = gameBoard.getState(position);
        for(ModelElement modelElement:modelElements)
            if(!modelElement.accept(modelElementVisitor))
                return false;
        return true;
    }

    @Override
    public ModelElement getState(Position position) {
        if(roads!=null && containRoad(new Road(position)))
            return new Road(position);
        return null;
    }

    private Boolean containRoad(Road r)
    {
        for(Road road: roads)
            if(road.getPosition().equals(r.getPosition()))
                return true;
        return false;
    }

    @Override
    public List<Position> update() {
        return this.positions();
    }

}
