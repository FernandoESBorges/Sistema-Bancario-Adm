package com.unincor.sistema.bancario.admin.model.dao;

import com.unincor.sistema.bancario.admin.configurations.MySQL;
import com.unincor.sistema.bancario.admin.model.domain.Cliente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ferna
 */
public class ClienteDao {

    public void inserirCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes(nome,cpf,data_nascimento,email,telefone,senha_hash)"
                + " VALUES (?,?,?,?,?,?)";
        try (Connection con = MySQL.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCpf());
            ps.setDate(3, Date.valueOf(cliente.getDataNascimento()));
            ps.setString(4, cliente.getEmail());
            ps.setString(5, cliente.getTelefone());
            ps.setString(6, cliente.getSenhaHash());
            ps.execute();

        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Cliente> buscarTodosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";
        try (Connection con = MySQL.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               var cliente = contruirClienteSql(rs);
              clientes.add(cliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clientes;
    }

    public Cliente buscarClientePorId(Long idCliente) {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        try (Connection con = MySQL.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, idCliente);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return contruirClienteSql(rs);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public Cliente contruirClienteSql(ResultSet rs) throws SQLException{
         Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getLong("id_cliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getNString("cpf"));
                cliente.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                cliente.setEmail(rs.getNString("email"));
                cliente.setTelefone(rs.getNString("telefone"));
                cliente.setSenhaHash(rs.getNString("senha_hash"));
                return cliente;
    
    }

    public static void main(String[] args) {
        //Cliente cliente = new Cliente(null, "Diogenes", "21324654", LocalDate.now(),
              //  "prof.diogenes.francisco@unincor.edu.br", "4564654897", "389102312749128903");
        ClienteDao clienteDao = new ClienteDao();
        //var clientes = clienteDao.buscarTodosClientes();
        //clientes.forEach(c -> System.out.println("Id: " + c.getIdCliente() + " Nome: " + c.getNome()
               // + " cpf: " + c.getCpf() + " Data Nascimento: " + c.getDataNascimento() + " email: " + c.getEmail()
               // + " telefone: " + c.getTelefone() + "Senha:" + c.getSenhaHash()));
        
        var c = clienteDao.buscarClientePorId(1l);
        System.out.println("ID= "+ c.getIdCliente() + " Nome= "+ c.getNome());
        
    }

}
