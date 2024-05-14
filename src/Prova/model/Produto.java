package Prova.model;

public class Produto {
    
    private int id;
    private String nome;
    private double valor;
    private int estoque;

    public Produto(int id, String nome, double valor, int estoque) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.estoque = estoque;
    }
    
    public Produto() {
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getEstoque() {
        return this.estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }


    @Override
    public String toString() {
        return getId() + "\n" +
            "nome: " + getNome() + "\n" +
            "valor: R$" + getValor() + "\n" +
            "Qtd estoque: " + getEstoque();
    }
}
