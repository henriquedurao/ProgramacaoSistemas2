public class CriterioDescricao implements CriterioBusca {
    private String descricao;

    public CriterioDescricao(String descricao) {
        this.descricao = descricao.toLowerCase();
    }

    @Override
    public boolean testar(Produto p) {
        return p.getDescricao().toLowerCase().contains(descricao);
    }
}
