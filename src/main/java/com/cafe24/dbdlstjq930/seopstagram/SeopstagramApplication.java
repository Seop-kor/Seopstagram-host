package com.cafe24.dbdlstjq930.seopstagram;

import com.cafe24.dbdlstjq930.seopstagram.Properties.FileUploadProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileUploadProperties.class
})
public class SeopstagramApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeopstagramApplication.class, args);
    }

}
