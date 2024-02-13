package model.Visitors;

import model.ModelElement.*;

public class CloudVisitor implements ModelElementVisitor {

    @Override
    public Boolean Visit(Fire fire) {
        return true;
    }

    @Override
    public Boolean Visit(FireFighter fireFighter) {
        return true;
    }

    @Override
    public Boolean Visit(Mountain mountain) {
        return true;
    }

    @Override
    public Boolean Visit(Cloud cloud) {
        return true;
    }

    @Override
    public Boolean Visit(Road road) {
        return true;
    }

    @Override
    public Boolean Visit(MotorizedFireFighter motorizedFireFighter) {
        return true;
    }

}
