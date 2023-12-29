package com.griffins.fours_movement.mixin;

import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	private int timeSinceSneak = 0;

	@Inject(method = "tickMovement", at = @At("HEAD"))
	private void checkSneak(CallbackInfo info) {
		LivingEntity player = (LivingEntity) (Object) this;
		if (player.isSneaking()) { timeSinceSneak = 0; } else { timeSinceSneak++; }
	}

	@Inject(method = "computeFallDamage", at = @At("RETURN"), cancellable = true)
	private void reduceAfterSneaking(CallbackInfoReturnable info) {
		if (timeSinceSneak < 10) {
			int val = info.getReturnValueI() - 2;
			info.setReturnValue(val > 0 ? val : 0);
		}
	}
}
