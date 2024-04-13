package net.neoforged.fml;

import org.quiltmc.loader.api.QuiltLoader;

public class ModList
{
	private static final ModList INSTANCE = new ModList();
	
	public static ModList get()
	{
		return INSTANCE;
	}
	
	public boolean isLoaded(String modId)
	{
		return QuiltLoader.isModLoaded(modId);
	}
}
