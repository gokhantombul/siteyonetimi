package com.gtombul.siteyonetimi.model.base;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
// beanName'i vererek hangi AuditorAware'in kullanılacağını garantiye alalım
@EnableJpaAuditing(auditorAwareRef = "securityAuditorAware")
public class AuditingConfig {

    // Bu metot, SecurityAuditorAware sınıfını @Component yaptığımız için
    // teknik olarak GEREKSİZ hale geldi. Spring onu zaten bulacak.
    // Ancak, @Bean tanımı olarak kalması da bir sakınca yaratmaz,
    // hatta daha "açık" (explicit) bir konfigürasyon sağlar.

    // @Bean // Eğer @Component kullandıysak bu @Bean'e gerek yok.
    // public AuditorAware<Long> auditorAware() {
    //    return new SecurityAuditorAware();
    // }

    // En temizi: @Component'i SecurityAuditorAware'de bırakın,
    // bu @Bean metodunu silin. Sadece @EnableJpaAuditing(auditorAwareRef = "securityAuditorAware") kalsın.
}