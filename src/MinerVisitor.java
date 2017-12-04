public class MinerVisitor extends AllFalseEntityVisitor
{
    public Boolean visit(MinerFull miner) { return true; }
    public Boolean visit(MinerNotFull minerNotFull) { return true; }
}