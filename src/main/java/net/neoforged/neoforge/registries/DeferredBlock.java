package net.neoforged.neoforge.registries;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class DeferredBlock<T extends Block> extends DeferredHolder<Block, T> implements ItemConvertible
{
	public DeferredBlock(RegistryKey<Block> key)
	{
		super(key);
	}
	
	@Override
	public Item asItem()
	{
		return get().asItem();
	}
	
	public static <T extends Block> DeferredBlock<T> createBlock(Identifier id)
	{
		return createBlock(RegistryKey.of(RegistryKeys.BLOCK, id));
	}
	
	public static <T extends Block> DeferredBlock<T> createBlock(RegistryKey<Block> key)
	{
		return new DeferredBlock<>(key);
	}
}
