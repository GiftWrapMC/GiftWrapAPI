package io.github.giftwrapmc.gift_wrap_api.mixin;

import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.serialization.Lifecycle;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.registry.entry.RegistryEntry;

@Mixin(SimpleRegistry.class)
public class SimpleRegistryMixin<T>
{
	@Shadow
	private Map<T, RegistryEntry.Reference<T>> intrusiveValueToEntry;
	
	@Inject(method = "set", at = @At(value = "RETURN"))
	private void gift_wrap$set(int rawId, RegistryKey<T> key, T value, Lifecycle lifecycle, CallbackInfoReturnable<RegistryEntry.Reference<T>> info)
	{
		if (this.intrusiveValueToEntry == null)
		{
			@SuppressWarnings("unchecked")
			final RegistryEntryReferenceInvoker<T> ref = ((RegistryEntryReferenceInvoker<T>) info.getReturnValue());
			
			ref.callSetValue(value);
		}
	}
}
