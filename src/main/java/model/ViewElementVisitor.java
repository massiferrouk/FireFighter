package model;

import model.ModelElement.*;
import view.ViewElement;

public interface ViewElementVisitor {
    ViewElement visit(Fire fire);
    ViewElement visit(FireFighter fireFighter);
    ViewElement visit(MotorizedFireFighter motorizedFireFighter);
    ViewElement visit(Cloud cloud);
    ViewElement visit(Mountain mountain);
    ViewElement visit(Road road);



}
