package com.github.braians.springblog.config;

import java.util.Optional;

import com.github.braians.springblog.security.UserPrincipal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@EnableJpaAuditing
@Configuration
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditProvider(){
      return () -> {
          String currentAuditor;
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          if(authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
              currentAuditor = authentication.getPrincipal().toString();
          }else {
              currentAuditor = ((UserPrincipal) authentication.getPrincipal()).getUsername();
          }
          return Optional.of(currentAuditor);
      };
    }
    
}

/* class SpringSecurityAuditAware implements AuditorAware<String>{

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken){
            return Optional.empty();
        }
       
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return Optional.ofNullable(userPrincipal.getUsername());
    }


} */
