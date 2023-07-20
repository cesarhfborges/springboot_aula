package br.com.chfb.domain.repositorio;

import br.com.chfb.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class Clientes {

    private static final String INSERT = "INSERT INTO CLIENTES (NOME) VALUES (?);";
    private static final String SELECT_ALL = "SELECT * FROM CLIENTES;";
    private static final String UPDATE = "UPDATE CLIENTES SET NOME = ? WHERE ID = ?;";
    private static final String DELETE = "DELETE FROM CLIENTES WHERE ID = ?;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

//    private static RowMapper<Cliente> getClienteRowMapper() {
//        return new RowMapper<Cliente>() {
//            @Override
//            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
//                Integer id = resultSet.getInt("id");
//                String nome = resultSet.getString("nome");
//                return new Cliente(id, nome);
//            }
//        };
//    }

    @Transactional
    public Cliente salvar(Cliente cliente) {
        this.entityManager.persist(cliente);
//        this.jdbcTemplate.update(INSERT, cliente.getNome());
//        return cliente;
        return cliente;
    }

    @Transactional
    public Cliente atualizar(Cliente cliente) {
        this.entityManager.merge(cliente);
//        this.jdbcTemplate.update(UPDATE, cliente.getNome(),
//                cliente.getId());
        return cliente;
    }

    @Transactional
    public void deletar(Cliente cliente) {
        if (!this.entityManager.contains(cliente)) {
            cliente = entityManager.merge(cliente);
        }
        this.entityManager.remove(cliente);
    }

    @Transactional
    public void deletar(Integer id) {
        Cliente cliente = this.entityManager.find(Cliente.class, id);
        this.deletar(cliente);
    }

    @Transactional(readOnly = true)
    public List<Cliente> buscarPorNome(String nome) {
//        return this.jdbcTemplate.query(
//                SELECT_ALL.replace(";", "").concat(" WHERE NOME LIKE ?;"),
//                new Object[]{"%".concat(nome).concat("%")},
//                getClienteRowMapper()
//        );
        String jpql = " select c from Cliente c where c.nome like :nome ";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%".concat(nome).concat("%"));
        return query.getResultList();
    }

    @Transactional
    public List<Cliente> obterTodos() {
//        return this.jdbcTemplate.query(SELECT_ALL, getClienteRowMapper());
        return this.entityManager.createQuery("from Cliente", Cliente.class).getResultList();
    }
}
