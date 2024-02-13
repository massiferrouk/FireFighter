package model.ModelElements;
import model.*;
import model.ModelElement.*;
import model.Visitors.*;
import util.Position;

import java.util.*;

public class MotorizedFireFighters implements FireExtinguisherElement{

    private List<MotorizedFireFighter> motorizedFireFighters;
    private final GameBoard gameBoard;
    private FireElement fireElement;
    private final int initialMotorizedFirefighterCount;


    public MotorizedFireFighters(int initialMotorizedFirefighterCount, GameBoard gameBoard, FireElement fireElement)
    {
        this.gameBoard=gameBoard;
        this.fireElement=fireElement;
        this.initialMotorizedFirefighterCount=initialMotorizedFirefighterCount;
    }
    @Override
    public void initializeElements() {
        motorizedFireFighters= new ArrayList<>();
        List<Position> unvisitablePositions= new ArrayList<>();
        int i=0;
        while (i<initialMotorizedFirefighterCount)
        {
            Position position= gameBoard.randomPosition();
            if(unvisitablePositions.contains(position))
                continue;
            if(isVisitable(position,new MotorizedFireFighterVisitor())) {
                motorizedFireFighters.add(new MotorizedFireFighter(position));
                i++;
            }
            else
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
        if(motorizedFireFighters!=null && motorizedFireFighters.contains(new MotorizedFireFighter(position)))
            return new MotorizedFireFighter(position);
        return null;
    }

    public MotorizedFireFighter updateByOneStep(List<Position> modifiedPosition, List<MotorizedFireFighter> motorizedfirefighterNew
    ,List<Position> visitablePositions,MotorizedFireFighter motorizedFireFighter, List<Position> firePositions)
    {
            List<Position> visitableneighbors = new ArrayList<>();
            //récupérer les neighbors visitable
            for (Position position : gameBoard.neighbors(motorizedFireFighter.getPosition())) {
                if(visitablePositions.contains(position))
                {
                    visitableneighbors.add(position);
                    continue;
                }
                if (isVisitable(position, new MotorizedFireFighterVisitor())) {
                    visitableneighbors.add(position);
                    visitablePositions.add(position);
                }
            }

            Position newFirefighterPosition = neighborClosestToFire(motorizedFireFighter.getPosition(), visitableneighbors);
            MotorizedFireFighter motorizedFireFighterWithNewPos= new MotorizedFireFighter(newFirefighterPosition);
            motorizedfirefighterNew.add(motorizedFireFighterWithNewPos  );
            extinguish(newFirefighterPosition);
            modifiedPosition.add(motorizedFireFighter.getPosition());
            modifiedPosition.add(newFirefighterPosition);
            List<Position> neighborFirePositions = gameBoard.neighbors(newFirefighterPosition).stream()
                    .filter(firePositions::contains).toList();

            for (Position firePosition : neighborFirePositions)
                extinguish(firePosition);
            modifiedPosition.addAll(neighborFirePositions);

            return motorizedFireFighterWithNewPos;

    }


    @Override
    public List<Position> update() {
        List<Position> modifiedPosition = new ArrayList<>();
        List<Position> visitablePositions= new ArrayList<>();
        List<MotorizedFireFighter> motorizedfirefighterNew = new ArrayList<>();
        if(fireElement.firePositions().isEmpty())
        {
            for(MotorizedFireFighter motorizedFireFighter: motorizedFireFighters)
                modifiedPosition.add(motorizedFireFighter.getPosition());
            return modifiedPosition;
        }

        for(MotorizedFireFighter motorizedFireFighter: motorizedFireFighters)
        {
            List<MotorizedFireFighter> motorizedfirefighterNewinter = new ArrayList<>();
            List<Position> firePositions= fireElement.firePositions();

            MotorizedFireFighter motorizedFireFighter1=updateByOneStep(modifiedPosition,motorizedfirefighterNewinter,visitablePositions,motorizedFireFighter,firePositions);
            firePositions= fireElement.firePositions();
            updateByOneStep(modifiedPosition,motorizedfirefighterNew,visitablePositions,motorizedFireFighter1,firePositions);

        }
        motorizedFireFighters=motorizedfirefighterNew;
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
        Set<Position> seen = new HashSet<>();
        HashMap<Position, Position> firstMove = new HashMap<>();
        List<Position> firePositions= fireElement.firePositions();
        Queue<Position> toVisit = new LinkedList<>(visitableneighbor);
        for (Position initialMove : toVisit)
            firstMove.put(initialMove, initialMove);
        while (!toVisit.isEmpty()) {
            Position current = toVisit.poll();
            if (contain(firePositions,current))
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
    private boolean contain(Set<Position> seen, Position position)
    {
        for(Position pos: seen)
            if(position.equals(pos))
                return true;
        return false;
    }

    private boolean contain(List<Position> seen, Position position)
    {
        for(Position pos: seen)
            if(position.equals(pos))
                return true;
        return false;
    }
}

