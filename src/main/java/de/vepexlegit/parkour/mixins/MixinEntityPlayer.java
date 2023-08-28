package de.vepexlegit.parkour.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBinding.class)
public class MixinEntityPlayer {

    private static Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = "isKeyDown", at = @At("RETURN"))
    public void isKeyDown(final CallbackInfoReturnable<Boolean> cir) {
        if (mc.thePlayer.onGround && !mc.thePlayer.isSneaking() && mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.5D, 0.0D).expand(-0.001D, 0.0D, -0.001D)).isEmpty()) {
            mc.thePlayer.jump();
        }
    }
}
