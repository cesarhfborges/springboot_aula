package br.com.chfb.domain.repositorio;

import br.com.chfb.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Clientes {

    private static final String INSERT = "INSERT INTO CLIENTES (NOME) VALUES (?);";
    private static final String SELECT_ALL = "SELECT * FROM CLIENTES;";
    private static final String UPDATE = "UPDATE CLIENTES SET NOME = ? WHERE ID = ?;";
    private static final String DELETE = "DELETE FROM CLIENTES WHERE ID = ?;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Cliente salvar(Cliente cliente) {
        this.jdbcTemplate.update(INSERT, cliente.getNome());
        return cliente;
    }

    public Cliente atualizar(Cliente cliente) {
        this.jdbcTemplate.update(UPDATE, cliente.getNome(),
                cliente.getId());
        return cliente;
    }

    public void deletar(Cliente cliente) {
        deletar(cliente.getId());
    }

    public void deletar(Integer id) {
        this.jdbcTemplate.update(DELETE, id);
    }

    public List<Cliente> buscarPorNome(String nome) {
        return this.jdbcTemplate.query(
                SELECT_ALL.replace(";", "").concat(" WHERE NOME LIKE ?;"),
                new Object[]{"%".concat(nome).concat("%") },
                getClienteRowMapper()
        );
    }

    public List<Cliente> obterTodos() {
        return this.jdbcTemplate.query(SELECT_ALL, getClienteRowMapper());
    }

    private static RowMapper<Cliente> getClienteRowMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                Integer id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                return new Cliente(id, nome);
            }
        };
    }
}
