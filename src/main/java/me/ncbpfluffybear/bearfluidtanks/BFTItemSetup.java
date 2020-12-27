package me.ncbpfluffybear.bearfluidtanks;

import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public final class BFTItemSetup {

    private BFTItemSetup() {}

    public static void setup(@Nonnull BearFluidTanks plugin) {
        new FluidTank(BFTItems.bft_category, BFTItems.FLUID_TANK, RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[] {
            new ItemStack(Material.GLASS), new ItemStack(Material.GLASS), new ItemStack(Material.GLASS),
            new ItemStack(Material.GLASS), new ItemStack(Material.HOPPER), new ItemStack(Material.GLASS),
            new ItemStack(Material.IRON_BLOCK), new ItemStack(Material.IRON_BLOCK), new ItemStack(Material.IRON_BLOCK)
        }).register(plugin);
    }

}
