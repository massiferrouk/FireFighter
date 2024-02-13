package model.ModelElements;

import model.ModelElement.ModelElement;
import util.Position;

import java.util.List;

public interface ModelElements {
    void initializeElements();
    ModelElement getState(Position position);
    List<Position> update();

}
