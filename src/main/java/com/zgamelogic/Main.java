package com.zgamelogic;

import com.zgamelogic.boot.JDASpringApplication;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.zgamelogic.test"})
public class Main {
    public static void main(String[] args) {

        JDABuilder bot = JDABuilder.createDefault("NzM4ODUxMzM2NTY0NzY4ODY4.GtaXjD.vnf3Rnqu269cjLxENIY6uPOq6ismujKMLnYn8s");

        JDASpringApplication app = new JDASpringApplication(bot, Main.class);
        app.run();

    }
}