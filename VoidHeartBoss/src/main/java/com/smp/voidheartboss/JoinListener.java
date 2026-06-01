package com.smp.voidheartboss;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinListener implements Listener {

    private final VoidHeartBoss plugin;

    public JoinListener(VoidHeartBoss plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        updatePlayerTitle(player);

        if (!plugin.hasJoined(player.getUniqueId())) {
            plugin.markJoined(player.getUniqueId());

            new BukkitRunnable() {
                @Override
                public void run() {
                    sendWelcomeMessage(player);
                    giveWelcomeBook(player);
                    player.sendTitle(
                        "\u00a78\u00a7lERRANT DES LIMBES",
                        "\u00a77Bienvenue sur VoidHeart...",
                        20, 100, 20
                    );
                }
            }.runTaskLater(plugin, 40L);
        }
    }

    public void updatePlayerTitle(Player player) {
        org.bukkit.scoreboard.Scoreboard board = player.getScoreboard();
        if (board == null) board = Bukkit.getScoreboardManager().getMainScoreboard();

        String teamName = "vh_" + player.getName().substring(0, Math.min(player.getName().length(), 10));
        org.bukkit.scoreboard.Team team = board.getTeam(teamName);
        if (team == null) team = board.registerNewTeam(teamName);

        String prefix;
        if (plugin.hasWitherReward(player.getUniqueId())) {
            prefix = "\u00a74[\u00a7cL'Obscur\u00a74] ";
        } else if (plugin.hasElderReward(player.getUniqueId())) {
            prefix = "\u00a76[\u00a7eL'Eveille\u00a76] ";
        } else {
            prefix = "\u00a78[\u00a75Errant\u00a78] ";
        }

        team.setPrefix(prefix);
        team.addEntry(player.getName());
        player.setScoreboard(board);
    }

    private void giveWelcomeBook(Player player) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        meta.setTitle("\u00a75\u00a7lVoidHeart");
        meta.setAuthor("Le Vide");

        meta.addPage(
            "\u00a75\u00a7l\u2726 VOIDHEART \u2726\n\n" +
            "\u00a70Tu foules pour la\n" +
            "premiere fois ces\n" +
            "terres anciennes...\n\n" +
            "\u00a70Le monde est vaste,\n" +
            "dangereux, et\n" +
            "impitoyable.\n\n" +
            "\u00a75\u00a7oBonne chance,\n" +
            "aventurier."
        );

        meta.addPage(
            "\u00a74\u00a7l\u2620 LA MORT\n\n" +
            "\u00a70Ta tete drop a ta\n" +
            "mort. Tes amis ont\n" +
            "\u00a7c\u00a7l24h\u00a70 pour la\n" +
            "ramasser.\n\n" +
            "Posez la sur un\n" +
            "\u00a7l Bloc d'Obsidienne\n" +
            "\u00a70avec les ressources\n" +
            "requises.\n\n" +
            "\u00a74Les ressources\n" +
            "augmentent a\n" +
            "chaque mort..."
        );

        meta.addPage(
            "\u00a75\u00a7l\u26a1 LES PORTES\n\n" +
            "\u00a70Les portails ne\n" +
            "s'ouvrent qu'a\n" +
            "ceux qui en sont\n" +
            "dignes...\n\n" +
            "\u00a76Nether\u00a70 : Requiert\n" +
            "une relique des\n" +
            "profondeurs.\n\n" +
            "\u00a75End\u00a70 : Requiert un\n" +
            "fragment de\n" +
            "l'obscurite."
        );

        meta.addPage(
            "\u00a76\u00a7l\u2694 LES EPREUVES\n\n" +
            "\u00a7b\u2693 Elder Guardian\n" +
            "\u00a70Gardien des abysses\n" +
            "-> Debloque le Nether\n\n" +
            "\u00a7c\u2620 Wither\n" +
            "\u00a70Champion du neant\n" +
            "-> Debloque l'End\n\n" +
            "\u00a75\u2605 Ender Dragon\n" +
            "\u00a70Seigneur du vide\n" +
            "-> La transcendance"
        );

        meta.addPage(
            "\u00a75\u00a7l\u2726 PROGRESSION\n\n" +
            "\u00a78Errant des Limbes\n" +
            "\u00a70(Depart)\n\n" +
            "\u00a7e\u2193 L'Eveille\n" +
            "\u00a70Elder Guardian\n\n" +
            "\u00a7c\u2193 L'Obscur\n" +
            "\u00a70Wither\n\n" +
            "\u00a7d\u2193 L'Ascendant\n" +
            "\u00a70Ender Dragon\n\n" +
            "\u00a75\u00a7oChaque victoire\n" +
            "te forge..."
        );

        meta.addPage(
            "\u00a76\u00a7l\u2605 RECOMPENSES\n\n" +
            "\u00a7b\u2693 L'Eveille\n" +
            "\u00a70+2 coeurs dores\n" +
            "Respiration infinie\n\n" +
            "\u00a7c\u2620 L'Obscur\n" +
            "\u00a70+2 coeurs rouges\n" +
            "Immunite au feu\n\n" +
            "\u00a7d\u2605 L'Ascendant\n" +
            "\u00a70+2 coeurs violets\n" +
            "Immunite aux chutes\n" +
            "Oeuf de dragon"
        );

        book.setItemMeta(meta);
        player.getInventory().addItem(book);
        player.sendMessage("\u00a75\u00a7l[VoidHeart] \u00a77Un livre vous a ete remis. Lisez-le bien, aventurier.");
    }

   private void sendWelcomeMessage(Player player) {
    player.sendMessage("");
    player.sendMessage("\u00a75\u00a7l\u2726 BIENVENUE SUR VOIDHEART \u2726");
    player.sendMessage("\u00a77Le vide vous accueille, aventurier...");
    player.sendMessage("");
}
