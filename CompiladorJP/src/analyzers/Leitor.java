package analyzers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Leitor {

	private String codigo = "";

	private char[] caracteres;
	private String caminho = "";

	public Leitor(String caminho) {
		this.caminho = caminho;

	}

	public void ler() {

		try {

			FileInputStream leitorDeArquivo;
			leitorDeArquivo = new FileInputStream(caminho);
			InputStreamReader ler_texto = new InputStreamReader(leitorDeArquivo);
			BufferedReader br = new BufferedReader(ler_texto);
			String linha = br.readLine();

			while (linha != null) {
				codigo = codigo + " " + linha + " \n";
				linha = br.readLine();
			}

			br.close();
			leitorDeArquivo.close();
			ler_texto.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void preparar() {
		codigo = addEspacosEntrada(codigo);
		caracteres = codigo.toCharArray();
	}

	public char[] getCaracteres() {
		return caracteres;
	}
	
	public void readAllFiles() {
		File arquivos[];
		File diretorio = new File("C:/Users/Pedro/Desktop/CompiladorJP/files");
		
		// Array com os diretórios de todos os arquivos
		arquivos = diretorio.listFiles();
		
		System.out.println("Numero de arquivos encontrados: " + arquivos.length);
		
		for(int i = 0; i < arquivos.length; i++){
			System.out.println(arquivos[i]);
		}
	}

	public String addEspacosEntrada(String textoEntrada) {

		String novaString = textoEntrada;
		
		// Seperando operadores duplos
		novaString = novaString.replaceAll("\\<\\>", " <> ");
		

		// Seperando operadores aritmeticos 
		novaString = novaString.replaceAll("\\+", " + ");

		novaString = novaString.replaceAll("\\-", " -");

		novaString = novaString.replaceAll("\\*", " * ");

		novaString = novaString.replaceAll("/", " / ");

		// Separando delimitadores

		novaString = novaString.replaceAll("\\;", " ; ");
		novaString = novaString.replaceAll("\\,", " , ");
		novaString = novaString.replaceAll("\\(", " ( ");
		novaString = novaString.replaceAll("\\)", " ) ");

		// Separando os caracters constantes

		novaString = novaString.replaceAll("' ([a-zA-Z_0-9]) '", " '$1' ");

		novaString = novaString.replaceAll("' *([a-zA-Z_0-9])", " '$1");

		// Separando as cadeias constantes

		novaString = novaString.replaceAll("(\"[a-zA-Z][[a-zA-Z_0-9]| ]*\")", " $1 ");
		
		// Separador de simbolos

		novaString = novaString.replaceAll("\\#", " # ");
		novaString = novaString.replaceAll("\\%", " % ");
		novaString = novaString.replaceAll("\\?", " ? ");
		novaString = novaString.replaceAll("\\@", " @ ");
		novaString = novaString.replaceAll("\\^", " ^ ");
		novaString = novaString.replaceAll("\\~", " ~ ");
		novaString = novaString.replaceAll("\\|", " | ");
		//novaString = novaString.replaceAll("(\\$)", " $1 ");
		novaString = novaString.replaceAll("\n", " \n");
		novaString = novaString.replaceAll("\t", " \t ");
		novaString = novaString.replaceAll("\\|", " | ");
		novaString = novaString.replaceAll("\\{", " {");
		novaString = novaString.replaceAll("\\}", "} ");
		
		System.out.println(novaString);

		return novaString;

	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
