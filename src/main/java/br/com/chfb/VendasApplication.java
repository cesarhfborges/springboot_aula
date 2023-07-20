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
            clientes.save(new Cliente("Cesar"));
            clientes.save(new Cliente("Alisson"));
            clientes.save(new Cliente("Marcos"));

            System.out.println("Listar todos: ");
            List<Cliente> lista = clientes.findAll();
            lista.forEach(System.out::println);

            System.out.println("busca por nome: ");
            List<Cliente> busca = clientes.encontrarPorNome("Marcos");
            busca.forEach(System.out::println);

//            System.out.println("Atualizando os clientes: ");
//            lista.forEach(c -> {
//                c.setNome(c.getNome().concat(" atualizado."));
//                clientes.save(c);
//            });
//
//            System.out.println("Retorna os clientes atualizados: ");
//            List<Cliente> lista2 = clientes.findAll();
//            lista2.forEach(System.out::println);
//
//            System.out.println("Buscar clientes por nome: ");
//            clientes.findByNomeLike("Mar").forEach(System.out::println);
//
//            System.out.println("Deletando Clientes: ");
//            lista2.forEach(c -> {
//                clientes.delete(c);
//                System.out.println(c.getNome() + " deletado.");
//            });
//
//            List<Cliente> lista3 = clientes.findAll();
//            if (lista3.isEmpty()) {
//                System.out.println("Nenhum cliente encontrado.");
//            } else {
//                lista3.forEach(System.out::println);
//            }
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
