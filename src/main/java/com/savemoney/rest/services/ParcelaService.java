package com.savemoney.rest.services;

import com.savemoney.domain.models.ContaBancaria;
import com.savemoney.domain.models.Parcela;
import com.savemoney.rest.repositories.ParcelaRepository;
import com.savemoney.utils.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ParcelaService {

    @Autowired
    private ParcelaRepository parcelaRepository;

    public Parcela bucarPorId(Long id) {
        return parcelaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parcela não encontrada"));
    }

    public void atualizarStatus(Parcela parcela) {
        parcelaRepository.save(parcela);
    }

    public List<Parcela> buscarParcelasParaGerarFatura(LocalDate dataVencimento, ContaBancaria contaBancaria) {
        Integer mes = dataVencimento.getMonthValue();
        Integer ano = dataVencimento.getYear();
        return parcelaRepository.buscarParcelasParaGerarFatura(mes, ano, contaBancaria);
    }
}
