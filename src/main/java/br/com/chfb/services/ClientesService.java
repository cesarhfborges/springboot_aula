package br.com.chfb.services;


import br.com.chfb.models.Cliente;
import br.com.chfb.repository.ClientesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientesService {

    private final ClientesRepository repository;

    @Autowired
    public ClientesService(ClientesRepository repository) {
        this.repository = repository;
    }

    public void salvarCliente(Cliente cliente) {
        this.validarCliente(cliente);
        this.repository.persistir(cliente);
        // ClientesRepository cl = new ClientesRepository();
        // cl.persistir(cliente);
    }

    public void validarCliente(Cliente cliente) {
        // aplica validações
    }
}
