public class MoveVisitor extends AllFalseEntityVisitor
{
    public Boolean visit(OreBlob oreBlob) { return true; }
    public Boolean visit(MinerFull minerFull) { return true; }
    public Boolean visit(MinerNotFull minerNotFull) { return true; }
    public Boolean visit(MinerZombie minerZombie) { return true; }
}