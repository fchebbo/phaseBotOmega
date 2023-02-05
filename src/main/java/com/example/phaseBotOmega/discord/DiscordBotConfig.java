package com.example.phaseBotOmega.discord;

import com.theokanning.openai.OpenAiService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;

@Configuration
public class DiscordBotConfig {
    @Value("${pb.discord.token}")
    private String discordToken;

    @Autowired
    private PhaseBotMessageListener phaseBotMessageListener;


    @Bean
    public JDA jda() throws LoginException {
        JDABuilder builder = JDABuilder.createDefault(discordToken);
        builder.setActivity(Activity.playing("GOD - type /pb to see options!"));
        JDA jda =  builder.build();
        jda.addEventListener(phaseBotMessageListener);

        jda.updateCommands().addCommands(phaseBotMessageListener.getCommandData()).queue();
        return jda;
    }


}
