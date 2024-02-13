package model.Visitors;

import model.ModelElement.*;

public interface ModelElementVisitor {
    Boolean Visit(Fire fire);

    Boolean Visit(FireFighter fireFighter);

    Boolean Visit(Mountain mountain);

    Boolean Visit(Cloud cloud);

    Boolean Visit(Road road);

    Boolean Visit(MotorizedFireFighter motorizedFireFighter);



}
