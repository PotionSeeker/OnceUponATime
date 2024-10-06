package codyhuh.onceuponatime.registry;

import codyhuh.onceuponatime.OnceUponATime;
import codyhuh.onceuponatime.common.level.structure.piece.BowlStructurePiece;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

// Much of this code, along with other cave-gen code was taken from the Alex's Caves mod
public class ModStructurePieces {
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECES = DeferredRegister.create(Registries.STRUCTURE_PIECE, OnceUponATime.MOD_ID);

    public static final Supplier<StructurePieceType> BOWL = STRUCTURE_PIECES.register("bowl", () -> BowlStructurePiece::new);
}