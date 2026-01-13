package com.murat.test.config;

import com.murat.test.dto.DataHolder;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.function.Function;

@Configuration
public class PlotConfig {

    @Value("classpath:plot.R")
    private Resource rSource;

    @Bean
    public Context graalVMContext() {
        return Context.newBuilder()
                .allowAllAccess(true)
                .allowNativeAccess(true)
                .option("engine.WarnInterpreterOnly", "false")
                .build();
    }

    @Bean
    public Function<DataHolder, String> plotFunction(@Autowired Context ctx) throws IOException {
        Source source = Source.newBuilder("R", rSource.getURL()).build();
        return ctx.eval(source).as(Function.class);
    }
}
