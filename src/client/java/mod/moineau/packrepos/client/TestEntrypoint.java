package mod.moineau.packrepos.client;

import mod.moineau.packrepos.integration.PackRepositoryProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.validation.DirectoryValidator;

import java.nio.file.Path;
import java.util.stream.Stream;

public class TestEntrypoint implements PackRepositoryProvider {
    public static final Path DIRECTORY = FabricLoader.getInstance().getGameDir().resolve("testpacks");
    public static final DirectoryValidator DIRECTORY_VALIDATOR = LevelStorageSource.parseValidator(FabricLoader.getInstance().getGameDir().resolve("allowed_symlinks.txt"));
    public static final FolderRepositorySource REPOSITORY_SOURCE = new FolderRepositorySource(
            DIRECTORY, PackType.CLIENT_RESOURCES, PackSource.DEFAULT, DIRECTORY_VALIDATOR
    );
    @Override
    public Stream<RepositorySource> provideResourcePackRepositorySources() {
        return Stream.of(REPOSITORY_SOURCE);
    }
}
