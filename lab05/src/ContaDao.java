import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class ContaDao implements IContaDao {
    private Connection conn;

    public ContaDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean criar(Conta c) {
        String sql = "INSERT INTO contas (nro_conta, saldo) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, c.getNumero());
            stmt.setBigDecimal(2, c.getSaldo());
            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao criar conta: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Conta> lerTodas() throws Exception {
        List<Conta> contas = new ArrayList<>();
        String sql = "SELECT nro_conta, saldo FROM contas";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                long numero = rs.getLong("nro_conta");
                BigDecimal saldo = rs.getBigDecimal("saldo");
                contas.add(new Conta(numero, saldo));
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao ler contas: " + e.getMessage(), e);
        }
        return contas;
    }

    @Override
    public Conta buscarPeloNumero(long id) {
        String sql = "SELECT nro_conta, saldo FROM contas WHERE nro_conta = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                long numero = rs.getLong("nro_conta");
                BigDecimal saldo = rs.getBigDecimal("saldo");
                return new Conta(numero, saldo);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar conta: " + e.getMessage());
        }
        return null;
    }

    @Override
    public boolean atualizar(Conta c) {
        String sql = "UPDATE contas SET saldo = ? WHERE nro_conta = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, c.getSaldo());
            stmt.setLong(2, c.getNumero());
            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar conta: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean apagar(Conta c) {
        String sql = "DELETE FROM contas WHERE nro_conta = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, c.getNumero());
            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao apagar conta: " + e.getMessage());
            return false;
        }
    }
}
