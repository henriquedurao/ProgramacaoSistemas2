import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://aws-1-sa-east-1.pooler.supabase.com:6543/postgres?user=postgres.cyiketnrpsrrhfwdngee&password=Panico04";
        ContaDao dao = new ContaDao(FabricaConexoes.getConnection(url));
        Scanner sc = new Scanner(System.in);


        int opcao;
        do {
            System.out.println("\n==== MENU ====");
            System.out.println("(1) Listar todas as contas");
            System.out.println("(2) Buscar uma conta específica pelo número");
            System.out.println("(3) Criar uma nova conta");
            System.out.println("(4) Alterar o saldo de uma conta");
            System.out.println("(5) Apagar uma conta");
            System.out.println("(0) Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // consumir quebra de linha

            switch (opcao) {
                case 1:
                    try {
                        List<Conta> contas = dao.lerTodas();
                        if (contas.isEmpty()) {
                            System.out.println("Nenhuma conta encontrada.");
                        } else {
                            contas.forEach(System.out::println);
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao listar contas: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Informe o número da conta: ");
                    long numeroBusca = sc.nextLong();
                    Conta contaBuscada = dao.buscarPeloNumero(numeroBusca);
                    if (contaBuscada != null) {
                        System.out.println(contaBuscada);
                    } else {
                        System.out.println("Conta não encontrada!");
                    }
                    break;

                case 3:
                    System.out.print("Informe o número da nova conta: ");
                    long numero = sc.nextLong();
                    System.out.print("Informe o saldo inicial: ");
                    BigDecimal saldo = sc.nextBigDecimal();
                    Conta novaConta = new Conta(numero, saldo);
                    if (dao.criar(novaConta)) {
                        System.out.println("Conta criada com sucesso!");
                    } else {
                        System.out.println("Falha ao criar conta!");
                    }
                    break;

                case 4:
                    System.out.print("Informe o número da conta a ser alterada: ");
                    long numeroAlt = sc.nextLong();
                    Conta contaAlt = dao.buscarPeloNumero(numeroAlt);
                    if (contaAlt != null) {
                        System.out.print("Informe o novo saldo: ");
                        BigDecimal novoSaldo = sc.nextBigDecimal();
                        contaAlt.setSaldo(novoSaldo);
                        if (dao.atualizar(contaAlt)) {
                            System.out.println("Conta atualizada com sucesso!");
                        } else {
                            System.out.println("Falha ao atualizar conta!");
                        }
                    } else {
                        System.out.println("Conta não encontrada!");
                    }
                    break;

                case 5:
                    System.out.print("Informe o número da conta a ser apagada: ");
                    long numeroDel = sc.nextLong();
                    Conta contaDel = dao.buscarPeloNumero(numeroDel);
                    if (contaDel != null) {
                        if (dao.apagar(contaDel)) {
                            System.out.println("Conta apagada com sucesso!");
                        } else {
                            System.out.println("Falha ao apagar conta!");
                        }
                    } else {
                        System.out.println("Conta não encontrada!");
                    }
                    break;

                case 0:
                    System.out.println("Saindo...");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);

        sc.close();
    }
}
