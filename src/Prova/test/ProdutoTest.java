package Prova.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import Prova.ProdutoService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProdutoTest {

    @BeforeAll
    public static void inicializa(){
        ProdutoService.excluiTblProduto();
    }

    @Order(1)
    @Test
    public void leArquivoEInsereProduto(){
        ProdutoService.leArquivoEInsereProduto();
    }

    @Order(2)
    @Test
    public void fazPedidoProdutoTestOk(){
        assertEquals(1, ProdutoService.fazPedidoProduto(1, 10));
    }

    @Order(3)
    @Test
    public void fazPedidoProdutoTestFailEstoque(){
        assertEquals(-1, ProdutoService.fazPedidoProduto(2, 50));
    }

    @Order(4)
    @Test
    public void fazPedidoProdutoInexistenteTest(){
        assertEquals(-1, ProdutoService.fazPedidoProduto(999, 5));
    }

    @Order(5)
    @Test
    public void GravaArquivoTest(){
        ProdutoService.GravaArquivo();
    }
}
