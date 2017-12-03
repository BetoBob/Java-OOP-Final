public class ActivitiesVisitor extends AllFalseEntityVisitor
{
    public Boolean visit(MinerNotFull minerNotFull) { return true; }
    public Boolean visit(Vein vein){ return true; }
}