package analyzers;

import java.io.File;

public class Teste {

	public static void main(String[] args) {
		
		
		String codigo = "programa funcao(((67.4 casa +++\"casa\"{casa de maria e azul )([]868765786} $";
		Leitor leitor = new Leitor("Aqui seria o caminho do arquivo");
		
		System.out.println("Arquivos: ");
		leitor.readAllFiles();
		
		//pula o metodo que le o arquivo
		leitor.setCodigo(codigo);
		leitor.preparar();
		
		AnalisadorLexico analisador = new AnalisadorLexico();
		analisador.analisarCodigo(leitor.getCaracteres());

	}

}
