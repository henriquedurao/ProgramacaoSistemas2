package ps2.titular_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import static ps2.titular_app.ES.*;

@SpringBootApplication
public class TitularAppApplication implements CommandLineRunner {

    @Autowired
    private TitularDao titularDao;

    public static void main(String[] args) {
        SpringApplication.run(TitularAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("# GERENCIADOR DE TITULARES!");
        boolean sair = false;
        String menu = "\n(1) Listar todos os titulares" +
                      "\n(2) Buscar titular por ID" +
                      "\n(3) Criar novo titular" +
                      "\n(4) Alterar dados do titular" +
                      "\n(5) Apagar titular" +
                      "\n(0) Sair \nEscolha uma opção: ";

        while (!sair) {
            String op = input(menu);
            switch (op) {
                case "1": // listar todos
                    for (Titular t : titularDao.listarTodos()) {
                        print(t.toString());
                    }
                    break;

                case "2": // buscar por id
                    Long idBusca = Long.parseLong(input("ID do titular: "));
                    Titular tBusca = titularDao.buscarPorId(idBusca);
                    if (tBusca != null)
                        print(tBusca.toString());
                    else
                        print("Titular não encontrado!");
                    break;

                case "3": // criar
                    String nome = input("Nome: ");
                    String cpf = input("CPF: ");
                    Titular novo = new Titular();
                    novo.setNome(nome);
                    novo.setCpf(cpf);
                    titularDao.salvar(novo);
                    print("Titular criado com ID " + novo.getId());
                    break;

                case "4": // alterar
                    Long idAlt = Long.parseLong(input("ID do titular a alterar: "));
                    String novoNome = input("Novo nome: ");
                    String novoCpf = input("Novo CPF: ");
                    Titular alterado = titularDao.alterar(idAlt, novoNome, novoCpf);
                    if (alterado != null)
                        print("Titular alterado: " + alterado);
                    else
                        print("Titular não encontrado!");
                    break;

                case "5": // apagar
                    Long idDel = Long.parseLong(input("ID do titular a apagar: "));
                    boolean apagou = titularDao.apagar(idDel);
                    if (apagou)
                        print("Titular apagado com sucesso!");
                    else
                        print("Titular não encontrado!");
                    break;

                case "0":
                    sair = true;
                    break;

                default:
                    print("Opção inválida!");
            }
        }
    }
}
