package com.smp.voidheartboss;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinListener implements Listener {

    private final VoidHeartBoss plugin;

    public JoinListener(VoidHeartBoss plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Titre au dessus de la tete (nametag)
        updatePlayerTitle(player);

        // Message premiere connexion
        if (!plugin.hasJoined(player.getUniqueId())) {
            plugin.markJoined(player.getUniqueId());

            new BukkitRunnable() {
                @Override
                public void run() {
                    sendWelcomeMessage(player);
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
        String prefix;
        String color;

        if (plugin.hasWitherReward(player.getUniqueId())) {
            prefix = "\u00a74[\u00a7cL'Obscur\u00a74]";
        } else if (plugin.hasElderReward(player.getUniqueId())) {
            prefix = "\u00a76[\u00a7eL'Eveille\u00a76]";
        } else {
            prefix = "\u00a78[\u00a75Errant des Limbes\u00a78]";
        }

        // Utilise le scoreboardteam pour afficher le titre
        org.bukkit.scoreboard.Scoreboard board = player.getScoreboard();
        if (board == null) board = org.bukkit.Bukkit.getScoreboardManager().getMainScoreboard();

        String teamName = "vh_" + player.getName().substring(0, Math.min(player.getName().length(), 10));
        org.bukkit.scoreboard.Team team = board.getTeam(teamName);
        if (team == null) team = board.registerNewTeam(teamName);

        team.setPrefix(prefix + " ");
        team.addEntry(player.getName());
        player.setScoreboard(board);
    }

    private void sendWelcomeMessage(Player player) {
        player.sendMessage("");
        player.sendMessage("\u00a75\u00a7l================================================");
        player.sendMessage("\u00a7d\u00a7l        \u2726 BIENVENUE SUR VOIDHEART \u2726");
        player.sendMessage("\u00a75\u00a7l================================================");
        player.sendMessage("");
        player.sendMessage("\u00a77Tu foules pour la premiere fois ces terres anciennes...");
        player.sendMessage("\u00a77Le monde est vaste, dangereux, et impitoyable.");
        player.sendMessage("");
        player.sendMessage("\u00a76\u00a7l\u2694 LES LOIS DE VOIDHEART \u2694");
        player.sendMessage("");
        player.sendMessage("\u00a7c\u2620 \u00a77La mort est permanente \u2014 tes amis peuvent");
        player.sendMessage("   \u00a77te reanimet via un \u00a7fAutel d'Obsidienne\u00a77.");
        player.sendMessage("");
        player.sendMessage("\u00a7b\u2693 \u00a77Prouve ta valeur en vainquant les \u00a7fGardiens\u00a77,");
        player.sendMessage("   \u00a77le \u00a7fWither\u00a77 et l'\u00a7fEnder Dragon\u00a77.");
        player.sendMessage("");
        player.sendMessage("\u00a7d\u2726 \u00a77Chaque victoire te forge en \u00a7eL'Eveille\u00a77,");
        player.sendMessage("   \u00a7cL'Obscur\u00a77, ou \u00a7dL'Ascendant\u00a77...");
        player.sendMessage("");
        player.sendMessage("\u00a75\u00a7l================================================");
        player.sendMessage("\u00a77              \u00a7o Bonne chance, aventurier.");
        player.sendMessage("\u00a75\u00a7l================================================");
        player.sendMessage("");
    }
}
