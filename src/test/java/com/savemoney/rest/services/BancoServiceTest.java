package com.savemoney.rest.services;

import br.com.six2six.fixturefactory.Fixture;
import com.savemoney.domain.models.Banco;
import com.savemoney.domain.pagination.BancosPagination;
import com.savemoney.rest.repositories.BancoRepository;
import com.savemoney.templates.models.BancoTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static br.com.six2six.fixturefactory.loader.FixtureFactoryLoader.loadTemplates;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class BancoServiceTest {

    @Mock
    private BancoRepository bancoRepository;

    @InjectMocks
    private BancoService bancoService;

    @BeforeEach
    public void setup() {
        loadTemplates("com.savemoney.templates");
    }

    @Test
    public void deveListarBancos() {
        List<Banco> bancos = Fixture.from(Banco.class)
                .gimme(1, BancoTemplate.VALIDO);
        final int pagina = 0;
        final int tamanho = 10;

        PageImpl<Banco> pageBancos = new PageImpl<>(bancos);
        Mockito.when(bancoRepository.findAll(any(Pageable.class)))
                .thenReturn(pageBancos);

        BancosPagination bancosPagination =
                bancoService.listar(PageRequest.of(pagina, tamanho));

        assertNotNull(bancosPagination);
    }

}
