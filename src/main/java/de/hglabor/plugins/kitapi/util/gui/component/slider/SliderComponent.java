package de.hglabor.plugins.kitapi.util.gui.component.slider;

import de.hglabor.plugins.kitapi.util.gui.component.ValueComponent;
import de.hglabor.plugins.kitapi.util.gui.component.button.ButtonComponent;
import de.hglabor.utils.noriskutils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SliderComponent<T> extends ButtonComponent implements ValueComponent {

    private final String description;
    private final T defaultValue;
    private final T minValue;
    private final T maxValue;
    private T currentValue;
    private final SliderComponent<T> instance = this;

    public SliderComponent(String name, String description, Material icon, T defaultValue, T minValue, T maxValue) {
        super(name, description+"##&7##&7Current: &e" + defaultValue + "##&7Min: &e" + minValue + "##&7Max: &e" + maxValue + "##&7Default: &e" + defaultValue + "##&a##&7Left click: &e+" + ((defaultValue instanceof Integer) ? "1" : "0.5") + "##&7Right click: &e-" + ((defaultValue instanceof Integer) ? "1" : "0.5") + "##&7Middle click: &ereset", icon, null);
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = defaultValue;
        this.description = description;
    }

    public SliderComponent(String name, String description, ItemBuilder icon, T defaultValue, T minValue, T maxValue) {
        super(name, description+"##&7##&7Current: &e" + defaultValue + "##&7Min: &e" + minValue + "##&7Max: &e" + maxValue + "##&7Default: &e" + defaultValue + "##&a##&7Left click: &e+" + ((defaultValue instanceof Integer) ? "1" : "0.5") + "##&7Right click: &e-" + ((defaultValue instanceof Integer) ? "1" : "0.5") + "##&7Middle click: &ereset", icon, null);
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = defaultValue;
        this.description = description;
    }

    public SliderComponent(String name, String description, ItemStack icon, T defaultValue, T minValue, T maxValue) {
        super(name, description+"##&7##&7Current: &e" + defaultValue + "##&7Min: &e" + minValue + "##&7Max: &e" + maxValue + "##&7Default: &e" + defaultValue + "##&a##&7Left click: &e+" + ((defaultValue instanceof Integer) ? "1" : "0.5") + "##&7Right click: &e-" + ((defaultValue instanceof Integer) ? "1" : "0.5") + "##&7Middle click: &ereset", icon, null);
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = defaultValue;
        this.description = description;
    }

    public SliderComponent(String name, String description, Material icon, T defaultValue, T minValue, T maxValue, T currentValue) {
        super(name, description+"##&7##&7Current: &e" + currentValue + "##&7Min: &e" + minValue + "##&7Max: &e" + maxValue + "##&7Default: &e" + defaultValue + "##&a##&7Left click: &e+" + ((defaultValue instanceof Integer) ? "1" : "0.5") + "##&7Right click: &e-" + ((defaultValue instanceof Integer) ? "1" : "0.5") + "##&7Middle click: &ereset", icon, null);
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = currentValue;
        this.description = description;
    }

    public SliderComponent(String name, String description, ItemBuilder icon, T defaultValue, T minValue, T maxValue, T currentValue) {
        super(name, description+"##&7##&7Current: &e" + currentValue + "##&7Min: &e" + minValue + "##&7Max: &e" + maxValue + "##&7Default: &e" + defaultValue + "##&a##&7Left click: &e+" + ((defaultValue instanceof Integer) ? "1" : "0.5") + "##&7Right click: &e-" + ((defaultValue instanceof Integer) ? "1" : "0.5") + "##&7Middle click: &ereset", icon, null);
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = currentValue;
        this.description = description;
    }

    public SliderComponent(String name, String description, ItemStack icon, T defaultValue, T minValue, T maxValue, T currentValue) {
        super(name, description+"##&7##&7Current: &e" + currentValue + "##&7Min: &e" + minValue + "##&7Max: &e" + maxValue + "##&7Default: &e" + defaultValue + "##&a##&7Left click: &e+" + ((defaultValue instanceof Integer) ? "1" : "0.5") + "##&7Right click: &e-" + ((defaultValue instanceof Integer) ? "1" : "0.5") + "##&7Middle click: &ereset", icon, null);
        this.defaultValue = defaultValue;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = currentValue;
        this.description = description;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void press(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1,1);
        ClickType clickType = event.getClick();
        if(clickType == ClickType.MIDDLE) {
            setValue(defaultValue);
        }
        if(clickType.isRightClick()) {
            if(defaultValue instanceof Integer) {
                Integer current = (Integer) getValue();
                if(current-1 >= (int) minValue) {
                    current-=1;
                    setValue((T) current);
                }
            } else if(defaultValue instanceof Float) {
                Float current = (Float) getValue();
                if(current-0.5F >= (float) minValue) {
                    current -= 0.5F;
                    setValue((T) current);
                }
            } else if(defaultValue instanceof Double) {
                Double current = (Double) getValue();
                if(current-0.5D >= (double) minValue) {
                    current -= 0.5D;
                    setValue((T) current);
                }
            }
        }
        if(clickType.isLeftClick()) {
            if(defaultValue instanceof Integer) {
                Integer current = (Integer) getValue();
                if(current+1 <= (int) maxValue) {
                    current+=1;
                    setValue((T) current);
                }
            } else if(defaultValue instanceof Float) {
                Float current = (Float) getValue();
                if(current+0.5F <= (float) maxValue) {
                    current += 0.5F;
                    setValue((T) current);
                }
            } else if(defaultValue instanceof Double) {
                Double current = (Double) getValue();
                if(current+0.5D <= (double) maxValue) {
                    current += 0.5D;
                    setValue((T) current);
                }
            }
        }
        ItemStack itemStack = event.getCurrentItem();
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Arrays.asList((description+"##&7##&7Current: &e" + getValue() + "##&7Min: &e" + minValue + "##&7Max: &e" + maxValue + "##&7Default: &e" + defaultValue + "##&a##&7Left click: &e+" + ((defaultValue instanceof Integer) ? "1" : "0.5") + "##&7Right click: &e-" + ((defaultValue instanceof Integer) ? "1" : "0.5") + "##&7Middle click: &ereset").replace("&", "§").split("##")));
        itemStack.setItemMeta(meta);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <K> void setValue(K value) {
        this.currentValue = (T) value;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <K> K getValue() {
        return (K) currentValue;
    }
}
