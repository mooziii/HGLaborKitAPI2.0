package de.hglabor.plugins.kitapi.util.gui;

import de.hglabor.plugins.kitapi.util.gui.component.button.ButtonComponent;
import de.hglabor.plugins.kitapi.util.gui.component.picker.Char;
import de.hglabor.plugins.kitapi.util.gui.component.picker.EnumPickerComponent;
import de.hglabor.plugins.kitapi.util.gui.component.slider.SliderComponent;
import de.hglabor.utils.noriskutils.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ExampleGui {

    //Store the components to add a getter for its value;
    private SliderComponent<Float> exampleFloatSlider;
    private EnumPickerComponent<Char> exampleCharPicker;
    private EnumPickerComponent<Material> exampleMaterialPicker;

    public void createExampleGui(Plugin plugin, Player player) {
        GuiBuilder guiBuilder = new GuiBuilder(plugin, player);

        guiBuilder.withSlots(27); //Sets the inventory size (currently only chest inventories are supported not other inventory types)
        guiBuilder.withName(ChatColor.BLACK + "EXAMPLE GUI"); //Sets the inventory name
        guiBuilder.fillWith(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build()); //Fill the whole inventory with the given ItemStack

        //Creating our first component
        exampleFloatSlider = new SliderComponent<Float>(ChatColor.YELLOW + "Example Slider", "&7&oJust another float slider!", Material.CAKE, 2.5f, 0.0f, 10.0f);
        guiBuilder.withComponent(15, exampleFloatSlider); //Add the component to the gui

        guiBuilder.withItem(new ItemBuilder(Material.WATER_BUCKET).setName(ChatColor.BLUE + "Your first Gui!").build(), 4); //Add a normal ItemStack to the gui
        //Creating a custom event-button
        guiBuilder.withComponent(13, new ButtonComponent(
                ChatColor.GREEN + "Hello World!",
                "&fPrints &aHello World! &f in the chat",
                Material.COOKIE,
                clickAction -> {
                    //You can do whatever you want!
                    clickAction.getPlayer().sendMessage(Component.text("Hello World!").color(TextColor.color(0, 255, 0)));
                }
        ));

        //Creating a char picker
        exampleCharPicker = new EnumPickerComponent<Char>(
                plugin,
                player,
                guiBuilder,
                ChatColor.GOLD + "Example Letter",
                "&7&oJust another letter!",
                Material.NAME_TAG,
                Char.class,
                Char.A,
                Char.A
        );
        //Add the char picker to our gui
        guiBuilder.withComponent(1, exampleCharPicker);

        //Creating a big enum picker
        exampleMaterialPicker = new EnumPickerComponent<Material>(
                plugin,
                player,
                guiBuilder,
                ChatColor.LIGHT_PURPLE + "Your favorite Block",
                "&7&oPick your favorite block!",
                Material.DIAMOND_BLOCK, //The icon
                Material.class, //The enum class
                Material.GRASS, //The default value
                Material.GRASS
        );
        //And, add them to our gui
        guiBuilder.withComponent(7, exampleMaterialPicker);

        //Creating the gui and open it to the player
        player.openInventory(guiBuilder.build());
    }

    //Getter for our float slider
    public float getFloatSliderValue() {
        return exampleFloatSlider.getValue();
    }

}
