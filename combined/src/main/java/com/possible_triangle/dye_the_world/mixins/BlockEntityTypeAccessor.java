package com.possible_triangle.dye_the_world.mixins;

import java.util.Set;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockEntityType.class)
public interface BlockEntityTypeAccessor {

    @Accessor
    Set<Block> getValidBlocks();

    @Accessor
    void setValidBlocks(Set<Block> blocks);

}
