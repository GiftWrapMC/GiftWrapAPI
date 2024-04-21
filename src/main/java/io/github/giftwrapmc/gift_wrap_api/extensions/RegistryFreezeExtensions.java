package io.github.giftwrapmc.gift_wrap_api.extensions;

import com.mojang.serialization.Lifecycle;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;

public interface RegistryFreezeExtensions<T> {

	Registry<T> partiallyFreeze();

	RegistryEntry.Reference<T> neoAdd(RegistryKey<T> key, T entry, Lifecycle lifecycle);

	RegistryEntry.Reference<T> neoSet(int rawId, RegistryKey<T> key, T value, Lifecycle lifecycle);

	RegistryEntry.Reference<T> neoGetOrCreateEntry(RegistryKey<T> key);

	RegistryEntry.Reference<T> neoCreateEntry(T value);

	RegistryEntryLookup<T> createMutableEntryLookup();
}
