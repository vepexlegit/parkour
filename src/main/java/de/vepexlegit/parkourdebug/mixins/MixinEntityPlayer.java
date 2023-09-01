package de.vepexlegit.parkourdebug.mixins;

import de.vepexlegit.parkourdebug.Parkour;
import de.vepexlegit.parkourdebug.commands.ParkourCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.ClientCommandHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBinding.class)
public class MixinEntityPlayer {
    @Inject(method = "isKeyDown", at = @At("RETURN"))
    private void startGame(final CallbackInfoReturnable<Boolean> cir) {
        ClientCommandHandler.instance.registerCommand(new ParkourCommand());
    }

    public Minecraft mc = Minecraft.getMinecraft();

    @Inject(method = "isKeyDown", at = @At("RETURN"))
    private void isKeyDown(final CallbackInfoReturnable<Boolean> cir) {
        if (Parkour.INSTANCE.isEnabled()) {
            if (mc.thePlayer.onGround && !mc.thePlayer.isSneaking() && mc.theWorld.getCollidingBoundingBoxes((Entity) mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, -0.5D, 0.0D).expand(-0.001D, 0.0D, -0.001D)).isEmpty()) {
                mc.thePlayer.jump();
            }
        }
    }
}
