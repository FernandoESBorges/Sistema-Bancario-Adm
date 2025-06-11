package com.unincor.sistema.bancario.admin.model.dao;

import com.unincor.sistema.bancario.admin.configurations.MySQL;
import com.unincor.sistema.bancario.admin.model.domain.Agencia;
import com.unincor.sistema.bancario.admin.model.domain.Gerente;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GerenteDao {

    public void inserirGerente(Gerente gerente) {
        String sql = "INSERT INTO gerentes(nome, cpf, data_nascimento, email, "
                + "telefone, senha_hash, agencia) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = MySQL.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, gerente.getNome());
            ps.setString(2, gerente.getCpf());
            ps.setDate(3, Date.valueOf(gerente.getDataNascimento()));
            ps.setString(4, gerente.getEmail());
            ps.setString(5, gerente.getTelefone());
            ps.setString(6, gerente.getSenhaHash());
            if (gerente.getAgencia() != null) {
                ps.setLong(7, gerente.getAgencia().getIdAgencia());
            } else {
                ps.setObject(7, null);
            }
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(GerenteDao.class.getName()).log(Level.SEVERE, "Erro ao inserir gerente", ex);
        }
    }

    public List<Gerente> buscarTodosGerentes() {
        List<Gerente> gerentes = new ArrayList<>();
        String sql = "SELECT * FROM gerentes";
        try (Connection con = MySQL.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                var gerente = construirGerenteSql(rs);
                gerentes.add(gerente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerenteDao.class.getName()).log(Level.SEVERE, "Erro ao buscar todos os gerentes", ex);
        }
        return gerentes;
    }

    public Gerente buscarGerentePorId(Long idGerente) {
        String sql = "SELECT * FROM gerentes WHERE id_gerente = ?";
        try (Connection con = MySQL.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, idGerente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return construirGerenteSql(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerenteDao.class.getName()).log(Level.SEVERE, "Erro ao buscar gerente por ID", ex);
        }
        return null;
    }

    public Gerente construirGerenteSql(ResultSet rs) throws SQLException {
        Gerente gerente = new Gerente();
        gerente.setIdGerente(rs.getLong("id_gerente"));
        gerente.setNome(rs.getString("nome"));
        gerente.setCpf(rs.getString("cpf"));
        gerente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
        gerente.setEmail(rs.getString("email"));
        gerente.setTelefone(rs.getString("telefone"));
        gerente.setSenhaHash(rs.getString("senha_hash"));
        Long idAgencia = rs.getLong("agencia");
    if (idAgencia != 0) { // 0 geralmente significa valor nulo em colunas do tipo Long
        Agencia agencia = new Agencia();
        agencia.setIdAgencia(idAgencia);
        gerente.setAgencia(agencia);
    } else {
        gerente.setAgencia(null);
    }
        return gerente;
    }

    public static void main(String[] args) {
        GerenteDao gerenteDao = new GerenteDao();

        // Exemplo de inserção (descomente se quiser testar)
        /*
        Gerente novoGerente = new Gerente(null, "Matheus Torremo", "21324623",
                LocalDate.now(), "torremo@unincor.edu.br", "45646124897",
                "389102312749128903dasda", "Agencia Central");
        gerenteDao.inserirGerente(novoGerente);
         */
        // Exemplo de buscar todos os gerentes
        var gerentes = gerenteDao.buscarTodosGerentes();
        gerentes.forEach(g -> System.out.println("Id: " + g.getIdGerente()
                + " Nome: " + g.getNome() + " Agência: " + g.getAgencia()));

        // Exemplo de buscar gerente por ID
        var gerente = gerenteDao.buscarGerentePorId(1L);
        if (gerente != null) {
            System.out.println("Id: " + gerente.getIdGerente()
                    + " Nome: " + gerente.getNome()
                    + " Agência: " + gerente.getAgencia());
        } else {
            System.out.println("Gerente não encontrado.");
        }
    }
}
