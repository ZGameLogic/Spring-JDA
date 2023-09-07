package com.zgamelogic.testdata;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("File:test-props.txt")
@Getter
public class Config {

    @Value("${name}")
    private String name;

}
