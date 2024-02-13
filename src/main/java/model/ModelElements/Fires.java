package model.ModelElements;

import model.GameBoard;
import model.ModelElement.*;
import model.Visitors.*;
import util.Position;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Fires implements FireElement {
    public Set<Fire> fires;
    private final GameBoard gameBoard;
    private final int initialFireCount;

    public Fires(int initialFireCount,GameBoard gameBoard)
    {
        this.gameBoard=gameBoard;
        this.initialFireCount=initialFireCount;

    }


    @Override
    public List<Position> firePositions() {

        List<Position> firepositions= new ArrayList<>();
        for(Fire fire: fires)
            firepositions.add(fire.getPosition());
        return firepositions;
    }


    @Override
    public void initializeElements()
    {
        fires=new HashSet<>();
        List<Position> unvisitablePositions= new ArrayList<>();
        int i=0;
        while (i<initialFireCount)
        {

                Position position= gameBoard.randomPosition();
                if(unvisitablePositions.contains(position))
                   continue;
                if(isVisitable(position,new FireVisitor()))
                {
                    fires.add(new Fire(position));
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

        if(fires!=null && containFire(new Fire(position) ))
            return new Fire(position);
        return null;
    }

    private Boolean containFire(Fire f)
    {
        for(Fire fire: fires)
            if(fire.getPosition().equals(f.getPosition()))
                return true;
        return false;
    }


    @Override
    public List<Position> update() {
        List<Position> modifiedPositions = new ArrayList<>();
        if (gameBoard.stepNumber() % 2 == 0) {
            List<Position> newFirePositions = new ArrayList<>();
            Set<Fire> copy= new HashSet<>();
            copy.addAll(fires);
            Set<Position> unvisitablePostions= new HashSet<>();
            for (Fire fire : fires)
            {
                    List<Position> fireneighbors= gameBoard.neighbors(fire.getPosition());
                    for(Position position:fireneighbors) {
                        if (unvisitablePostions.contains(position))
                            continue;
                        if (isVisitable(position, new FireVisitor())) {
                            newFirePositions.add(position);
                            copy.add(new Fire(position));

                        }
                        unvisitablePostions.add(position);
                    }
                    fires=copy;
            }
            modifiedPositions.addAll(newFirePositions);
        }
        return modifiedPositions;
    }

    @Override
    public List<Fire> getFires()
    {
        return fires.stream().toList();
    }

    @Override
    public void remove(Fire f)
    {
        fires.remove(f);
    }
}
