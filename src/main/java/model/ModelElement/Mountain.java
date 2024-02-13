package model.ModelElement;
import model.Visitors.ModelElementVisitor;
import util.Position;
import view.*;

public class Mountain implements ModelElement {
    private Position position;
    public Mountain(Position position)
    {
        this.position=position;
    }



    public boolean accept(ModelElementVisitor modelElementVisitor)
    {
        return  modelElementVisitor.Visit(this);
    }

    @Override
    public ViewElement getViewElement() {
        return new MountainElement();
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public String toString()
    {
        return position.row()+" / "+ position.column()+" ";
    }


}
