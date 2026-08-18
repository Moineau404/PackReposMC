package mod.moineau.packrepos.mixin;

import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.repository.Pack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Function;

@Mixin(Pack.Position.class)
@Unique
public enum PackPositionMixin {
    PACKED_MIDDLE;

    @Inject(method = "insert", at = @At(value = "HEAD"), cancellable = true)
    public <T> void inject$insert(List<T> list, T value, Function<T, PackSelectionConfig> converter, boolean reverse, CallbackInfoReturnable<Integer> cir) {
        if (this == PACKED_MIDDLE) {
            int index;
            for (index = 0; index < list.size(); index++) {
                PackSelectionConfig pack = converter.apply(list.get(index));
                if (!pack.fixedPosition() || pack.defaultPosition() == Pack.Position.BOTTOM) {
                    index = index + 1;
                    break;
                }
                if (pack.defaultPosition() == Pack.Position.TOP) {
                    break;
                }
            }

            list.add(index, value);
            cir.setReturnValue(index);
        }
    }
}
