package br.pe.acampos.tests;

import br.pe.acampos.dataFactory.PessoaDataFactory;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class PessoaAPITest {

    private String token;

    @BeforeEach
    public void beforeEach() {

        baseURI = "http://vemser-dbc.dbccompany.com.br";
        port = 39000;
        basePath = "/vemser/dbc-pessoa-api";

        this.token =
                given()
                        .contentType(ContentType.JSON)
                        .body("""
                                {
                                "login": "admin",
                                "senha": "123"
                                }
                                """)
                .when()
                        .post("/auth")
                .then()
                        .extract().asString()
                ;
    }

    @Test
    @DisplayName("Nao deve cadastrar pessoa com nome em branco")
    public void testNaoDeveCadastrarPessoaComNomeEmBranco() {

        given()
                .contentType(ContentType.JSON)
                .body(PessoaDataFactory.pessoaComNomeEmBranco())
                .header("Authorization", token)
        .when()
                .post("/pessoa")
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", hasItem("nome: must not be blank"))
        ;
    }

    @Test
    @DisplayName("Nao deve cadastrar pessoa com informacoes ausentes")
    public void testNaoDeveCadastrarPessoaComInfoAusentes() {

        given()
                .contentType(ContentType.JSON)
                .body(PessoaDataFactory.todasInformacoesAusentes())
                .header("Authorization", token)
        .when()
                .post("/pessoa")
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", hasItem("nome: must not be blank"))
                .body("errors", hasItem("dataNascimento: must not be null"))
        ;
    }

    @Test
    @DisplayName("Nao deve cadastrar pessoa com informacoes nulas")
    public void testNaoDeveCadastrarPessoaComInfoNulas() {

        given()
                .contentType(ContentType.JSON)
                .body(PessoaDataFactory.todasInformacoesNulas())
                .header("Authorization", token)
        .when()
                .post("/pessoa")
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("errors", hasItem("nome: must not be blank"))
                .body("errors", hasItem("cpf: must not be null"))
                .body("errors", hasItem("dataNascimento: must not be null"))
        ;
    }
}
