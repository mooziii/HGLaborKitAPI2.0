package de.hglabor.plugins.kitapi;

import com.google.common.collect.ImmutableMap;
import de.hglabor.plugins.kitapi.config.KitApiConfig;
import de.hglabor.plugins.kitapi.kit.AbstractKit;
import de.hglabor.plugins.kitapi.kit.config.Cooldown;
import de.hglabor.plugins.kitapi.kit.kits.*;
import de.hglabor.plugins.kitapi.kit.kits.analyst.AnalystKit;
import de.hglabor.plugins.kitapi.kit.kits.endermage.EndermageKit;
import de.hglabor.plugins.kitapi.kit.kits.grappler.GrapplerKit;
import de.hglabor.plugins.kitapi.kit.selector.KitSelector;
import de.hglabor.plugins.kitapi.player.KitPlayer;
import de.hglabor.plugins.kitapi.supplier.KitItemSupplier;
import de.hglabor.plugins.kitapi.supplier.KitItemSupplierImpl;
import de.hglabor.plugins.kitapi.supplier.KitPlayerSupplier;
import de.hglabor.utils.localization.Localization;
import de.hglabor.utils.noriskutils.ChatUtils;
import de.hglabor.utils.noriskutils.feast.Feast;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public final class KitApi {
    private final static KitApi INSTANCE = new KitApi();
    private final List<AbstractKit> kits;
    private final List<Locale> supportedLanguages;
    private KitSelector kitSelector;
    private KitPlayerSupplier playerSupplier;
    private KitItemSupplier itemSupplier;
    private JavaPlugin plugin;
    private Feast feast;

    private KitApi() {
        this.kits = new ArrayList<>();
        this.itemSupplier = KitItemSupplierImpl.INSTANCE;
        this.supportedLanguages = Arrays.asList(Locale.ENGLISH, Locale.GERMAN);
    }

    public static KitApi getInstance() {
        return INSTANCE;
    }

    public void checkUsesForCooldown(KitPlayer kitPlayer, AbstractKit kit, int maxUses) {
        String key = kit.getName() + "kitUses";
        if (kitPlayer.getKitAttribute(key) == null) {
            kitPlayer.putKitAttribute(key, new AtomicInteger());
        }
        AtomicInteger kitUses = kitPlayer.getKitAttribute(key);
        if (kitUses.getAndIncrement() > maxUses) {
            kitPlayer.activateKitCooldown(kit);
            kitUses.set(0);
        }
    }

    public String cooldownKey(AbstractKit kit) {
        return kit.getName() + "cooldown";
    }

    public void checkUsesForCooldown(Player player, AbstractKit kit, int maxUses) {
        checkUsesForCooldown(getPlayer(player), kit, maxUses);
    }

    public List<Locale> getSupportedLanguages() {
        return supportedLanguages;
    }

    public List<AbstractKit> emptyKitList() {
        int kitAmount = KitApiConfig.getInstance().getKitAmount();
        List<AbstractKit> emptyKitList = new ArrayList<>(kitAmount);
        for (int i = 0; i < kitAmount; i++) {
            emptyKitList.add(NoneKit.INSTANCE);
        }
        return emptyKitList;
    }

    public void enableKit(AbstractKit kit, boolean isEnabled) {
        kit.setEnabled(isEnabled);
        if (isEnabled) {
            if (kit instanceof Listener) {
                Bukkit.getPluginManager().registerEvents((Listener) kit, plugin);
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                KitPlayer kitPlayer = playerSupplier.getKitPlayer(player);
                if (kitPlayer.hasKit(kit)) {
                    kit.onEnable(kitPlayer);
                }
            }
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                kit.onDeactivation(playerSupplier.getKitPlayer(player));
            }
            if (kit instanceof Listener) {
                HandlerList.unregisterAll((Listener) kit);
            }
        }
    }

    public void register(KitPlayerSupplier kitPlayerSupplier, KitSelector kitSelector, JavaPlugin plugin, Path path) {
        KitApiConfig.getInstance().register(path.toFile());
        this.playerSupplier = kitPlayerSupplier;
        this.kitSelector = kitSelector;
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(kitSelector, plugin);
        kits.add(MagmaKit.INSTANCE);
        kits.add(NinjaKit.INSTANCE);
        kits.add(NoneKit.INSTANCE);
        kits.add(BlinkKit.INSTANCE);
        kits.add(SurpriseKit.INSTANCE);
        kits.add(CopyCatKit.INSTANCE);
        kits.add(GladiatorKit.INSTANCE);
        kits.add(GamblerKit.INSTANCE);
        kits.add(SmogmogKit.INSTANCE);
        kits.add(RogueKit.INSTANCE);
        kits.add(SnailKit.INSTANCE);
        kits.add(DiggerKit.INSTANCE);
        kits.add(ReviveKit.INSTANCE);
        kits.add(TankKit.INSTANCE);
        kits.add(GravityKit.INSTANCE);
        kits.add(CannibalKit.INSTANCE);
        kits.add(ZickZackKit.INSTANCE);
        kits.add(ThorKit.INSTANCE);
        kits.add(StomperKit.INSTANCE);
        kits.add(DannyKit.INSTANCE);
        kits.add(JackhammerKit.INSTANCE);
        kits.add(SwitcherKit.INSTANCE);
        kits.add(SpitKit.INSTANCE);
        kits.add(SquidKit.INSTANCE);
        kits.add(ShapeShifterKit.INSTANCE);
        kits.add(SpidermanKit.INSTANCE);
        kits.add(ManipulationKit.INSTANCE);
        kits.add(EndermageKit.INSTANCE);
        kits.add(ViperKit.INSTANCE);
        kits.add(LumberjackKit.INSTANCE);
        kits.add(ReaperKit.INSTANCE);
        kits.add(GrapplerKit.INSTANCE);
        kits.add(ClawKit.INSTANCE);
        kits.add(AutomaticKit.INSTANCE);
        kits.add(AnchorKit.INSTANCE);
        kits.add(BarbarianKit.INSTANCE);
        kits.add(TurtleKit.INSTANCE);
        kits.add(GrandpaKit.INSTANCE);
        kits.add(BerserkerKit.INSTANCE);
        kits.add(ScoutKit.INSTANCE);
        kits.add(MonkKit.INSTANCE);
        kits.add(VampireKit.INSTANCE);
        kits.add(KayaKit.INSTANCE);
        kits.add(SoulstealerKit.INSTANCE);
        kits.add(AnalystKit.INSTANCE);
        kits.add(KangarooKit.INSTANCE);
        kits.add(HulkKit.INSTANCE);
        kits.add(DemomanKit.INSTANCE);
        kits.add(GardenerKit.INSTANCE);
        kits.add(BeequeenKit.INSTANCE);
        kits.add(PoseidonKit.INSTANCE);
        kits.add(PhantomKit.INSTANCE);
        kits.add(ArcherKit.INSTANCE);
        kits.add(SpecialistKit.INSTANCE);
        kits.add(PacifistKit.INSTANCE);
        kits.add(NeoKit.INSTANCE);
        kits.add(JokerKit.INSTANCE);
        kits.add(ChameleonKit.INSTANCE);
        kits.add(SwordmanKit.INSTANCE);
        kits.add(BearKit.INSTANCE);
        //kits.add(PirateKit.INSTANCE);
        //kits.add(BeamKit.INSTANCE);
        //sort alphabetically
        kits.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        registerKits();
        kitSelector.load();
    }

    private void registerKits() {
        KitApiConfig kitApiConfig = KitApiConfig.getInstance();
        for (AbstractKit kit : kits) {
            System.out.println(kit.getName());
            kitApiConfig.add(kit);
            kitApiConfig.load(kit);
            if (kit instanceof Listener) plugin.getServer().getPluginManager().registerEvents((Listener) kit, plugin);
        }
    }

    public KitItemSupplier getItemSupplier() {
        return itemSupplier;
    }

    public void setItemSupplier(KitItemSupplier itemSupplier) {
        this.itemSupplier = itemSupplier;
    }

    public List<AbstractKit> getEnabledKits() {
        return kits.stream().filter(AbstractKit::isEnabled).collect(Collectors.toList());
    }

    public List<AbstractKit> getAllKits() {
        return kits;
    }

    public AbstractKit getAlphabeticallyKit(int index) {
        List<AbstractKit> kits = new ArrayList<>(getEnabledKits());
        kits.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
        return kits.get(index);
    }

    public AbstractKit byItem(ItemStack itemStack) {
        for (AbstractKit kit : kits) {
            if (kit.getDisplayItems().stream().anyMatch(displayItem -> displayItem.isSimilar(itemStack))) {
                return kit;
            }
        }
        return null;
    }

    public KitPlayer getPlayer(Player player) {
        return playerSupplier.getKitPlayer(player);
    }

    public KitPlayer getRandomAlivePlayer() {
        return playerSupplier.getRandomAlivePlayer();
    }

    public boolean hasKitItemInAnyHand(Player player, AbstractKit kit) {
        //TODO edgecase hulk
        if (kit.getMainKitItem() != null && kit.getMainKitItem().getType().equals(Material.AIR) && player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            return true;
        }
        return player.getInventory().getItemInOffHand().isSimilar(kit.getMainKitItem()) || player.getInventory().getItemInMainHand().isSimilar(kit.getMainKitItem());
    }

    public void giveKitItemsIfInvFull(KitPlayer kitPlayer, AbstractKit kit) {
        itemSupplier.giveKitItems(kitPlayer, kit);
    }

    public void giveKitItemsIfInvFull(KitPlayer kitPlayer, AbstractKit kit, List<ItemStack> items) {
        itemSupplier.giveKitItems(kitPlayer, kit, items);
    }

    public void giveItemsIfSlotEmpty(KitPlayer kitPlayer, List<ItemStack> kits) {
        itemSupplier.giveItems(kitPlayer, kits);
    }

    public void removeKitItems(AbstractKit kit, Player player) {
        player.getInventory().removeItem(kit.getKitItems().toArray(new ItemStack[0]));
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public KitSelector getKitSelector() {
        return kitSelector;
    }

    public KitPlayerSupplier getPlayerSupplier() {
        return playerSupplier;
    }

    public boolean sendCooldownMessage(KitPlayer kitPlayer, AbstractKit kit) {
        if (kit.getCooldown() > 0) {
            Cooldown kitCooldown = kitPlayer.getKitCooldown(kit);
            Player player = Bukkit.getPlayer(kitPlayer.getUUID());
            if (player == null) {
                return false;
            }
            if (kitCooldown.hasCooldown()) {
                long timeLeft = (kitCooldown.getEndTime()) - System.currentTimeMillis();
                if (timeLeft <= 0) {
                    kitPlayer.clearCooldown(kit);
                    return false;
                }
                if (kit.getMainKitItem() != null && hasKitItemInAnyHand(player, kit)) {
                    player.sendActionBar(Localization.INSTANCE.getMessage("kit.cooldown",
                            ImmutableMap.of("numberInSeconds", String.valueOf(timeLeft / 1000D)),
                            ChatUtils.locale(player)));
                } else if (kit.getMainKitItem() == null) {
                    player.sendActionBar(Localization.INSTANCE.getMessage("kit.cooldown",
                            ImmutableMap.of("numberInSeconds", String.valueOf(timeLeft / 1000D)),
                            ChatUtils.locale(player)));
                }
                return true;
            }
        }
        return false;
    }

    public void setFeast(Feast feast) {
        if (this.feast == null) {
            this.feast = feast;
        }
    }

    public Feast getFeast() {
        return feast;
    }
}
