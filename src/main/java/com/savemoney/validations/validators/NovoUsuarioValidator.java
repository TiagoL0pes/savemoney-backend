package com.savemoney.validations.validators;

import com.savemoney.security.rest.repositories.UsuarioRepository;
import com.savemoney.utils.exceptions.BadRequestException;
import com.savemoney.validations.annotations.EmailNovoUsuario;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

@SupportedValidationTarget(ValidationTarget.ANNOTATED_ELEMENT)
public class NovoUsuarioValidator implements ConstraintValidator<EmailNovoUsuario, String> {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void initialize(EmailNovoUsuario constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(email)) {
            throw new BadRequestException("E-mail não informado");
        }

        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(email)) {
            throw new BadRequestException("Formato do e-mail está incorreto");
        }

        boolean usuarioExistente = validarNovoUsuario(email);
        if (usuarioExistente) {
            throw new BadRequestException("Email informado já foi cadastrado");
        }

        return true;
    }

    private boolean validarNovoUsuario(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }
}
