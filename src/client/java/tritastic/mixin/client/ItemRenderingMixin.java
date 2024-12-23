package tritastic.mixin.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Colors;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tritastic.ModComponents;
import tritastic.ModItems;
import tritastic.items.Echofang;

import java.util.Objects;

@Mixin(DrawContext.class)
public abstract class ItemRenderingMixin {
    @Shadow public abstract void fill(RenderLayer layer, int x1, int y1, int x2, int y2, int color, int black);

    @Shadow @Final private MatrixStack matrices;

    @Inject(method = "drawItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;IIII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;getItemModelManager()Lnet/minecraft/client/item/ItemModelManager;"))
    public void renderSoulChargeUnderlay(LivingEntity entity, World world, ItemStack stack, int x, int y, int seed, int z, CallbackInfo ci) {
        if (stack.isOf(ModItems.ECHOFANG)) {
            var charge = (float) Objects.requireNonNullElse(stack.get(ModComponents.SCULK_CHARGE), 0) / Echofang.MAX_CHARGE;

            // I don't why I need to use 50 units, but 1 or 10 or some smaller number doesn't work and the underlay renders on top
            this.matrices.translate(0, 0, -50);
            this.fill(RenderLayer.getGui(), x, y + 16, x + 16, y + 16 - Math.round(charge * 16), 200, ColorHelper.withAlpha(60, Colors.CYAN));
            this.matrices.translate(0, 0, 50);
        }
    }
}