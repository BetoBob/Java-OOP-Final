public class OreBlobVisitor extends AllFalseEntityVisitor
{
    public Boolean visit(Vein vein) { return true; }
    public Boolean visit(MinerZombie minerZombie) { return true; }
}