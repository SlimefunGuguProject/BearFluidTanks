package me.ncbpfluffybear.bearfluidtanks;

import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

public class BFTItems {

    public static final Category bft_category = new Category(new NamespacedKey(
        BearFluidTanks.getInstance(), "bft_category"),
        new CustomItem(Material.HOPPER, "&6Bear Fluid Tanks"));

    public static final SlimefunItemStack FLUID_TANK = new SlimefunItemStack("FLUID_TANK",
        Material.HOPPER,
        "&6&lFluid Tank",
        "",
        "&7Stores Liquids"
    );
}
