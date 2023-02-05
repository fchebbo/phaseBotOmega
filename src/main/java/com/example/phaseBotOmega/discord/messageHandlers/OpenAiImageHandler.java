package com.example.phaseBotOmega.discord.messageHandlers;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.image.CreateImageRequest;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenAiImageHandler extends MessageHandler{
    private static String PROMPT = "prompt"; //lol magic strings

    @Autowired
    OpenAiService openAiService;

    @Override
    public String getDesc() {
        return "Have chatGPT solve your problems";
    }

    @Override
    public String getSlashCommand() {
        return "pbimagebot";
    }

    @Override
    public void handleAction(SlashCommandInteractionEvent event) {

        CreateImageRequest request = CreateImageRequest.builder().prompt(event.getOption(PROMPT).getAsString()).build();

        event.deferReply().queue();
        String url="";
        try {
            url = openAiService.createImage(request).getData().get(0).getUrl();
        } catch (Exception e) {
            url = "failed to generate image :(";
        }
        event.getHook().sendMessage(event.getOption(PROMPT).getAsString()).queue();
        event.getHook().sendMessage(url).queue();

    }

    @Override
    public OptionData[] getOptions(){
        return new OptionData[]
                {
                        new OptionData(OptionType.STRING, "prompt","prompt to ask",true,true)
                };
    }
}
