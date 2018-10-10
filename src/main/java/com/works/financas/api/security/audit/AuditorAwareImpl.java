package com.works.financas.api.security.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/*
 *  A interface AuditorAware é usada para obter informações do usuário do Spring
 *  Security para campos createdby e modifiedby.
 * 
 */

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

		@Override
		public Optional<String> getCurrentAuditor() {
			String uname = SecurityContextHolder.getContext().getAuthentication().getName();
	        return Optional.of(uname);
    }
}
