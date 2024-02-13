package model.ModelElement;

import model.Visitors.ModelElementVisitor;
import util.Position;
import view.*;

import java.util.Objects;

public class Fire implements ModelElement {
    private Position position;

  public Fire(Position position)
  {
      this.position=position;

  }

    @Override
    public ViewElement getViewElement() {
        return new FireElement();
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fire fire = (Fire) o;
        return Objects.equals(position, fire.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }

    public String toString()
    {
        return position.row()+" / "+ position.column()+" ";
    }

}
