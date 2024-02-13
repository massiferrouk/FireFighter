package model.ModelElement;

import model.Visitors.ModelElementVisitor;
import util.Position;
import view.CloudElement;
import view.ViewElement;

import java.util.Objects;

public class Cloud implements ModelElement {

    private Position position;

    public Cloud(Position position)
    {
        this.position=position;

    }


    @Override
    public ViewElement getViewElement() {
        return new CloudElement();
    }
    @Override
    public Position getPosition()
    {
        return position;
    }

    @Override
    public boolean accept(ModelElementVisitor modelElementVisitor)
    {
        return  modelElementVisitor.Visit(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cloud cloud = (Cloud) obj;
        return Objects.equals(position, cloud.position);
    }

    public String toString()
    {
        return position.row()+" / "+ position.column()+" ";
    }
}
