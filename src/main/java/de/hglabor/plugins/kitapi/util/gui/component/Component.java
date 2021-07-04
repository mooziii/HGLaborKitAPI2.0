package de.hglabor.plugins.kitapi.util.gui.component;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public interface Component {

    public ItemStack getItemStackResult();

    public void doClick(InventoryClickEvent event);
}
