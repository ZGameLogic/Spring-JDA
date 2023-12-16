package com.zgamelogic.test;

import com.zgamelogic.boot.JDASpringApplication;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Test {
    public static void main(String[] args){
        JDASpringApplication app = new JDASpringApplication(Test.class);
        app.run(args);
    }

    @Bean
    public JDABuilder bot(){
        JDABuilder botBuilder = JDABuilder.createDefault("NzM4ODUxMzM2NTY0NzY4ODY4.Gos_J-.xqvVpTTnOSEULDaBm5DCuppinqRty9uh7qffm8");
        botBuilder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
        botBuilder.setEventPassthrough(true);
        return botBuilder;
    }
}
