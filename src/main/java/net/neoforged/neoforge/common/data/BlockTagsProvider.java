package net.neoforged.neoforge.common.data;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.data.DataOutput;
import net.minecraft.data.server.tag.ValueLookupTagProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

public abstract class BlockTagsProvider extends ValueLookupTagProvider<Block>
{
	public BlockTagsProvider(DataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper)
	{
		super(output,
			RegistryKeys.BLOCK,
			lookupProvider,
			block -> block.getRegistryEntry().registryKey()//,
		//	modId,
		//	existingFileHelper
		);
	}
}
