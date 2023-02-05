package com.example.phaseBotOmega.discord.messageHandlers;

import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenAiChatHandler extends MessageHandler{
    private static String PROMPT = "prompt"; //lol magic strings

    @Autowired
    OpenAiService openAiService;

    @Override
    public String getDesc() {
        return "Have chatGPT solve your problems";
    }

    @Override
    public String getSlashCommand() {
        return "pbchatbot";
    }

    @Override
    public void handleAction(SlashCommandInteractionEvent event) {

        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(event.getOption(PROMPT).getAsString())
                .model("davinci")
                .echo(true)
                .maxTokens(200)
                .build();

        StringBuffer replyBuffer = new StringBuffer();

        event.deferReply().queue();

        openAiService.createCompletion(completionRequest).getChoices().
                forEach(t -> replyBuffer.append(t.getText()));

        event.getHook().sendMessage(replyBuffer.toString()).queue();
    }

    @Override
    public OptionData[] getOptions(){
        return new OptionData[]
                {
                        new OptionData(OptionType.STRING, "prompt","/prompt to ask",true,true)
                };
    }
}
