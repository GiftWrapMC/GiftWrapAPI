package io.github.giftwrapmc.gift_wrap_api.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.github.giftwrapmc.gift_wrap_api.registry.NeoRegistries;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Registries.class)
public class RegistriesMixin {

	@WrapOperation(method = "bootstrap", at = @At(value = "INVOKE", target = "Lnet/minecraft/registry/Registries;freezeRegistries()V"))
	private static void partiallyFreezeRegistriesInsteadOfFullyFreezingThem(Operation<Void> original) {
		NeoRegistries.partiallyFreezeRegistries();
	}
}
