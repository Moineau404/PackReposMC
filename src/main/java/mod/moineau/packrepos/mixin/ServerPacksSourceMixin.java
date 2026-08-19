package mod.moineau.packrepos.mixin;

import mod.moineau.packrepos.PackRepos;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Arrays;
import java.util.stream.Stream;

@Mixin(ServerPacksSource.class)
public class ServerPacksSourceMixin {
    @ModifyArg(method = "createPackRepository(Ljava/nio/file/Path;Lnet/minecraft/world/level/validation/DirectoryValidator;)Lnet/minecraft/server/packs/repository/PackRepository;", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>([Lnet/minecraft/server/packs/repository/RepositorySource;)V"))
    private static RepositorySource[] inject$init_serverPackSource(RepositorySource[] sources) {
        return Stream.concat(Arrays.stream(sources), PackRepos.getDataPackRepositorySources()).toArray(RepositorySource[]::new);
    }
}
