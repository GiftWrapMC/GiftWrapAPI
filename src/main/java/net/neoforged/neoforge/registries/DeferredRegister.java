package net.neoforged.neoforge.registries;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.neoforged.bus.api.IEventBus;

public class DeferredRegister<T>
{
	final String namespace;
	final RegistryKey<? extends Registry<T>> registryKey;
	
	public DeferredRegister(RegistryKey<? extends Registry<T>> key, final String modId)
	{
		this.namespace = modId;
		this.registryKey = key;
	}
	
	public void register(IEventBus bus)
	{
		bus.addListener(this::addEntries);
	}
	
	public void addEntries(RegisterEvent event)
	{
		if (!event.getRegistryKey().equals(this.registryKey))
		{
			return;
		}
		for (Map.Entry<DeferredHolder<T, ? extends T>, Supplier<? extends T>> e : entries.entrySet())
		{
			event.register(this.registryKey, e.getKey().getId(), () -> e.getValue().get());
			e.getKey().bind(false);
		}
	}
	
	public static <T> DeferredRegister<T> create(RegistryKey<? extends Registry<T>> key, String namespace)
	{
		return new DeferredRegister<>(key, namespace);
	}
	
	public <I extends T> DeferredHolder<T, I> register(String path, Supplier<? extends I> s)
	{
		return register(path, $ -> s.get());
	}
	
	Map<DeferredHolder<T, ? extends T>, Supplier<? extends T>> entries = new LinkedHashMap<>();
	
	public <I extends T> DeferredHolder<T, I> register(String path, Function<Identifier, ? extends I> func)
	{
		Identifier id = new Identifier(namespace, path);
		DeferredHolder<T, I> ret = createHolder(registryKey, id);
		
		entries.putIfAbsent(ret, () -> func.apply(id));
		
		return ret;
	}
	
	public <I extends T> DeferredHolder<T, I> createHolder(RegistryKey<? extends Registry<T>> registryKey, Identifier key)
	{
		return DeferredHolder.create(registryKey, key);
	}
	
	public static Blocks createBlocks(String modId)
	{
		return new Blocks(modId);
	}
	
	public static Items createItems(String modId)
	{
		return new Items(modId);
	}
	
	public static class Blocks extends DeferredRegister<Block>
	{
		public Blocks(final String modId)
		{
			super(RegistryKeys.BLOCK, modId);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <B extends Block> DeferredBlock<B> register(String path, Function<Identifier, ? extends B> s)
		{
			return (DeferredBlock<B>) super.register(path, s);
		}
		
		@Override
		public <B extends Block> DeferredBlock<B> register(String path, Supplier<? extends B> s)
		{
			return register(path, k -> s.get());
		}
		
		public <B extends Block> DeferredBlock<B> registerBlock(String path, Function<AbstractBlock.Settings, ? extends B> func, AbstractBlock.Settings settings)
		{
			return register(path, () -> func.apply(settings));
		}
		
		public DeferredBlock<Block> registerSimpleBlock(String path, AbstractBlock.Settings settings)
		{
			return registerBlock(path, Block::new, settings);
		}
		
		@Override
		public <I extends Block> DeferredBlock<I> createHolder(RegistryKey<? extends Registry<Block>> registryKey, Identifier key)
		{
			return DeferredBlock.createBlock(RegistryKey.of(registryKey, key));
		}
	}
	
	public static class Items extends DeferredRegister<Item>
	{
		public Items(final String modId)
		{
			super(RegistryKeys.ITEM, modId);
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public <B extends Item> DeferredItem<B> register(String path, Function<Identifier, ? extends B> s)
		{
			return (DeferredItem<B>) super.register(path, s);
		}
		
		@Override
		public <B extends Item> DeferredItem<B> register(String path, Supplier<? extends B> s)
		{
			return register(path, k -> s.get());
		}
		
		@Override
		public <I extends Item> DeferredItem<I> createHolder(RegistryKey<? extends Registry<Item>> registryKey, Identifier key)
		{
			return DeferredItem.createItem(RegistryKey.of(registryKey, key));
		}
	}
}
