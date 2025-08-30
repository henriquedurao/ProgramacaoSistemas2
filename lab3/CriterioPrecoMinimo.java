public class CriterioPrecoMinimo implements CriterioBusca {
    private double precoMinimo;

    public CriterioPrecoMinimo(double precoMinimo) {
        this.precoMinimo = precoMinimo;
    }

    @Override
    public boolean testar(Produto p) {
        return p.getPreco() >= precoMinimo;
    }
}
