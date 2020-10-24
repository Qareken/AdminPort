package uzkor.aziz.adminIntra.AdminPort.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Value("${upload.folder}")
    private String uploadFolder;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String myExternalFilePath = "file:///D:/DBase/upload/";

        registry.addResourceHandler("/upload/**").addResourceLocations(myExternalFilePath);


    }

}
