package io.github.giftwrapmc.gift_wrap_api.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.registry.entry.RegistryEntry;

@Mixin(RegistryEntry.Reference.class)
public interface RegistryEntryReferenceInvoker<T>
{
	@Invoker
	void callSetValue(T value);
}
