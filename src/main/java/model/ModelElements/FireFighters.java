package model.ModelElements;

import model.*;
import model.ModelElement.*;
import model.Visitors.*;
import util.Position;

import java.util.*;

public class FireFighters implements FireExtinguisherElement {
    private List<FireFighter> firefighters;
    private final GameBoard gameBoard;
    private FireElement fireElement;
    private final int initialFirefighterCount;

    public FireFighters(int initialFirefighterCount, GameBoard gameBoard, FireElement fireElement)
    {
        this.gameBoard=gameBoard;
        this.fireElement=fireElement;
        this.initialFirefighterCount=initialFirefighterCount;
    }

    @Override
    public void initializeElements() {
        firefighters= new ArrayList<>();
        List<Position> unvistablePositions=new ArrayList<>();
        int i=0;
        while (i<initialFirefighterCount)
        {
            Position position= gameBoard.randomPosition();
            if(unvistablePositions.contains(position))
                continue;
            if(isVisitable(position,new FireFighterVisitor())) {
                firefighters.add(new FireFighter(position));
                i++;
            }
            else
                unvistablePositions.add(position);
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
    public ModelElement getState(Position position)
    {
        if(firefighters!=null && contain(new FireFighter(position)))
            return new FireFighter(position);
        return null;
    }
    private boolean contain(FireFighter f)
    {
        for(FireFighter fireFighter: firefighters)
            if(f.getPosition().equals(fireFighter.getPosition()))
                return true;
        return false;
    }


    @Override
    public void extinguish(Position position)
    {
        for(Fire fire: fireElement.getFires())
            if(fire.getPosition().equals(position))
                fireElement.remove(fire);
    }


    private boolean contain(List<Position> seen, Position position)
    {
        for(Position pos: seen)
            if(position.equals(pos))
                return true;
        return false;
    }
    private boolean contain(Set<Position> seen, Position position)
    {
        for(Position pos: seen)
            if(position.equals(pos))
                return true;
        return false;
    }
    @Override
    public Position neighborClosestToFire(Position position,List<Position> visitableneighbor) {

        Set<Position> seen = new HashSet<>();
        HashMap<Position, Position> firstMove = new HashMap<>();
        Queue<Position> toVisit = new LinkedList<>(visitableneighbor);
        List<Position> firePostitions= fireElement.firePositions();
        for (Position initialMove : toVisit)
            firstMove.put(initialMove, initialMove);
        while (!toVisit.isEmpty()) {
            Position current = toVisit.poll();
            if (contain(firePostitions,current))
                return firstMove.get(current);
            for (Position adjacent : gameBoard.neighbors(current)) {
                if (contain(seen,adjacent)) continue;
                toVisit.add(adjacent);
                seen.add(adjacent);
                firstMove.put(adjacent, firstMove.get(current));
            }
        }
        return position;
    }
    @Override
    public List<Position> update() {
        List<Position> modifiedPosition = new ArrayList<>();
        List<FireFighter> firefighterNew = new ArrayList<>();
        List<Position> visitablePositions= new ArrayList<>();
        if(fireElement.firePositions().isEmpty())
        {
            for(FireFighter fireFighter: firefighters)
                modifiedPosition.add(fireFighter.getPosition());
            return modifiedPosition;
        }
        for (FireFighter firefighter : firefighters) {
            List<Position> firePositions= fireElement.firePositions();

            List<Position> visitableneighbors= new ArrayList<>();
            for(Position position: gameBoard.neighbors(firefighter.getPosition()))
            {
                if(visitablePositions.contains(position))
                {
                    visitableneighbors.add(position);
                    continue;
                }
                if (isVisitable(position, new FireFighterVisitor()))
                {
                    visitableneighbors.add(position);
                    visitablePositions.add(position);
                }
            }

            Position newFirefighterPosition = neighborClosestToFire(firefighter.getPosition(),visitableneighbors);
            firefighterNew.add(new FireFighter(newFirefighterPosition) );

            extinguish(newFirefighterPosition);
            modifiedPosition.add(firefighter.getPosition());
            modifiedPosition.add(newFirefighterPosition);
            List<Position> neighborFirePositions = gameBoard.neighbors(newFirefighterPosition).stream()
                    .filter(firePositions::contains).toList();

            for(Position firePosition : neighborFirePositions)
                extinguish(firePosition);
            modifiedPosition.addAll(neighborFirePositions);
        }
        firefighters =firefighterNew;
        return modifiedPosition;
    }

}
