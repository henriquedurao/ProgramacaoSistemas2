public class Produto {
    private String descricao;
    private String marca;
    private double preco;

    public Produto(String descricao, String marca, double preco) {
        this.descricao = descricao;
        this.marca = marca;
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getMarca() {
        return marca;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return descricao + " - " + marca + " - R$ " + preco;
    }
}
