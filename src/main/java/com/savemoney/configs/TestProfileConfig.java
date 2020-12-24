package com.savemoney.configs;

import com.savemoney.domain.enums.Mes;
import com.savemoney.domain.enums.StatusPagamento;
import com.savemoney.domain.models.*;
import com.savemoney.rest.repositories.*;
import com.savemoney.security.domain.models.Usuario;
import com.savemoney.security.rest.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestProfileConfig {

    @Autowired
    private DBTestService service;

    @Bean
    @Transactional(noRollbackFor = Exception.class)
    public boolean initializeDB() {
        service.initializeDB();
        return true;
    }

    @Service
    public static class DBTestService {

        @Autowired
        private BancoRepository bancoRepository;

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private ContaBancariaRepository contaBancariaRepository;

        @Autowired
        private DespesaRepository despesaRepository;

        @Autowired
        private ParcelaRepository parcelaRepository;

        @Autowired
        private ItemCartaoRepository itemCartaoRepository;

        @Autowired
        private CartaoCreditoRepository cartaoCreditoRepository;

        @Autowired
        private FaturaRepository faturaRepository;

        public void initializeDB() {
            Banco banco = bancoRepository.findById(1L).get();

            BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
            Usuario usuario = new Usuario("admin@email.com", crypt.encode("1234"));
            usuarioRepository.save(usuario);

            ContaBancaria contaBancaria =
                    new ContaBancaria(1L,
                            "0001",
                            "123456",
                            new BigDecimal("1500"));
            contaBancaria.setUsuario(usuario);
            contaBancaria.setBanco(banco);
            contaBancariaRepository.save(contaBancaria);

            Despesa despesa = new Despesa(LocalDate.of(2020, 7, 15),
                    LocalDate.of(2020, 7, 30),
                    "Camiseta",
                    new BigDecimal("50"),
                    StatusPagamento.PENDENTE);
            despesa.setContaBancaria(contaBancaria);
            despesaRepository.save(despesa);

            Parcela parcela1 = new Parcela(1L,
                    1,
                    new BigDecimal("50"),
                    StatusPagamento.PENDENTE,
                    LocalDate.of(2020, 1, 7));
            parcela1.setMesVencimento(Mes.JANEIRO);
            parcela1.setDescricao("Tênis - 1/3");

            Parcela parcela2 = new Parcela(2L,
                    2,
                    new BigDecimal("50"),
                    StatusPagamento.PENDENTE,
                    LocalDate.of(2020, 2, 7));
            parcela2.setMesVencimento(Mes.FEVEREIRO);
            parcela2.setDescricao("Tênis - 2/3");

            Parcela parcela3 = new Parcela(3L,
                    3,
                    new BigDecimal("50"),
                    StatusPagamento.PENDENTE,
                    LocalDate.of(2020, 3, 7));
            parcela3.setMesVencimento(Mes.MARCO);
            parcela3.setDescricao("Tênis - 3/3");
            parcelaRepository.saveAll(Arrays.asList(parcela1, parcela2, parcela3));

            ItemCartao item = new ItemCartao(1L,
                    LocalDate.now(),
                    "Tênis",
                    new BigDecimal("150"),
                    3);
            item.setContaBancaria(contaBancaria);
            item.adicionarParcela(parcela1);
            item.adicionarParcela(parcela2);
            item.adicionarParcela(parcela3);
            itemCartaoRepository.save(item);

            CartaoCredito cartaoCredito =
                    new CartaoCredito(1L,
                            "1234567812345678",
                            10,
                            new BigDecimal("2500"));
            cartaoCredito.setContaBancaria(contaBancaria);
            cartaoCredito.adicionarItem(item);
            cartaoCreditoRepository.save(cartaoCredito);

            Fatura fatura = new Fatura(Arrays.asList(parcela1),
                    StatusPagamento.PENDENTE,
                    LocalDate.of(2020, 1, 10));
            BigDecimal totalFatura = fatura.getParcelas().stream()
                    .map(Parcela::getValor)
                    .reduce(BigDecimal::add)
                    .get();
            fatura.setTotal(totalFatura);
            fatura.setCartaoCredito(cartaoCredito);
            faturaRepository.save(fatura);
        }
    }
}
