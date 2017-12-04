public class AllFalseEntityVisitor implements EntityVisitor<Boolean>
{
    public Boolean visit(BlackSmith blackSmith){ return false; }
    public Boolean visit(MinerFull minerFull){
        return false;
    }
    public Boolean visit(MinerNotFull minerNotFull) { return false; }
    public Boolean visit(MinerZombie minerZombie) {return false;}
    public Boolean visit(Obstacle obstacle){
        return false;
    }
    public Boolean visit(Ore ore){ return false; }
    public Boolean visit(OreBlob oreBlob){
        return false;
    }
    public Boolean visit(PumpkinMan pumpkinMan) { return false; }
    public Boolean visit(Quake quake){ return false; }
    public Boolean visit(Vein vein){ return false; }
    public Boolean visit(Entity entity) {return false;}
}