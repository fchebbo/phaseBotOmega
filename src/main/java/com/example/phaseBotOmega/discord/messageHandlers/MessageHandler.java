package com.example.phaseBotOmega.discord.messageHandlers;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public abstract class MessageHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    public MessageHandler(){
        logger.info("Starting: " + this.getClass().getSimpleName() + " " + getSlashCommand());
    }

    /**
     * The description of this handler to be returned by the help message.
     **/
    public abstract String getDesc();

    /**
     * The string used to trigger this handler.
     **/
    public abstract String getSlashCommand();

    public abstract void handleAction(SlashCommandInteractionEvent event);

    public OptionData[] getOptions()
    {
        return null;
    }
}
