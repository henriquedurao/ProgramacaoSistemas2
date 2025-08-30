public class CriterioPrecoMaximo implements CriterioBusca {
    private double precoMaximo;

    public CriterioPrecoMaximo(double precoMaximo) {
        this.precoMaximo = precoMaximo;
    }

    @Override
    public boolean testar(Produto p) {
        return p.getPreco() <= precoMaximo;
    }
}
