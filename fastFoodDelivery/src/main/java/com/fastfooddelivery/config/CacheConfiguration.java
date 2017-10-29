package com.fastfooddelivery.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Endereco.class.getName(), jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Cartao.class.getName(), jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Pais.class.getName(), jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Pedido.class.getName(), jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.FormaDeEntrega.class.getName(), jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Bebida.class.getName(), jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Preparo.class.getName(), jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Alimento.class.getName(), jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.TipoAlimento.class.getName(), jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Refeicao.class.getName(), jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.ValorRefeicao.class.getName(), jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Doce.class.getName(), jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.ValorNutricional.class.getName(), jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Tempero.class.getName(), jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Cliente.class.getName(), jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Pais.class.getName() + ".cartaos", jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Pedido.class.getName() + ".bebidas", jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Pedido.class.getName() + ".alimentos", jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Bebida.class.getName() + ".doces", jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Alimento.class.getName() + ".preparos", jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Alimento.class.getName() + ".temperos", jcacheConfiguration);
            cm.createCache(com.fastfooddelivery.domain.Refeicao.class.getName() + ".tipoAlimentos", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
