package mod.moineau.packrepos.client.mixin;

import mod.moineau.packrepos.client.PackReposClient;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Arrays;
import java.util.stream.Stream;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/packs/repository/PackRepository;<init>([Lnet/minecraft/server/packs/repository/RepositorySource;)V"))
    private RepositorySource[] inject$init_clientPackSource(RepositorySource[] sources) {
        return Stream.concat(
                Arrays.stream(sources),
                Stream.concat(
                        Stream.of(PackReposClient.REQUIRED_DATA_PACK_REPOSITORY_SOURCE),
                        PackReposClient.getAdditionalResourcePackRepositorySources()
                )
        ).toArray(RepositorySource[]::new);
    }
}