package ac.grim.grimac.command.commands;

import ac.grim.grimac.GrimAPI;
import ac.grim.grimac.checks.impl.misc.SetBackTimeListener;
import ac.grim.grimac.command.BuildableCommand;
import ac.grim.grimac.platform.api.command.PlayerSelector;
import ac.grim.grimac.platform.api.player.PlatformPlayer;
import ac.grim.grimac.platform.api.sender.Sender;
import ac.grim.grimac.utils.anticheat.MessageUtil;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.parser.standard.LongParser;
import org.jetbrains.annotations.NotNull;


public class GrimSetBack implements BuildableCommand {
    @Override
    public void register(CommandManager<Sender> commandManager) {
        commandManager.command(
                commandManager.commandBuilder("snowgrim", "snowgrimac")
                        .literal("setback")
                        .permission("snowgrim.setback")
                        .required("target", GrimAPI.INSTANCE.getCommandAdapter().singlePlayerSelectorParser())
                        .required("time", LongParser.longParser())
                        .handler(this::handleSpectate)
        );
    }

    private void handleSpectate(@NotNull CommandContext<Sender> context) {
        Sender sender = context.sender();
        PlayerSelector targetSelectorResults = context.getOrDefault("target", null);
        Long timeSelectorResults = context.getOrDefault("time", null);
        if (targetSelectorResults == null || timeSelectorResults == null) return;

        PlatformPlayer targetPlatformPlayer = targetSelectorResults.getSinglePlayer().getPlatformPlayer();

        if (targetPlatformPlayer == null || targetPlatformPlayer.isExternalPlayer()) {
            sender.sendMessage(MessageUtil.getParsedComponent(sender, "player-not-this-server", "%prefix% &cThis player isn't on this server!"));
            return;
        }

        // hide player from tab list
        if (SetBackTimeListener.addSetBack(targetPlatformPlayer.getUniqueId(),(timeSelectorResults * 1_000_000))) {
            Component component = MessageUtil.getParsedComponent(
                    sender,
                    "setback-setted",
                    "%prefix% &bsetback &fsetted for &b%target% &f(%time%ms)"
            );
            component = component.replaceText(builder -> builder
                    .matchLiteral("%target%")
                    .replacement(targetPlatformPlayer.getName())
            );
            component = component.replaceText(builder -> builder
                    .matchLiteral("%time%")
                    .replacement(String.valueOf(timeSelectorResults))
            );
            sender.sendMessage(component);
        } else {
            sender.sendMessage(MessageUtil.getParsedComponent(sender, "setback-error", "%prefix% &cError! Maybe this player already setback'ed!"));
        }
    }
}
