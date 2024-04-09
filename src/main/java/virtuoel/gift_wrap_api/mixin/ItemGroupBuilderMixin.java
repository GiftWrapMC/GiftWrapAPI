package virtuoel.gift_wrap_api.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.item.ItemGroup;
import virtuoel.gift_wrap_api.extensions.GiftWrapItemGroupBuilder;

@Mixin(ItemGroup.Builder.class)
public class ItemGroupBuilderMixin implements GiftWrapItemGroupBuilder
{
	
}
