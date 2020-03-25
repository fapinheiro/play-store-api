package com.nelioalves.cursomc.validation;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.repository.ClienteRepository;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ClienteRepository repo;

	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
        
        Map<String, String> errorsMap = new HashMap<>();

		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null && !aux.getId().equals(uriId)) {
			errorsMap.put("email", "Email já existente");
		}
        
        for (Map.Entry<String, String> entry : errorsMap.entrySet()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(entry.getValue())
                    // .addPropertyNode(entry.getKey())
					.addConstraintViolation();
        }
		
		return errorsMap.isEmpty();
	}
}