public interface EntityVisitor<R> {
    R visit(BlackSmith blackSmith);
    R visit(MinerFull minerFull);
    R visit(MinerNotFull minerNotFull);
    R visit(MinerZombie minerZombie);
    R visit(Obstacle obstacle);
    R visit(Ore ore);
    R visit(OreBlob oreBlob);
    R visit(PumpkinMan pumpkinMan);
    R visit(Quake quake);
    R visit(Vein vein);
    R visit(Entity entity);
}
