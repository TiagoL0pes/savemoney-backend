package com.savemoney.validators;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.security.domain.models.Usuario;
import com.savemoney.security.rest.repositories.UsuarioRepository;
import com.savemoney.templates.models.UsuarioTemplate;
import com.savemoney.utils.exceptions.BadRequestException;
import com.savemoney.validations.validators.EmailUsuarioValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintValidatorContext;

import java.util.Optional;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class EmailUsuarioTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private EmailUsuarioValidator validator;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
    }

    @Test
    public void deveRetornarEmailValido() {
        String email = "email@admin.com";

        boolean result = validator.isValid(email, context);

        assertTrue(result);
    }

    @Test
    public void deveValidarEmailEmBranco() {
        String email = "";

        assertThrows(BadRequestException.class, () -> {
            validator.isValid(email, context);
        });
    }

    @Test
    public void deveValidarEstruturaEmail() {
        String email1 = "email@@admin.com";
        String email2 = "@admin.com";
        String email3 = "email@admin.";
        String email4 = "email.admin.com";
        String email5 = "email@admincom";

        assertThrows(BadRequestException.class, () -> {
            validator.isValid(email1, context);
        });

        assertThrows(BadRequestException.class, () -> {
            validator.isValid(email2, context);
        });

        assertThrows(BadRequestException.class, () -> {
            validator.isValid(email3, context);
        });

        assertThrows(BadRequestException.class, () -> {
            validator.isValid(email4, context);
        });

        assertThrows(BadRequestException.class, () -> {
            validator.isValid(email5, context);
        });
    }

    @Test
    public void deveValidarEmailDuplicado() {
        Usuario usuario = Fixture.from(Usuario.class)
                .gimme(UsuarioTemplate.VALIDO);
        String email = "email@admin.com";

        Mockito.when(usuarioRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(usuario));

        assertThrows(BadRequestException.class, () -> {
            validator.isValid(email, context);
        });
    }
}
