package model.ModelElements;

import model.*;
import model.ModelElement.*;
import model.Visitors.*;
import util.Position;

import java.util.ArrayList;
import java.util.List;

public class Mountains implements Immovables {

    private List<Mountain> mountains;
    private final int initialMountainsCount;
    private final GameBoard gameBoard;


    public Mountains(int initialMountainsCount, GameBoard gameBoard)
    {
        this.gameBoard=gameBoard;
        this.initialMountainsCount=initialMountainsCount;
    }

    @Override
    public List<Position> positions()
    {
        List<Position> mountainsPositions= new ArrayList<>();
        for(Mountain mountain:mountains)
            mountainsPositions.add(mountain.getPosition());
        return mountainsPositions;
    }

    @Override
    public void initializeElements()
    {
        mountains= new ArrayList<>();
        List<Position> unvisitable= new ArrayList<>();

        int i=0;
        while (i<initialMountainsCount)
        {
            Position position= gameBoard.randomPosition();
            if(unvisitable.contains(position))
                continue;
            if(isVisitable(position,new MountainsVisitor()))
            {
                mountains.add(new Mountain(position));
                i++;
            }
            unvisitable.add(position);
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
        if(mountains!=null && containMountain(new Mountain(position)))
            return new Mountain(position);
        return null;
    }

    private Boolean containMountain(Mountain m)
    {
        for(Mountain mountain: mountains)
            if(mountain.getPosition().equals(m.getPosition()))
                return true;
        return false;
    }
    @Override
    public List<Position> update() {
        return this.positions();
    }
}
