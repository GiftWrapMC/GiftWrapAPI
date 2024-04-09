package net.neoforged.fml;

import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.fml.config.ModConfig;

public class ModLoadingContext
{
	private static final ThreadLocal<ModLoadingContext> context = ThreadLocal.withInitial(ModLoadingContext::new);
	
	public static ModLoadingContext get()
	{
		return context.get();
	}
	
	public void registerConfig(ModConfig.Type type, IConfigSpec<?> spec)
	{
		
	}
}
