package com.example.phaseBotOmega.discord;

import com.example.phaseBotOmega.discord.messageHandlers.MessageHandler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PhaseBotMessageListener extends ListenerAdapter {

    Logger logger = LoggerFactory.getLogger(getClass());

    Map <String,MessageHandler> handlers = new ConcurrentHashMap();

    CommandData[] commandData;

    public PhaseBotMessageListener(MessageHandler[] handlers) {
        ArrayList<SlashCommandData> dataList = new ArrayList<>();

        // For each message handler
        for (MessageHandler handler : handlers)
        {
            this.handlers.put(handler.getSlashCommand(),handler);
            SlashCommandData data =  Commands.slash(handler.getSlashCommand(),handler.getDesc());
            if (handler.getOptions() != null) {
                data.addOptions(handler.getOptions());
            }

            dataList.add(data);
        }
        commandData = dataList.toArray(new CommandData[0]);

        System.out.println ("supDawg" + handlers.length);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        logger.info(event.toString());

        System.out.println(event.getName());

        MessageHandler handler = handlers.get(event.getName());

        if (handler==null)
        {
            event.reply("I DONT KNOW SLASH COMMANDS WELL ENOUGH YET");
        }

        handler.handleAction(event);


    }

    public CommandData[] getCommandData() {

        return commandData;
    }
}
