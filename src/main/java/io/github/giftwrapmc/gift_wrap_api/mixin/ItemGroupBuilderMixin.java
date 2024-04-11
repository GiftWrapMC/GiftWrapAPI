package io.github.giftwrapmc.gift_wrap_api.mixin;

import org.spongepowered.asm.mixin.Mixin;

import io.github.giftwrapmc.gift_wrap_api.extensions.GiftWrapItemGroupBuilder;
import net.minecraft.item.ItemGroup;

@Mixin(ItemGroup.Builder.class)
public class ItemGroupBuilderMixin implements GiftWrapItemGroupBuilder
{
	
}
