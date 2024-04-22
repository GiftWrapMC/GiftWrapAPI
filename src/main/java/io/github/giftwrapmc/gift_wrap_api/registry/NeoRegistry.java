package io.github.giftwrapmc.gift_wrap_api.registry;

import com.mojang.serialization.Lifecycle;
import io.github.giftwrapmc.gift_wrap_api.extensions.RegistryFreezeExtensions;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class NeoRegistry {

	/**
	 * Registers {@code entry} to {@code registry} under {@code id}.
	 *
	 * @return the passed {@code entry}
	 */
	public static <V, T extends V> T register(Registry<V> registry, Identifier id, T entry) {
		return register(registry, RegistryKey.of(registry.getKey(), id), entry);
	}

	/**
	 * Registers {@code entry} to {@code registry} under {@code key}.
	 *
	 * @return the passed {@code entry}
	 */
	@SuppressWarnings("unchecked")
	static <V, T extends V> T register(Registry<V> registry, RegistryKey<V> key, T entry) {
		((RegistryFreezeExtensions<V>) registry).neoAdd(key, entry, Lifecycle.stable());
		return entry;
	}
}
