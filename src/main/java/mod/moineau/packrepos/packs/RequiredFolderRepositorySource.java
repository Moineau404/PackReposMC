package mod.moineau.packrepos.packs;

import mod.moineau.packrepos.PackRepos;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.level.validation.DirectoryValidator;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class RequiredFolderRepositorySource extends FolderRepositorySource {
    private static final PackSelectionConfig SELECTION_CONFIG = new PackSelectionConfig(true, PackRepos.MIDDLE_PACK_POSITION, false);

    public RequiredFolderRepositorySource(Path folder, PackType packType, PackSource packSource, DirectoryValidator validator) {
        super(folder, packType, packSource, validator);
    }

    @Override
    public void loadPacks(final Consumer<Pack> result) {
        List<Pack> packs = new ArrayList<>();
        super.loadPacks(packs::add);
        for (Pack pack : packs) {
            Pack modifiedPack = Pack.readMetaAndCreate(pack.location(), pack.resources, this.packType, SELECTION_CONFIG);
            if (modifiedPack != null) {
                result.accept(modifiedPack);
            }
        }
    }
}
