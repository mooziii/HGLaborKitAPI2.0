package de.hglabor.plugins.kitapi.util.gui.component.picker;

import de.hglabor.plugins.kitapi.util.ReflectionUtils;
import de.hglabor.plugins.kitapi.util.gui.GuiBuilder;
import de.hglabor.plugins.kitapi.util.gui.component.button.ButtonComponent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.lang.reflect.Field;

public class PickableComponent<I> extends ButtonComponent {

    private Field fieldToChange;
    private final I changeTo;
    private final GuiBuilder parent;

    public PickableComponent(Material icon, Field fieldToChange, I changeTo, GuiBuilder parent) {
        super(changeTo.toString(), "&7&oClick to select &f&o" + changeTo, icon, null);
        this.changeTo = changeTo;
        this.fieldToChange = fieldToChange;
        this.parent = parent;
    }

    @Override
    public void press(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
        ReflectionUtils.set(fieldToChange, fieldToChange.getClass(), changeTo);
        player.openInventory(parent.build());
    }
}
