package model.ModelElements;

import util.Position;

import java.util.List;

public interface FireExtinguisherElement extends ModelElements {

    void extinguish(Position position);
    Position neighborClosestToFire(Position position, List<Position> visitableneighbor);

}
