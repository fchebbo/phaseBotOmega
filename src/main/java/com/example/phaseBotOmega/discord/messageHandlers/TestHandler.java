package com.example.phaseBotOmega.discord.messageHandlers;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.image.CreateImageRequest;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestHandler extends MessageHandler{

    @Autowired
    OpenAiService openAiService;

    private static final String ECHO_TEXT = "echotext";

    @Override
    public String getDesc() {
        return "This will just echo back your stuff lol";
    }

    @Override
    public String getSlashCommand() {
        return "pbtest";
    }

    @Override
    public void handleAction(SlashCommandInteractionEvent event) {

        CreateImageRequest request = CreateImageRequest.builder().prompt(event.getOption(ECHO_TEXT).getAsString()).build();

        event.deferReply().queue();
        String url="";
        try {
            url = openAiService.createImage(request).getData().get(0).getUrl();
        } catch (Exception e) {
            url = "failed to generate image :(";
        }
        event.getHook().sendMessage(event.getOption(ECHO_TEXT).getAsString()).queue();
        event.getHook().sendMessage(url).queue();

    }

    @Override
    public OptionData[] getOptions(){
        return new OptionData[]
                {
                        new OptionData(OptionType.STRING, ECHO_TEXT,"Text to echo back",true,true)
                };
    }
}
