package com.nelioalves.cursomc.validation;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.enums.TipoCliente;
import com.nelioalves.cursomc.repository.ClienteRepository;
import com.nelioalves.cursomc.utils.BR;

import org.springframework.beans.factory.annotation.Autowired;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository repo;
    
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

		Map<String, String> errorsMap = new HashMap<>();

		if (objDto.getTipo().equals(TipoCliente.PESSOA_FISICA.getCodigo()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			errorsMap.put("cpfOuCnpj", "CPF inválido");
		}

		if (objDto.getTipo().equals(TipoCliente.PESSOA_JURIDICA.getCodigo()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			errorsMap.put("cpfOuCnpj", "CNPJ inválido");
		}

        Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
			errorsMap.put("email", "Email já existente");
        }
        
        for (Map.Entry<String, String> entry : errorsMap.entrySet()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(entry.getValue())
                    // .addPropertyNode(entry.getValue())
					.addConstraintViolation();
        }
		
		return errorsMap.isEmpty();
	}
}