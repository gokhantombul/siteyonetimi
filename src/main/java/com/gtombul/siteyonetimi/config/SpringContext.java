package com.gtombul.siteyonetimi.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring tarafından yönetilmeyen veya DI sorunları yaşayan sınıfların
 * (mapper'lar gibi) Spring bean'lerine erişmesini sağlayan yardımcı sınıf.
 */
@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // Spring'in kendisini bu statik alana atamasını sağlar
        context = applicationContext;
    }

    /**
     * İstenen tipteki Spring bean'ini döndürür.
     * @param beanClass İstenen bean'in sınıfı
     * @return Spring tarafından yönetilen bean
     */
    public static <T> T getBean(Class<T> beanClass) {
        if (context == null) {
            throw new IllegalStateException("ApplicationContext başlatılmadı. SpringContext'in bir bean olduğundan emin olun.");
        }
        return context.getBean(beanClass);
    }
}