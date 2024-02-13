package model.ModelElement;
import model.Visitors.ModelElementVisitor;
import util.Position;
import view.*;

import java.util.Objects;

public class Road implements ModelElement {
    private Position position;
    public Road(Position position)
    {
        this.position=position;
    }

    @Override
    public boolean accept(ModelElementVisitor modelElementVisitor) {
        return modelElementVisitor.Visit(this);
    }

    @Override
    public ViewElement getViewElement() {
        return new RoadElement();
    }

    @Override
    public Position getPosition() {
        return position;
    }
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Road road = (Road) obj;
        return Objects.equals(position, road.position);
    }

    public String toString()
    {
        return position.row()+" / "+ position.column()+" ";
    }

}
