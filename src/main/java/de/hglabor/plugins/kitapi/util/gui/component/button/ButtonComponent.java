package de.hglabor.plugins.kitapi.util.gui.component.button;

import de.hglabor.plugins.kitapi.util.gui.component.Component;
import de.hglabor.utils.noriskutils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import java.util.function.Consumer;

public class ButtonComponent implements Component {

    private Consumer<ButtonClickAction> onPress;
    private ItemStack itemStackResult;

    public void press(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1,1);
        onPress.accept(new ButtonClickAction(player, this, event));
    }

    public ButtonComponent(String name, String description, Material icon, Consumer<ButtonClickAction> onPress) {
        this.onPress = onPress;
        this.itemStackResult = new ItemBuilder(icon).setName(name.replace("&", "§")).setDescription(description.replace("&", "§").split("##")).build();
    }

    public ButtonComponent(String name, String description, ItemStack icon, Consumer<ButtonClickAction> onPress) {
        this.onPress = onPress;
        this.itemStackResult = new ItemBuilder(icon).setName(name.replace("&", "§")).setDescription(description.replace("&", "§").split("##")).build();
    }

    public ButtonComponent(String name, String description, ItemBuilder icon, Consumer<ButtonClickAction> onPress) {
        this.onPress = onPress;
        this.itemStackResult = new ItemBuilder(icon.build()).setName(name.replace("&", "§")).setDescription(description.replace("&", "§").split("##")).build();
    }

    @Override
    public ItemStack getItemStackResult() {
        return itemStackResult;
    }

    @Override
    public void doClick(InventoryClickEvent event) {
        press(event);
    }
}
