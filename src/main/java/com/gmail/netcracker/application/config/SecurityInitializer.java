package com.gmail.netcracker.application.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;


// якщо ми не юзаємо web.xml треба exteds AbstractSecurityWebApplicationInitializer для того щоб налаштувати початкові фільтри
// якщо ми хочемо створити свої фільтри нам треба listener, а щоб включити listener нам треба перевизначити метод
// enableHttpSessionEventPublisher і зробити тру) інакше наш фільтр в appInit не буде працювати
public class SecurityInitializer
        extends AbstractSecurityWebApplicationInitializer {

    @Override
    public boolean enableHttpSessionEventPublisher() {
        return true;
    }
}
