package br.ufrn.imd.bancovestido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication(scanBasePackages = "br.ufrn.imd.bancovestido", exclude = {SecurityAutoConfiguration.class})
@EnableJpaRepositories(basePackages = "br.ufrn.imd.bancovestido")
@EntityScan("br.ufrn.imd.bancovestido")
@EnableSpringDataWebSupport
@EnableJpaAuditing
public class BancoVestidoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BancoVestidoApplication.class, args);
    }

}
