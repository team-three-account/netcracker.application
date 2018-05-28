package com.gmail.netcracker.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * Spring configuration class for components
 * submission, MVC configuration.
 * Specifies Spring where the components are located
 * views, and how to display them.
 * Tagged with @Configuration annotation -
 * the class is the source of the definition
 * Bins; annotation @EnableWebMvc -
 * Allows the project to use MVC;
 * an annotation
 */
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
     * View encoding type.
     */
    private static final String CONTENT_TYPE = "text/html;charset=UTF-8";

    /**
     *
     * Path to views.
     */
    private static final String PREFIX = "/WEB-INF/views/";

    /**
     * View resolution.
     */
    private static final String SUFFIX = ".jsp";

    /**
     *
     * Path to resources.
     */

    private static final String RESOURCES_URL = "/resources/";

    /**
     * Indicates where Spring is located
     * * Components
     * * views, and how to display them.
     * * Views will be in the directory
     * * / WEB-INF / views / and have the expansion * .jsp.
     *
     * @return ViewResolver.
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
     * Indicates where resources will be stored.
     */
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry resource) {
        resource.addResourceHandler(RESOURCES_URL + "**")
                .addResourceLocations(RESOURCES_URL);
    }

}
