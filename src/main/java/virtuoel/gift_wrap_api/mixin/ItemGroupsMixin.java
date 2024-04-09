package virtuoel.gift_wrap_api.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import net.minecraft.item.ItemGroups;

@Mixin(ItemGroups.class)
public class ItemGroupsMixin
{
	@Overwrite
	public static void collect()
	{
		
	}
}
