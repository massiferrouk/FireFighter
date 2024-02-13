package model.ModelElements;

import model.*;
import model.ModelElement.*;
import model.ModelElement.ModelElement;
import model.Visitors.*;

import util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Clouds implements FireExtinguisherElement{
    private List<Cloud> clouds;
    private final GameBoard gameBoard;
    private FireElement fireElement;

    private final int initialCloudCount;


    public Clouds(int initialCloudCount, GameBoard gameBoard, FireElement fireElement)
    {
        this.fireElement=fireElement;
        this.gameBoard=gameBoard;
        this.initialCloudCount=initialCloudCount;
    }

    @Override
    public void initializeElements() {

        clouds= new ArrayList<>();
        int i=0;
        while (i<initialCloudCount)
        {
            Position position= gameBoard.randomPosition();
            if(isVisitable(position,new CloudVisitor())) {
                clouds.add(new Cloud(position));
                i++;
            }
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

        if(clouds!=null && containCloud(new Cloud(position)))
            return new Cloud(position);
        return null;
    }

    private boolean containCloud(Cloud c)
    {
        for(Cloud cloud: clouds)
            if(c.getPosition().equals(cloud.getPosition()))
                return true;
        return false;
    }

    @Override
    public List<Position> update() {
        List<Position> modifiedPosition = new ArrayList<>();
        List<Cloud> cloudNew = new ArrayList<>();
        if(fireElement.firePositions().isEmpty())
        {
            for(Cloud cloud: clouds)
                modifiedPosition.add(cloud.getPosition());
            return modifiedPosition;
        }
        for (Cloud cloud : clouds)
        {
            Position newClooudPosition = neighborClosestToFire(cloud.getPosition(),gameBoard.neighbors(cloud.getPosition()));
            cloudNew.add(new Cloud(newClooudPosition ));
            extinguish(cloud.getPosition());
            extinguish(newClooudPosition);
            modifiedPosition.add(newClooudPosition);
            modifiedPosition.add(cloud.getPosition());

        }

        clouds = cloudNew;
        return modifiedPosition;
    }



    @Override
    public void extinguish(Position position) {
            for(Fire fire: fireElement.getFires())
                if(fire.getPosition().equals(position))
                    fireElement.remove(fire);
    }


    @Override
    public Position neighborClosestToFire(Position position, List<Position> visitableneighbor) {
        Random gen= new Random();
        return visitableneighbor.get(gen.nextInt(visitableneighbor.size()));
    }
}
