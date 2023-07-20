package br.com.chfb;

import br.com.chfb.domain.entity.Cliente;
import br.com.chfb.domain.repositorio.Clientes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class VendasApplication {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public CommandLineRunner init(@Autowired Clientes clientes) {
        return args -> {
            clientes.salvar(new Cliente("Cesar"));
            clientes.salvar(new Cliente("Alisson"));
            clientes.salvar(new Cliente("Marcos"));

            System.out.println("Listar todos: ");
            List<Cliente> lista = clientes.obterTodos();
            lista.forEach(System.out::println);

            System.out.println("Atualizando os clientes: ");
            lista.forEach(c -> {
                c.setNome(c.getNome().concat(" atualizado."));
                clientes.atualizar(c);
            });

            System.out.println("Retorna os clientes atualizados: ");
            List<Cliente> lista2 = clientes.obterTodos();
            lista2.forEach(System.out::println);

            System.out.println("Buscar por nome: ");
            clientes.buscarPorNome("Mar").forEach(System.out::println);

            lista2.forEach(c -> {
                clientes.deletar(c.getId());
            });

//            clientes.deletar(2);
        };
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "HelloWorld!";
    }

    @GetMapping("/appname")
    public String appName() {
        return this.applicationName;
    }

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
