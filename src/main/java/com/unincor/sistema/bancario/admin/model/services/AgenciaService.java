/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unincor.sistema.bancario.admin.model.services;

import com.unincor.sistema.bancario.admin.exceptions.CadastroExceptions;
import com.unincor.sistema.bancario.admin.model.dao.AgenciaDao;
import com.unincor.sistema.bancario.admin.model.domain.Agencia;
import java.util.List;

/**
 *
 * @author Ferna
 */
public class AgenciaService {

    private final AgenciaDao agenciaDao = new AgenciaDao();

    public void salvaAgencia(Agencia agencia) throws CadastroExceptions {
        if (agencia.getCodigoAgencia() == null || agencia.getCodigoAgencia().isBlank()) {
            throw new CadastroExceptions("A agencia nao possui um codigo de agencia");

        }
        Agencia agenciaBusca = agenciaDao.buscarAgenciaPorCodigoAgencia(agencia.getCodigoAgencia());
        if (agenciaBusca != null) {
            throw new CadastroExceptions("o codigo de agencia ja esta cadastrado");

        }
        if (agencia.getCidade() == null || agencia.getCidade().isBlank()) {
            throw new CadastroExceptions("A cidade nao esta cadastrada !");

        }
        if (agencia.getUf() == null || agencia.getUf().isBlank()) {
            throw new CadastroExceptions("A UF nao esta cadastrada !");

        }

        agenciaDao.inserirAgencia(agencia);
    }

    public List<Agencia> buscarAgencias() {
        return agenciaDao.listarTodasAgencias();
    }

}
