package com.bone.was;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class AppRunner implements ApplicationRunner {
    @Autowired
    ResourceLoader resourceLoader;

    @Override
    public void run(ApplicationArguments args) throws Exception{
        Resource resource = resourceLoader.getResource("classpath:/static/.well-known/pki-validation/DFEC40A1632426A425FF7AEE29053E64.txt"); //
        System.out.println(resource.exists());

        System.out.println(Files.readString(Path.of(resource.getURI())));
    }
}