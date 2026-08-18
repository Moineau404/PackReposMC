package mod.moineau.packrepos.client;

import mod.moineau.packrepos.PackRepos;
import mod.moineau.packrepos.integration.PackRepositoryProvider;
import mod.moineau.packrepos.packs.RequiredFolderRepositorySource;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;

import java.nio.file.Path;
import java.util.stream.Stream;

public final class PackReposClient implements ClientModInitializer {
	public static final Path REQUIRED_RESOURCE_PACK_DIRECTORY = FabricLoader.getInstance().getConfigDir().resolve(PackRepos.MOD_ID).resolve("required_resourcepacks");
	public static final FolderRepositorySource REQUIRED_DATA_PACK_REPOSITORY_SOURCE = new RequiredFolderRepositorySource(
			REQUIRED_RESOURCE_PACK_DIRECTORY, PackType.CLIENT_RESOURCES, PackSource.DEFAULT, PackRepos.DIRECTORY_VALIDATOR
	);

	public static Stream<RepositorySource> getAdditionalResourcePackRepositorySources() {
		return FabricLoader.getInstance().getEntrypoints("packrepos", PackRepositoryProvider.class).stream().flatMap(PackRepositoryProvider::provideResourcePackRepositorySources);
	}

	@Override
	public void onInitializeClient() {
		REQUIRED_RESOURCE_PACK_DIRECTORY.toFile().mkdirs();
	}
}