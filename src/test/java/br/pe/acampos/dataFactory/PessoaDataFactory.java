package br.pe.acampos.dataFactory;

import br.pe.acampos.models.PessoalModel;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;

public class PessoaDataFactory {
    private static Faker faker = new Faker();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static PessoalModel pessoaComNomeEmBranco() {
        PessoalModel pessoaSemNome = novaPessoa();
        pessoaSemNome.setNome(StringUtils.EMPTY);

        return pessoaSemNome;
    }

    public static PessoalModel todasInformacoesAusentes() {
        PessoalModel pessoa = novaPessoa();
        pessoa.setNome(StringUtils.EMPTY);
        pessoa.setDataNascimento(StringUtils.EMPTY);
        pessoa.setCpf(StringUtils.EMPTY);
        pessoa.setEmail(StringUtils.EMPTY);

        return pessoa;
    }

    public static PessoalModel todasInformacoesNulas() {
        PessoalModel pessoa = novaPessoa();
        pessoa.setNome(null);
        pessoa.setDataNascimento(null);
        pessoa.setCpf(null);
        pessoa.setEmail(null);

        return pessoa;
    }

    private static PessoalModel novaPessoa() {
        PessoalModel pessoa = new PessoalModel();
        pessoa.setNome(faker.name().nameWithMiddle());
        pessoa.setDataNascimento(dateFormat.format(faker.date().birthday()));
        pessoa.setCpf(faker.cpf().valid(false));
        pessoa.setEmail(faker.internet().emailAddress());

        return pessoa;
    }
}
