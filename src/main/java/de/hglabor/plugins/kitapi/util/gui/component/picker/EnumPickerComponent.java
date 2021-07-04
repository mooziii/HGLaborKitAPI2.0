package de.hglabor.plugins.kitapi.util.gui.component.picker;

import de.hglabor.plugins.kitapi.util.ReflectionUtils;
import de.hglabor.plugins.kitapi.util.gui.GuiBuilder;
import de.hglabor.plugins.kitapi.util.gui.component.button.ButtonComponent;
import de.hglabor.utils.noriskutils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class EnumPickerComponent<E extends Enum<?>> extends ButtonComponent {

    private final Player player;
    private final Plugin plugin;
    private final GuiBuilder parentScreen;
    private final Class<? extends Enum<?>> enumClass;
    private final E defaultValue;
    private final String name;
    private E currentValue;

    public EnumPickerComponent(Plugin plugin, Player player, GuiBuilder parent, String name, String description, Material icon, Class<? extends Enum<?>> enumClass, E defaultValue, E currentValue) {
        super(name, description+"##&7##&7Current: &e" + currentValue.name() + "##&7Default: &e" + defaultValue.name() + "##&a##&7&oClick to open selector.", icon, null);
        this.currentValue = currentValue;
        this.defaultValue = defaultValue;
        this.enumClass = enumClass;
        this.plugin = plugin;
        this.player = player;
        this.parentScreen = parent;
        this.name = name;
    }

    public EnumPickerComponent(Plugin plugin, Player player, GuiBuilder parent, String name, String description, ItemBuilder icon, Class<? extends Enum<?>> enumClass, E defaultValue, E currentValue) {
        super(name, description+"##&7##&7Current: &e" + currentValue.name() + "##&7Default: &e" + defaultValue.name() + "##&a##&7&oClick to open selector.", icon, null);
        this.currentValue = currentValue;
        this.defaultValue = defaultValue;
        this.enumClass = enumClass;
        this.plugin = plugin;
        this.player = player;
        this.parentScreen = parent;
        this.name = name;
    }

    public EnumPickerComponent(Plugin plugin, Player player, GuiBuilder parent, String name, String description, ItemStack icon, Class<? extends Enum<?>> enumClass, E defaultValue, E currentValue) {
        super(name, description+"##&7##&7Current: &e" + currentValue.name() + "##&7Default: &e" + defaultValue.name() + "##&a##&7&oClick to open selector.", icon, null);
        this.currentValue = currentValue;
        this.defaultValue = defaultValue;
        this.enumClass = enumClass;
        this.plugin = plugin;
        this.player = player;
        this.parentScreen = parent;
        this.name = name;
    }

    @Override
    public void press(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.0f);
        drawEnumSelector(0);
    }

    private void drawEnumSelector(int page) {
        Enum<?>[] entries = enumClass.getEnumConstants();
        int pages = entries.length <= 54 ? 0 : (entries.length - 2) / 52;
        if (page > pages) {
            return;
        }
        GuiBuilder enumSettingScreen = new GuiBuilder(plugin, player).withName(name + (pages == 0 ? " Selector" : " Selector (" + page + "/" + pages + ")"));
        int pageSize = pages == 0 ? entries.length : page == 0 ? 53 : page == pages ? entries.length % 52 - 1 : 52;
        enumSettingScreen.withSlots(pageSize);
        if (page > 0) {
            enumSettingScreen.withComponent(0, new ButtonComponent(
                    "Page Up",
                    "Scroll one page up",
                    Material.ARROW,
                    it -> drawEnumSelector(page - 1)
            ));
        }
        if (page < pages) {
            enumSettingScreen.withComponent(53, new ButtonComponent(
                    "Page Down",
                    "Scroll one page down",
                    Material.ARROW,
                    it -> drawEnumSelector(page + 1)
            ));
        }
        for (int i = page == 0 ? 0 : 1, e = page == 0 ? 0 : page * 52 + 1; i < (page == 0 ? pageSize : pageSize + 1); ++i, ++e) {
            Enum<?> entry = entries[e];
            try {
                enumSettingScreen.withComponent(i, new PickableComponent<>(
                        ((entry instanceof Material) ? Material.valueOf(entry.name()) : currentValue.equals(entry) ? Material.FILLED_MAP : Material.PAPER),
                        currentValue.getClass().getField("currentValue"),
                        entry,
                        parentScreen
                ));
            } catch (NoSuchFieldException | RuntimeException exception) {
                exception.printStackTrace();
            }
        }

        player.openInventory(enumSettingScreen.build());
    }

}
