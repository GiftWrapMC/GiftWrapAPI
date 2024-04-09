package net.neoforged.neoforge.registries;

import java.util.function.Supplier;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class DeferredHolder<R, T extends R> implements Supplier<T>
{
	public static <R, T extends R> DeferredHolder<R, T> create(RegistryKey<? extends Registry<R>> key, Identifier id)
	{
		return create(RegistryKey.of(key, id));
	}
	
	public static <R, T extends R> DeferredHolder<R, T> create(Identifier registryId, Identifier id)
	{
		return create(RegistryKey.ofRegistry(registryId), id);
	}
	
	public static <R, T extends R> DeferredHolder<R, T> create(RegistryKey<R> key)
	{
		return new DeferredHolder<>(key);
	}
	
	RegistryKey<R> key;
	RegistryEntry<R> holder = null;
	
	public DeferredHolder(RegistryKey<R> key)
	{
		this.key = key;
		bind(false);
	}
	
	@SuppressWarnings("unchecked")
	protected Registry<R> getRegistry()
	{
		return (Registry<R>) Registries.REGISTRIES.get(key.getRegistry());
	}
	
	public Identifier getId()
	{
		return getKey().getValue();
	}
	
	public RegistryKey<R> getKey()
	{
		return key;
	}
	
	protected void bind(boolean throwing)
	{
		if (holder == null)
		{
			Registry<R> r = getRegistry();
			if (r != null)
			{
				holder = r.getEntry(key).orElse(null);
			}
			else if (throwing)
			{
				throw new IllegalStateException();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T get()
	{
		bind(true);
		return (T) holder.value();
	}
}
