package io.github.giftwrapmc.gift_wrap_api.mixin;

import io.github.giftwrapmc.gift_wrap_api.extensions.NeoRegistries;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

	@Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourcePackManager;scanPacks()V"))
	private void applyRegistrations(RunArgs args, CallbackInfo ci) {
		NeoRegistries.acceptQueuedContent();
		NeoRegistries.fullyFreezeRegistries();
	}
}
