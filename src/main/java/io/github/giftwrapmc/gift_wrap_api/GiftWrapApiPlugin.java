package io.github.giftwrapmc.gift_wrap_api;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import org.objectweb.asm.tree.MethodInsnNode;
import org.quiltmc.loader.api.LoaderValue;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.api.plugin.QuiltLoaderPlugin;
import org.quiltmc.loader.api.plugin.QuiltPluginContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GiftWrapApiPlugin implements QuiltLoaderPlugin
{
	public static final String MOD_ID = "gift_wrap_api";
	
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	
	@Override
	public void load(QuiltPluginContext context, Map<String, LoaderValue> previousData)
	{
		QuiltLoader.getObjectShare().whenAvailable("gift_wrap:method_insn_patches", (key, value) ->
		{
			@SuppressWarnings("unchecked")
			final Consumer<BiPredicate<String, MethodInsnNode>> patches = (Consumer<BiPredicate<String, MethodInsnNode>>) value;
			patches.accept(new StaticMethodPatcher());
		});
		
		QuiltLoader.getObjectShare().put("gift_wrap:adapter", "gift_wrap_api");
	}
	
	@Override
	public void unload(Map<String, LoaderValue> data)
	{
		
	}
}
