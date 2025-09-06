import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Conta {
    private long nroConta;
    private double saldo;

    public Conta(long nroConta, double saldo) {
        this.nroConta = nroConta;
        this.saldo = saldo;
    }

    public long getNroConta() {
        return nroConta;
    }

    public double getSaldo() {
        return saldo;
    }

    @Override
    public String toString() {
        return "Número: " + nroConta + " - R$ " + saldo;
    }
}

class ContaDAO {
    private Connection conn;

    public ContaDAO() throws SQLException {
        String url = "jdbc:postgresql://aws-1-sa-east-1.pooler.supabase.com:6543/postgres?user=postgres.cyiketnrpsrrhfwdngee&password=Panico04";
        this.conn = DriverManager.getConnection(url);
    }

    public void inserir(Conta conta) throws SQLException {
        String sql = "INSERT INTO CONTAS (nro_conta, saldo) VALUES (?, ?)";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setLong(1, conta.getNroConta());
        stm.setDouble(2, conta.getSaldo());
        stm.executeUpdate();
        stm.close();
    }

    public List<Conta> listar() throws SQLException {
        List<Conta> contas = new ArrayList<>();
        String sql = "SELECT * FROM CONTAS";
        PreparedStatement stm = conn.prepareStatement(sql);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            contas.add(new Conta(rs.getLong("nro_conta"), rs.getDouble("saldo")));
        }
        rs.close();
        stm.close();
        return contas;
    }

    public Conta buscarPorNumero(long nroConta) throws SQLException {
        String sql = "SELECT * FROM CONTAS WHERE nro_conta = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setLong(1, nroConta);
        ResultSet rs = stm.executeQuery();
        Conta conta = null;
        if (rs.next()) {
            conta = new Conta(rs.getLong("nro_conta"), rs.getDouble("saldo"));
        }
        rs.close();
        stm.close();
        return conta;
    }

    public void atualizarSaldo(long nroConta, double novoSaldo) throws SQLException {
        String sql = "UPDATE CONTAS SET saldo = ? WHERE nro_conta = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setDouble(1, novoSaldo);
        stm.setLong(2, nroConta);
        stm.executeUpdate();
        stm.close();
    }

    public void deletar(long nroConta) throws SQLException {
        String sql = "DELETE FROM CONTAS WHERE nro_conta = ?";
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setLong(1, nroConta);
        stm.executeUpdate();
        stm.close();
    }

    public void fechar() throws SQLException {
        conn.close();
    }
}

public class App {
    public static void main(String[] args) {
        try {
            ContaDAO dao = new ContaDAO();

            System.out.println("=== INSERINDO CONTA ===");
            dao.inserir(new Conta(12345, 500.0));

            System.out.println("=== LISTANDO CONTAS ===");
            for (Conta c : dao.listar()) {
                System.out.println(c);
            }

            System.out.println("=== BUSCANDO CONTA ===");
            Conta conta = dao.buscarPorNumero(12345);
            System.out.println(conta != null ? conta : "Conta não encontrada");

            System.out.println("=== ATUALIZANDO SALDO ===");
            dao.atualizarSaldo(12345, 1000.0);

            System.out.println("=== LISTANDO CONTAS APÓS UPDATE ===");
            for (Conta c : dao.listar()) {
                System.out.println(c);
            }

            System.out.println("=== DELETANDO CONTA ===");
            dao.deletar(12345);

            System.out.println("=== LISTANDO CONTAS APÓS DELETE ===");
            for (Conta c : dao.listar()) {
                System.out.println(c);
            }

            dao.fechar();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
