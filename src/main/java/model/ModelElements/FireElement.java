package model.ModelElements;

import model.ModelElement.Fire;
import util.Position;

import java.util.List;

public interface FireElement extends ModelElements
{
   List<Position> firePositions();
   List<Fire> getFires();
   void remove(Fire fire);

}
