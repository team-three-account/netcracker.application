package com.gmail.netcracker.application.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class AppInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * Возвращает конфигурацию, в которой
     * инициализируем ViewResolver.
     *
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    /**
     * Возвращает конфигурации,
     * которые инициализируют Beans.
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                RootConfig.class,

        };
    }

    /**
     * Настроили мэпинг сервлета на "/"
     * и поэтому все запросы будут перехвачены
     * Диспетчером Сервлета Spring.
     *
     * @return Массив типа String.
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * Настройка ссесии.
     *
     *AbstractAnnotationConfigDispatcherServletInitializer.
     */
    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        final FilterRegistration.Dynamic encodingFilter = servletContext
                .addFilter(
                        "encodingFilter",
                        new CharacterEncodingFilter()
                );
        encodingFilter.setInitParameter("encoding", "UTF-8");
        encodingFilter.setInitParameter("forceEncoding", "true");
        encodingFilter.addMappingForUrlPatterns(null, true, "/*");
    }

    /**
     * Включение исключений NoHandlerFound.
     *
     */
    @Override
    protected DispatcherServlet createDispatcherServlet(
            final WebApplicationContext context
    ) {
        final DispatcherServlet dispatcherServlet =
                (DispatcherServlet) super.createDispatcherServlet(context);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        return dispatcherServlet;
    }
}
