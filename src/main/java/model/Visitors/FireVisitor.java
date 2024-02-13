package model.Visitors;
import model.ModelElement.*;


public class FireVisitor implements ModelElementVisitor {

    @Override
    public Boolean Visit(Fire fire)
    {
         return false;
    }

    @Override
    public Boolean Visit(FireFighter fireFighter) {
        return false;
    }

    @Override
    public Boolean Visit(Mountain mountain) {
        return false;
    }

    @Override
    public Boolean Visit(Cloud cloud) {
        return false;
    }

    @Override
    public Boolean Visit(Road road) {
        return false;
    }
    @Override
    public Boolean Visit(MotorizedFireFighter motorizedFireFighter) {
        return false;
    }


}
