package dev.rosewood.rosegarden.command.command;

import dev.rosewood.rosegarden.RosePlugin;
import dev.rosewood.rosegarden.command.framework.CommandContext;
import dev.rosewood.rosegarden.command.framework.RoseCommand;
import dev.rosewood.rosegarden.command.framework.RoseCommandWrapper;
import dev.rosewood.rosegarden.command.framework.annotation.RoseExecutable;
import dev.rosewood.rosegarden.manager.AbstractLocaleManager;
import dev.rosewood.rosegarden.utils.StringPlaceholders;
import java.util.Collections;
import java.util.List;

public class HelpCommand extends RoseCommand {

    public HelpCommand(RosePlugin rosePlugin, RoseCommandWrapper parent) {
        super(rosePlugin, parent);
    }

    @RoseExecutable
    public void execute(CommandContext context) {
        AbstractLocaleManager localeManager = this.rosePlugin.getManager(AbstractLocaleManager.class);

        localeManager.sendCommandMessage(context.getSender(), "command-help-title");
        for (RoseCommand command : this.parent.getCommands()) {
            if (!command.hasHelp() || !command.canUse(context.getSender()))
                continue;

            StringPlaceholders stringPlaceholders = StringPlaceholders.builder("cmd", this.parent.getName())
                    .addPlaceholder("subcmd", command.getName().toLowerCase())
                    .addPlaceholder("args", command.getArgumentsString())
                    .addPlaceholder("desc", localeManager.getLocaleMessage(command.getDescriptionKey()))
                    .build();

            localeManager.sendSimpleCommandMessage(context.getSender(), "command-help-list-description" + (command.getNumParameters() == 0 ? "-no-args" : ""), stringPlaceholders);
        }
    }

    @Override
    public String getDefaultName() {
        return "help";
    }

    @Override
    public List<String> getDefaultAliases() {
        return Collections.emptyList();
    }

    @Override
    public String getDescriptionKey() {
        return "command-help-description";
    }

    @Override
    public String getRequiredPermission() {
        return null;
    }

}