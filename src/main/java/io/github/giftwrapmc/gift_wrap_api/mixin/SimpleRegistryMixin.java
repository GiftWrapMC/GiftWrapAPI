package io.github.giftwrapmc.gift_wrap_api.mixin;

import java.util.Map;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import com.mojang.serialization.Lifecycle;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import io.github.giftwrapmc.gift_wrap_api.extensions.RegistryFreezeExtensions;

@Mixin(SimpleRegistry.class)
public abstract class SimpleRegistryMixin<T> implements RegistryFreezeExtensions<T>
{
	@Unique
	private boolean canBeAccessed = false;

	@Shadow
	private Map<T, RegistryEntry.Reference<T>> intrusiveValueToEntry;

	@Shadow
	private boolean frozen;

	@Shadow
	public abstract RegistryEntry.Reference<T> set(int rawId, RegistryKey<T> key, T value, Lifecycle lifecycle);

	@Shadow
	abstract RegistryEntry.Reference<T> getOrCreateEntry(RegistryKey<T> key);

	@Shadow
	public abstract RegistryEntry.Reference<T> createEntry(T value);

	@Shadow
	private int nextId;

	@Override
	public void statesAsUnfrozen() {
		this.frozen = false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Registry<T> partiallyFreeze() {
		this.frozen = true;
		return (Registry<T>) this;
	}

	@Override
	public RegistryEntry.Reference<T> neoAdd(RegistryKey<T> key, T entry, Lifecycle lifecycle) {
		return this.neoSet(this.nextId, key, entry, lifecycle);
	}

	@Override
	public RegistryEntry.Reference<T> neoSet(int rawId, RegistryKey<T> key, T value, Lifecycle lifecycle) {
		this.switchAccess();
		RegistryEntry.Reference<T> ref = this.set(rawId, key, value, lifecycle);
		this.switchAccess();
		return ref;
	}

	@Override
	public RegistryEntry.Reference<T> neoGetOrCreateEntry(RegistryKey<T> key) {
		this.switchAccess();
		RegistryEntry.Reference<T> ref = this.getOrCreateEntry(key);
		this.switchAccess();
		return ref;
	}

	@Override
	public RegistryEntry.Reference<T> neoCreateEntry(T value) {
		this.switchAccess();
		RegistryEntry.Reference<T> ref = this.createEntry(value);
		this.switchAccess();
		return ref;
	}

	@Override
	public RegistryEntryLookup<T> createMutableEntryLookup() {
		this.switchAccess();
		RegistryEntryLookup<T> lookup = this.createMutableEntryLookup();
		this.switchAccess();
		return lookup;
	}

	@Unique
	private void switchAccess() {
		this.canBeAccessed = !this.canBeAccessed;
	}
}
