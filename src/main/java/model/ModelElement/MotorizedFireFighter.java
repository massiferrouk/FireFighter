package model.ModelElement;

import model.Visitors.ModelElementVisitor;
import util.Position;
import view.*;
import java.util.Objects;

public class MotorizedFireFighter implements ModelElement {
    private Position position;

    public  MotorizedFireFighter(Position position)
    {
        this.position=position;
    }



    @Override
    public boolean accept(ModelElementVisitor modelElementVisitor)
    {
        return  modelElementVisitor.Visit(this);
    }


    @Override
    public ViewElement getViewElement() {
        return new MotorizedFireFighterElement();
    }

    @Override
    public Position getPosition()
    {
        return position;
    }

    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MotorizedFireFighter motorizedfirefighter = (MotorizedFireFighter) obj;
        return Objects.equals(position, motorizedfirefighter.position);
    }

    public String toString()
    {
        return position.row()+" / "+ position.column()+" ";
    }


}
