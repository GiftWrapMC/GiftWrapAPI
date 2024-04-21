package io.github.giftwrapmc.gift_wrap_api.mixin;

import io.github.giftwrapmc.gift_wrap_api.extensions.NeoRegistries;
import net.minecraft.server.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public class MainMixin {

	@Inject(method = "main", at = @At(value = "INVOKE", target = "Ljava/nio/file/Paths;get(Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path;", shift = At.Shift.AFTER))
	private static void applyRegistrations(String[] args, CallbackInfo ci) {
		NeoRegistries.acceptQueuedContent();
		NeoRegistries.fullyFreezeRegistries();
	}
}
