package mod.moineau.packrepos;

import mod.moineau.packrepos.integration.PackRepositoryProvider;
import mod.moineau.packrepos.packs.RequiredFolderRepositorySource;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.validation.DirectoryValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.stream.Stream;

public final class PackRepos implements ModInitializer {
	public static final String MOD_ID = "packrepos";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Pack.Position MIDDLE_PACK_POSITION = Pack.Position.valueOf("PACKED_MIDDLE");
	public static final Path DATA_PACK_DIRECTORY = FabricLoader.getInstance().getGameDir().resolve("datapacks");
	public static final Path REQUIRED_DATA_PACK_DIRECTORY = FabricLoader.getInstance().getConfigDir().resolve(MOD_ID).resolve("required_datapacks");
	public static final DirectoryValidator DIRECTORY_VALIDATOR = LevelStorageSource.parseValidator(FabricLoader.getInstance().getGameDir().resolve("allowed_symlinks.txt"));
	public static final FolderRepositorySource DATA_PACK_REPOSITORY_SOURCE = new FolderRepositorySource(
			DATA_PACK_DIRECTORY, PackType.SERVER_DATA, PackSource.DEFAULT, DIRECTORY_VALIDATOR
	);
	public static final FolderRepositorySource REQUIRED_DATA_PACK_REPOSITORY_SOURCE = new RequiredFolderRepositorySource(
			REQUIRED_DATA_PACK_DIRECTORY, PackType.SERVER_DATA, PackSource.DEFAULT, DIRECTORY_VALIDATOR
	);

	public static Stream<RepositorySource> getAdditionalDataPackRepositorySources() {
		return FabricLoader.getInstance().getEntrypoints("packrepos", PackRepositoryProvider.class).stream().flatMap(PackRepositoryProvider::provideDataPackRepositorySources);
	}

	@Override
	public void onInitialize() {
		DATA_PACK_DIRECTORY.toFile().mkdirs();
		REQUIRED_DATA_PACK_DIRECTORY.toFile().mkdirs();
	}
}
