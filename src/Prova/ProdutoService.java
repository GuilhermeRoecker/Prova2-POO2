package Prova;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.PrimitiveIterator;

import Prova.model.Produto;

public class ProdutoService {
    
    public static void leArquivoEInsereProduto() {
        try {
            
            FileReader fr = new FileReader("produtos.txt");
            BufferedReader br = new BufferedReader(fr);
            String linha;
            while ((linha = br.readLine()) != null) {
    
                String[] partes = linha.split(",");
                if(partes.length < 4) {
                    System.out.println("Falta de informações na linha: " + linha);
                    continue;
                }
    
                int id;
                try {
                    id = Integer.parseInt(partes[0].trim());
                } catch(NumberFormatException e) {
                    System.out.println("ID do produto inválido: " + partes[0]);
                    continue;
                }
    
                String nome = partes[1];
                if(nome == null || nome.trim().isEmpty()) {
                    System.out.println("Nome do produto ausente: " + linha);
                    continue;
                }
    
                double valor;
                try {
                    valor = Double.parseDouble(partes[2].trim());
                } catch(NumberFormatException e) {
                    System.out.println("Valor do produto inválido: " + partes[2]);
                    continue;
                }
    
                int estoque;
                try {
                    estoque = Integer.parseInt(partes[3].trim());
                    if(estoque <= 0) {
                        System.out.println("Estoque do produto inválido: " + partes[3]);
                        continue;
                    }
                } catch(NumberFormatException e) {
                    System.out.println("Estoque do produto inválido: " + partes[3]);
                    continue;
                }
    
                Produto produto = new Produto(id, nome, valor, estoque);
                cadastraProduto(produto);
            }
    
            br.close();
            fr.close();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public static int cadastraProduto(Produto p){
        try {
            Connection conn = Conexao.conectaMySql();
    
            PreparedStatement pr = conn.prepareStatement("Insert into prova2.produto (id,nome, valor, estoque) values (?,?,?,?)");
            pr.setInt(1, p.getId());
            pr.setString(2, p.getNome());
            pr.setDouble(3, p.getValor());
            pr.setInt(4, p.getEstoque());
            int total =  pr.executeUpdate();
            conn.close();
            return total;
    
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int fazPedidoProduto(int id, int quantidade){
        try {
            Connection conn = Conexao.conectaMySql();
    
            PreparedStatement pr = conn.prepareStatement("Select * from prova2.produto where id=?");
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if(rs.next()){
                int qtdEstoque = rs.getInt("estoque");
                double valor = rs.getDouble("valor");
                if (qtdEstoque >= quantidade) {
                    pr = conn.prepareStatement("update prova2.produto set estoque=? where id=?");
                    pr.setInt(1, qtdEstoque - quantidade); 
                    pr.setInt(2, id);
                    pr.executeUpdate();
                    double valorTotal = valor * quantidade;
                    System.out.println("O valor total da compra é de: R$ " + valorTotal);
                    conn.close();
                    return 1;
                } else {
                    System.out.println("Estoque insuficiente do produto id: " + id);
                }
            } else {
                System.out.println("Produto não encontrado do id: " + id);
            }
    
            conn.close();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    

    public static int excluiTblProduto(){
        try {

            Connection conn = Conexao.conectaMySql();
            String sql = "delete from prova2.produto where id > 0";
            PreparedStatement pr = conn.prepareStatement(sql);
            int total = pr.executeUpdate();
            sql = "Alter table produto auto_increment = 0";
            pr = conn.prepareStatement(sql);
            pr.executeUpdate();
            conn.close();
            return total;
            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static ArrayList<Produto> listAll(){
        ArrayList<Produto> lista = new ArrayList<Produto>();
        try {
            String sql = "select * from produto";
            Connection conn = Conexao.conectaMySql();
            PreparedStatement pr = conn.prepareStatement(sql);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor(rs.getDouble("valor"));
                p.setEstoque(rs.getInt("estoque"));
                lista.add(p);
            }
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static Produto findById(int id){
        Produto p = new Produto();
        try {
            Connection conn = Conexao.conectaMySql();
            PreparedStatement pr = conn.prepareStatement("select * from produto where id=?");
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setValor(rs.getDouble("valor"));
                p.setEstoque(rs.getInt("estoque"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }
    
    public static String GravaArquivo(){
        try {

            FileWriter fw = new FileWriter("produtos.txt", false);
            BufferedWriter bw = new BufferedWriter(fw);

            Connection conn = Conexao.conectaMySql();
            PreparedStatement pr = conn.prepareStatement("Select * from produto");
            ResultSet rs = pr.executeQuery();
            while(rs.next()){
                String Produto = rs.getInt("id")+ "," + rs.getString("Nome")+ "," + rs.getDouble("valor")+ "," + rs.getInt("estoque"); 
                bw.append(Produto);
                bw.newLine();

            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Falhou";
    }
}
