package net.neoforged.neoforge.registries;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.neoforged.bus.api.Event;

public class RegisterEvent extends Event
{
	final RegistryKey<? extends Registry<?>> registryKey;
	final Registry<?> registry;
	
	public RegisterEvent(final RegistryKey<? extends Registry<?>> registryKey, final Registry<?> registry)
	{
		this.registryKey = registryKey;
		this.registry = registry;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> void register(RegistryKey<? extends Registry<T>> registryKey, Identifier name, Supplier<T> valueSupplier)
	{
		if (this.registryKey.equals(registryKey))
		{
			Registry.register((Registry) this.registry, name, valueSupplier.get());
		}
	}
	
	/**
	 * Calls the provided consumer with a register helper if the provided
	 * registry key matches this event's registry key.
	 *
	 * @param registryKey
	 *            the key of the registry to register objects to
	 * @param <T>
	 *            the type of the registry
	 * @see #register(ResourceKey, ResourceLocation, Supplier) a register
	 *      variant targeted towards registering one or two objects
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> void register(RegistryKey<? extends Registry<T>> registryKey, Consumer<RegisterHelper<T>> consumer)
	{
		if (this.registryKey.equals(registryKey))
		{
			consumer.accept((name, value) -> Registry.register((Registry) this.registry, name, value));
		}
	}
	
	/**
	 * @return The registry key linked to this event
	 */
	public RegistryKey<? extends Registry<?>> getRegistryKey()
	{
		return this.registryKey;
	}
	
	/**
	 * @return The registry linked to this event
	 */
	public Registry<?> getRegistry()
	{
		return this.registry;
	}
	
	/**
	 * @param key
	 *            the registry key to compare again {@link #getRegistryKey()}
	 * @return The registry typed to the given registry key if it matches
	 *         {@link #getRegistryKey()}, or {@code null} if it does not match.
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public <T> Registry<T> getRegistry(RegistryKey<? extends Registry<T>> key)
	{
		return key == this.registryKey ? (Registry<T>) this.registry : null;
	}
	
	@FunctionalInterface
	public interface RegisterHelper<T>
	{
		@Deprecated(forRemoval = true, since = "1.20.2")
		default void register(String name, T value)
		{
			throw new UnsupportedOperationException();
		}
		
		/**
		 * Registers the given value with the given name to the registry.
		 *
		 * @param key
		 *            the resource key of the object to register
		 * @param value
		 *            the object value
		 */
		default void register(RegistryKey<T> key, T value)
		{
			register(key.getRegistry(), value);
		}
		
		/**
		 * Registers the given value with the given name to the registry.
		 *
		 * @param name
		 *            the name of the object to register as its key
		 * @param value
		 *            the object value
		 */
		void register(Identifier name, T value);
	}
}
