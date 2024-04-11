package io.github.giftwrapmc.gift_wrap_api.mixin;

import org.spongepowered.asm.mixin.Mixin;

import io.github.giftwrapmc.gift_wrap_api.extensions.GiftWrapDyeColorExtensions;
import io.github.giftwrapmc.gift_wrap_api.hooks.ItemTagsHooks;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

@Mixin(DyeColor.class)
public class DyeColorMixin implements GiftWrapDyeColorExtensions
{
	TagKey<Item> forge$tag = null;
	
	@Override
	public TagKey<Item> getTag()
	{
		if (forge$tag == null)
		{
			forge$tag = ItemTagsHooks.create(new Identifier("forge", "dyes/" + ((DyeColor) (Object) this).getName()));
		}
		
		return forge$tag;
	}
}
