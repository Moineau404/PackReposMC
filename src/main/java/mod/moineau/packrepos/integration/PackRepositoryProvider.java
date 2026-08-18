package mod.moineau.packrepos.integration;

import net.minecraft.server.packs.repository.RepositorySource;

import java.util.stream.Stream;

public interface PackRepositoryProvider {
    default Stream<RepositorySource> provideResourcePackRepositorySources() {
        return Stream.empty();
    }

    default Stream<RepositorySource> provideDataPackRepositorySources() {
        return Stream.empty();
    }
}
