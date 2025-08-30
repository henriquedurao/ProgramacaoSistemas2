import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    private static List<Produto> produtos = new ArrayList<>();

    public static void main(String[] args) {
        inicializarProdutos();
        Scanner sc = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\nMenu:");
            System.out.println("(1) Listar produtos");
            System.out.println("(2) Pesquisar descrição");
            System.out.println("(3) Pesquisar marca");
            System.out.println("(4) Pesquisar pelo preço máximo");
            System.out.println("(5) Pesquisar pelo preço mínimo");
            System.out.println("(0) Sair");
            System.out.print("Escolha uma opção: ");
            opcao = sc.nextInt();
            sc.nextLine(); // consumir quebra de linha

            switch (opcao) {
                case 1:
                    listarProdutos();
                    break;
                case 2:
                    System.out.print("Digite a descrição: ");
                    String desc = sc.nextLine();
                    listarProdutos(new CriterioDescricao(desc));
                    break;
                case 3:
                    System.out.print("Digite a marca: ");
                    String marca = sc.nextLine();
                    listarProdutos(new CriterioMarca(marca));
                    break;
                case 4:
                    System.out.print("Digite o preço máximo: ");
                    double precoMax = sc.nextDouble();
                    listarProdutos(new CriterioPrecoMaximo(precoMax));
                    break;
                case 5:
                    System.out.print("Digite o preço mínimo: ");
                    double precoMin = sc.nextDouble();
                    listarProdutos(new CriterioPrecoMinimo(precoMin));
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);

        sc.close();
    }

    private static void listarProdutos() {
        System.out.println("\nLista de produtos:");
        for (Produto p : produtos) {
            System.out.println(p);
        }
    }

    private static void listarProdutos(CriterioBusca criterio) {
        System.out.println("\nResultados da busca:");
        boolean encontrou = false;
        for (Produto p : produtos) {
            if (criterio.testar(p)) {
                System.out.println(p);
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhum produto encontrado.");
        }
    }

    private static void inicializarProdutos() {
        produtos.add(new Produto("Notebook Dell", "Dell", 3500.00));
        produtos.add(new Produto("Smartphone Samsung", "Samsung", 2500.00));
        produtos.add(new Produto("Smartphone Xiaomi", "Xiaomi", 1500.00));
        produtos.add(new Produto("Tablet Lenovo", "Lenovo", 1200.00));
        produtos.add(new Produto("Monitor LG", "LG", 900.00));
    }
}
