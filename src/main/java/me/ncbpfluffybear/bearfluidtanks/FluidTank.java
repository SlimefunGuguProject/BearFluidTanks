package me.ncbpfluffybear.bearfluidtanks;

import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockUseHandler;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.github.thebusybiscuit.slimefun4.utils.holograms.SimpleHologram;
import me.mrCookieSlime.Slimefun.Lists.RecipeType;
import me.mrCookieSlime.Slimefun.Objects.Category;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.SlimefunItem;
import me.mrCookieSlime.Slimefun.Objects.handlers.ItemHandler;
import me.mrCookieSlime.Slimefun.api.BlockStorage;
import me.mrCookieSlime.Slimefun.api.SlimefunItemStack;
import me.mrCookieSlime.Slimefun.cscorelib2.item.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.List;

public class FluidTank extends SlimefunItem implements Listener {

    private static final List<Material> fluids = Arrays.asList(Material.WATER_BUCKET, Material.WATER_BUCKET,
        Material.EXPERIENCE_BOTTLE);

    public FluidTank(Category category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);

        registerBlockHandler(getId(), (p, b, stack, reason) -> {
            SimpleHologram.remove(b);
            return true;
        });

        addItemHandler(onPlace());
        addItemHandler(onUse());
        Bukkit.getPluginManager().registerEvents(this, BearFluidTanks.getInstance());
    }

    private ItemHandler onPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(BlockPlaceEvent e) {
                Block b = e.getBlockPlaced();

                BlockStorage.addBlockInfo(b, "type", "None");
                BlockStorage.addBlockInfo(b, "stored", "0");

                SimpleHologram.update(b, "&eType: None Stored: 0");
            }
        };
    }

    private ItemHandler onUse() {
        return (BlockUseHandler) e -> {
            // Prevent player from vanilla interacting with the block
            e.cancel();

            Player p = e.getPlayer();
            Block b = e.getClickedBlock().get();
            PlayerInventory inv = p.getInventory();
            ItemStack heldItem = inv.getItemInMainHand();
            String type = BlockStorage.getLocationInfo(b.getLocation(), "type");
            int stored = Integer.parseInt(BlockStorage.getLocationInfo(b.getLocation(), "stored"));

            if (heldItem.getType() == Material.AIR && !type.equals("None")) {
                p.getWorld().dropItemNaturally(b.getLocation().add(0, 1, 0), new ItemStack(Material.getMaterial(type), stored));
                BlockStorage.addBlockInfo(b, "type", "None");
                BlockStorage.addBlockInfo(b, "stored", "0");

                updateDisplays(b);
                return;
            }

            for (Material m : fluids) {
                if (SlimefunUtils.isItemSimilar(heldItem, new ItemStack(m), true, false)) {

                    inv.removeItem(new CustomItem(heldItem, 1));

                    BlockStorage.addBlockInfo(b, "type", m.getKey().getKey().toUpperCase());
                    BlockStorage.addBlockInfo(b, "stored", String.valueOf(stored + 1));

                    updateDisplays(b);

                    return;
                }
            }
        };
    }

    private void updateDisplays(Block b) {
        SimpleHologram.update(b,
            "&eType: " + BlockStorage.getLocationInfo(b.getLocation(), "type") +
                " Stored: " + BlockStorage.getLocationInfo(b.getLocation(), "stored"));
    }
}
