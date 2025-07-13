package tritastic.mixin.client;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Colors;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tritastic.ModComponents;
import tritastic.items.Echofang;

@Mixin(DrawContext.class)
public abstract class ItemRenderingMixin {
    @Shadow public abstract void fill(RenderPipeline pipeline, int x1, int y1, int x2, int y2, int z);

    @Inject(method = "drawItemBar", at = @At("TAIL"))
    private void awd(ItemStack stack, int x, int y, CallbackInfo ci) {
        if (stack.contains(ModComponents.SCULK_CHARGE)) {
            var component = stack.get(ModComponents.SCULK_CHARGE);
            int i = x + 2;
            int j = y + 13;
            this.fill(RenderPipelines.GUI, i, j, i + MathHelper.floor(component.floatValue() / Echofang.MAX_CHARGE * 13), j + 2, ColorHelper.fullAlpha(Colors.CYAN));
        }
    }
}