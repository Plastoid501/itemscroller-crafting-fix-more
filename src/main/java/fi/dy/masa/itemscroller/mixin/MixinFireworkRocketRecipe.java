package fi.dy.masa.itemscroller.mixin;

import fi.dy.masa.itemscroller.config.Configs;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.FireworkRocketRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkRocketRecipe.class)
public class MixinFireworkRocketRecipe {
    @Inject(method = "craft(Lnet/minecraft/inventory/CraftingInventory;)Lnet/minecraft/item/ItemStack;", at = @At("RETURN"), cancellable = true)
    private void modifyCraft(CraftingInventory craftingInventory, CallbackInfoReturnable<ItemStack> cir) {
        if (!Configs.Generic.ONLY_CRAFT_FIREWORK_ROCKET_LVL3.getBooleanValue()) {
            return;
        }

        NbtCompound tag = cir.getReturnValue().getSubNbt("Fireworks");
        if (tag != null) {
            if (tag.contains("Flight", 99)) {
                if (tag.getByte("Flight") != 3) {
                    cir.setReturnValue(ItemStack.EMPTY);
                }
            }
        }
    }
}
