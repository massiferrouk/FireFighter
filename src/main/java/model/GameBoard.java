package model;

import model.ModelElement.ModelElement;
import model.ModelElements.ModelElements;
import util.Position;

import java.util.*;

public class GameBoard implements Board<List<ModelElement>> {
    private final int columnCount;
    private final int rowCount;
    private int step=0;
    private List<ModelElements> gameelements;

    private final Random randomGenerator = new Random();

    public Position randomPosition() {
        return new Position(randomGenerator.nextInt(rowCount), randomGenerator.nextInt(columnCount));
    }
    public GameBoard(int columnCount, int rowCount)
    {
        this.columnCount=columnCount;
        this.rowCount=rowCount;
    }

    public List<Position> neighbors(Position position) {
        List<Position> list = new ArrayList<>();
        if (position.row() > 0) list.add(new Position(position.row() - 1, position.column()));
        if (position.column() > 0) list.add(new Position(position.row(), position.column() - 1));
        if (position.row() < rowCount() - 1) list.add(new Position(position.row() + 1, position.column()));
        if (position.column() < columnCount() - 1) list.add(new Position(position.row(), position.column() + 1));
        return list;
    }




    public void initializeElements(List<ModelElements> gameelements)
    {
        this.gameelements=new ArrayList<>();
        this.gameelements.addAll(gameelements);
        for(ModelElements elements: gameelements) {
            elements.initializeElements();
        }
    }


    @Override
    public List<ModelElement> getState(Position position) {

        List<ModelElement> result= new ArrayList<>();
        for(ModelElements element:gameelements)
            if(element.getState(position)!=null)
              result.add(element.getState(position));
        return result;
    }




    @Override
    public int rowCount() {
        return rowCount;
    }

    @Override
    public int columnCount() {
        return columnCount;
    }

    @Override
    public List<Position> updateToNextGeneration()
    {
        List<Position> modifiedPositions= new ArrayList<>();
        for(ModelElements element:gameelements)
          modifiedPositions.addAll(element.update());
        step++;
        return modifiedPositions;
    }

    @Override
    public void reset() {
        step=0;
        initializeElements(gameelements);
    }
    public void add(ModelElements elements)
    {
        gameelements.add(elements);
    }



    @Override
    public int stepNumber() {
        return step;
    }
}
