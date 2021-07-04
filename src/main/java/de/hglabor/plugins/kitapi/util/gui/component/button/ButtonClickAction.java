package de.hglabor.plugins.kitapi.util.gui.component.button;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ButtonClickAction {

    private final InventoryClickEvent bukkitEvent;
    private final Player player;
    private final ButtonComponent button;

    public ButtonClickAction(Player player, ButtonComponent button, InventoryClickEvent bukkitEvent) {
        this.player = player;
        this.button = button;
        this.bukkitEvent = bukkitEvent;
    }

    public InventoryClickEvent getBukkitEvent() {
        return bukkitEvent;
    }

    public Player getPlayer() {
        return player;
    }

    public ButtonComponent getButton() {
        return button;
    }

}
