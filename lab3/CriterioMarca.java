public class CriterioMarca implements CriterioBusca {
    private String marca;

    public CriterioMarca(String marca) {
        this.marca = marca.toLowerCase();
    }

    @Override
    public boolean testar(Produto p) {
        return p.getMarca().toLowerCase().contains(marca);
    }
}
