package model.ModelElement;

import model.Visitors.ModelElementVisitor;
import util.Position;
import view.*;

import java.util.List;

public interface ModelElement{



     boolean accept(ModelElementVisitor modelElementVisitor);


     ViewElement getViewElement() ;

     Position getPosition();







}
