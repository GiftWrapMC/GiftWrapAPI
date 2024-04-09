package net.neoforged.neoforge.registries;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class DeferredItem<T extends Item> extends DeferredHolder<Item, T> implements ItemConvertible
{
	public DeferredItem(RegistryKey<Item> key)
	{
		super(key);
	}
	
	@Override
	public Item asItem()
	{
		return get();
	}
	
	public static <T extends Item> DeferredItem<T> createItem(Identifier id)
	{
		return createItem(RegistryKey.of(RegistryKeys.ITEM, id));
	}
	
	public static <T extends Item> DeferredItem<T> createItem(RegistryKey<Item> key)
	{
		return new DeferredItem<>(key);
	}
}
