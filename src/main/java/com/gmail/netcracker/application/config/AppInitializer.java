package com.gmail.netcracker.application.config;

import com.gmail.netcracker.application.utilites.SessionListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;
import java.io.File;

public class AppInitializer
        extends AbstractAnnotationConfigDispatcherServletInitializer {

    private int maxUploadSizeInMb = 10 * 1024 * 1024; // 5 MB

    /**
     * Возвращает конфигурацию, в которой
     * инициализируем ViewResolver.
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
     * <p>
     * AbstractAnnotationConfigDispatcherServletInitializer.
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
        servletContext.addListener(new SessionListener());
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {

        // upload temp file will put here
        File uploadDirectory = new File("");

        // register a MultipartConfigElement
        MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(uploadDirectory.getAbsolutePath(),
                        maxUploadSizeInMb, maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);

        registration.setMultipartConfig(multipartConfigElement);


    }

    /**
     * Включение исключений NoHandlerFound.
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
