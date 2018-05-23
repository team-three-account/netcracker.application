package com.gmail.netcracker.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@Configuration
@EnableWebMvc
@ComponentScan(
        basePackages = {
                "com.gmail.netcracker.application.controller",
                "com.gmail.netcracker.application.config"
        }
)
public class WebConfig extends WebMvcConfigurerAdapter {

    /**
     * Тип кодировки вьюшек.
     */
    private static final String CONTENT_TYPE = "text/html;charset=UTF-8";

    /**
     * Путь к вьюшкам.
     */
    private static final String PREFIX = "/WEB-INF/views/";

    /**
     * Разрешение вьюшек.
     */
    private static final String SUFFIX = ".jsp";

    /**
     * Путь к ресурсам.
     */

    private static final String RESOURCES_URL = "/resources/";

    /**
     * Указывает Spring'у где находятся
     * компоненты
     * представления, и как их отображать.
     * Вьюшкибудут лежать в директории
     * /WEB-INF/views/ и иметь разширение *.jsp.
     *
     * @return Реализация интерфейса ViewResolver
     * с настройками для вьюшек.
     */
    @Bean
    public ViewResolver viewResolver() {
        final InternalResourceViewResolver viewResolver =
                new InternalResourceViewResolver();
        viewResolver.setContentType(CONTENT_TYPE);
        viewResolver.setPrefix(PREFIX);
        viewResolver.setSuffix(SUFFIX);
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setExposeContextBeansAsAttributes(true);
        return viewResolver;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    /**
     * Указывает где будут хранится ресурсы.
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry resource) {
        resource.addResourceHandler(RESOURCES_URL + "**")
                .addResourceLocations(RESOURCES_URL);
    }

}
