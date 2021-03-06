package analyzers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import util.Erro;
import util.Token;

public class AnalisadorLexico {

	private ArrayList<Token> tokens = new ArrayList<Token>();
	private ArrayList<Erro> erros = new ArrayList<Erro>();

	private ArrayList<String> palavrasReservadas = new ArrayList<String>();
	private ArrayList<String> operadoresLogicos = new ArrayList<String>();
	private ArrayList<String> operadoresRelacionais = new ArrayList<String>();
	private ArrayList<String> operadoresAritmeticos = new ArrayList<String>();
	private ArrayList<String> delimitadores = new ArrayList<String>();

	// Difinindo quais sao os caracteres delimitadores de token do modo
	private char delimitadorTokens1 = ' ';
	private char delimitadorTokens2 = ' ';
	private char delimitadorTokens3 = ' ';
	private char delimitadorTokens4 = ' ';

	// Modos
	private final int ELEMENTOSAVULSOS = 1;
	private final int CADEIACONSTANTE = 2;
	private final int COMENTARIO = 3;
	private final int CARACTERCONSTANTE = 4;

	private int modo;
	private int contadorLinha = 1;
	private int contadorLexema = 1;

	private char[] caracteres;

	public void analisarCodigo(char[] caracteresParam) {

		this.caracteres = caracteresParam;

		carregarListas();

		String lexema = "";

		// Definindo 1 dos 4 modos para o programa iniciar

		modoInicio();

		// Percorrendo o array de caracteres
		for (int i = 0; i < caracteres.length; i++) {

			if (caracteres[i] == '\n') {
				contadorLinha++;
				contadorLexema = 1;
			}

			if (caracteres[i] != delimitadorTokens1 && caracteres[i] != delimitadorTokens2
					&& caracteres[i] != delimitadorTokens3 && caracteres[i] != delimitadorTokens4) {

				lexema = lexema + caracteres[i];
//				System.out.println("Lexema atual: " + lexema);

			} else if (!lexema.equals(" ") && !lexema.equals("") && !lexema.equals("\n") && !lexema.equals("\t")) {

				switch (modo) {

				case ELEMENTOSAVULSOS:

					lexema = lexema.replaceAll(" ", "");
					lexema = lexema.replaceAll("\n", "");
					lexema = lexema.replaceAll("\t", "");

					if (palavrasReservadas.contains(lexema)) {

						Token temp = new Token("Palavra Reservada", lexema, contadorLinha, contadorLexema);
						contadorLexema++;
						tokens.add(temp);

//						System.out.println("Add como palavra reservada: " + lexema);

					} else if (delimitadores.contains(lexema)) {

						Token temp = new Token("Delimitador", lexema, contadorLinha, contadorLexema);
						contadorLexema++;
						tokens.add(temp);

//						System.out.println("Add como Delimitador: " + lexema);

					} else if (operadoresAritmeticos.contains(lexema)) {

						Token temp = new Token("Operador Aritmetico", lexema, contadorLinha, contadorLexema);
						contadorLexema++;
						tokens.add(temp);

//						System.out.println("Add como Operador Aritmetico: " + lexema);

					} else if (operadoresRelacionais.contains(lexema)) {

						Token temp = new Token("Operador Relacional", lexema, contadorLinha, contadorLexema);
						contadorLexema++;
						tokens.add(temp);

//						System.out.println("Add como Operador Relacional: " + lexema);

					} else if (operadoresLogicos.contains(lexema)) {

						Token temp = new Token("Operador Logico", lexema, contadorLinha, contadorLexema);
						contadorLexema++;
						tokens.add(temp);

//						System.out.println("Add como Operador Logico: " + lexema);

					} else if (lexema.matches("[a-zA-Z][[a-zA-Z_0-9]|_]*")) {

						Token temp = new Token("Identificador", lexema, contadorLinha, contadorLexema);
						contadorLexema++;
						tokens.add(temp);

//						System.out.println("Add como Identificador: " + lexema);

					} else if (lexema.matches("-?[\\d]+|-?[\\d]+\\.[\\d]+")) {

						Token temp = new Token("Numero", lexema, contadorLinha, contadorLexema);
						contadorLexema++;
						tokens.add(temp);

//						System.out.println("Add como Numero: " + lexema);

					} else if (!lexema.equals("") && !lexema.equals(" ") && !lexema.equals("\n")){

						identificarErroElementosAvulsos(lexema, contadorLinha, contadorLexema);
						contadorLexema++;
						
//						System.out.println("Erro");
					}

					break;

				case CADEIACONSTANTE:

					lexema = "\"" + lexema + caracteres[i];

					if (lexema.matches("\"[a-zA-Z][[a-zA-Z_0-9]| ]*\"")) {

						Token temp = new Token("Cadeia Constante", lexema, contadorLinha, contadorLexema);
						contadorLexema++;
						tokens.add(temp);

//						System.out.println("Add como Cadeia Constante: " + lexema);

					} else {

						erros.add(new Erro("Cadeia constante mal formada.", lexema, contadorLinha, contadorLexema));
						contadorLexema++;

					}

					delimitadorTokens1 = ' ';
					modo = ELEMENTOSAVULSOS;
					break;

				case COMENTARIO:
					
				

					lexema = lexema + caracteres[i];
					
					lexema = lexema.replaceAll("\n", "");
					lexema = lexema.replaceAll("\t", "");
					
					//usados na regex dos comentarios
					String simbolos = "\\#|\\$|\\%|\\?|\\@|\\^|\\~|\\|\\*|:|\\<|\\>|_|!|\\.";
					String simbolosOperadores = "\\+|\\-|\\=|\\&|\\||/|\\\\";
					String simbolosDelimitadores = "\\;|\\,|\\(|\\)|\\{|\\}|\\[|\\]";

					if (lexema.matches("\\{[[a-zA-Z_0-9]| |\n|\t|\\'|\"|"
							+ simbolos + "|" + simbolosDelimitadores + "|"
							+ simbolosOperadores + "]*\\}")) {

						Token temp = new Token("Comentario", lexema, contadorLinha, contadorLexema);
						contadorLexema++;
						tokens.add(temp);

//						System.out.println("Add como Comentario: " + lexema);

					} else {

//						System.out.println("Nao reconheceu o comentario: " + lexema);
						erros.add(new Erro("Comentario mal formado.", lexema, contadorLinha, contadorLexema));
						contadorLexema++;

					}

					delimitadorTokens1 = ' ';
					modo = ELEMENTOSAVULSOS;
					break;

				case CARACTERCONSTANTE:

					lexema = "'" + lexema + caracteres[i];

					if (lexema.matches("\'[a-zA-Z_0-9]\'")) {

						Token temp = new Token("Caracter constante", lexema, contadorLinha, contadorLexema);
						contadorLexema++;
						tokens.add(temp);

//						System.out.println("Add como Caracter constante: " + lexema);

					} else {

						erros.add(new Erro("Caracter constante mal formado.", lexema, contadorLinha, contadorLexema));
						contadorLexema++;

					}

					delimitadorTokens1 = ' ';
					modo = ELEMENTOSAVULSOS;
					break;

				}// Fim do switch

				lexema = "";

			}

			// Verifica qual deve ser o proximo modo

			if (i + 1 < caracteres.length && caracteres[i + 1] == '\'' && modo!=COMENTARIO) {

				delimitadorTokens1 = '\'';
				delimitadorTokens2 = ' ';
				delimitadorTokens3 = '\n';
				delimitadorTokens4 = '$'; // escolher caracter de fim de codigo
				modo = CARACTERCONSTANTE;

			} else if (i + 1 < caracteres.length && caracteres[i + 1] == '"' && modo!=COMENTARIO) {

				delimitadorTokens1 = '"';
				delimitadorTokens2 = '\n';
				delimitadorTokens3 = '\n';
				delimitadorTokens4 = '$'; // escolher caracter de fim de codigo
				modo = CADEIACONSTANTE;

			} else if (i + 1 < caracteres.length && caracteres[i + 1] == '{' && modo!=COMENTARIO) {

				delimitadorTokens1 = '}';
				delimitadorTokens2 = '}';
				delimitadorTokens3 = '}';
				delimitadorTokens4 = '$'; // escolher caracter de fim de codigo
				modo = COMENTARIO;

			}

		} // Fim do percorrimento do array


		
		

	}
	
	
	public void printLexemas(){
		  for (int i = 0; i < tokens.size() ; i++){
		   System.out.println("LEXEMA Token: " + tokens.get(i).getLexema() + " - TIPO: " + tokens.get(i).getTipo());
		  }
		  for (int i = 0; i < tokens.size() ; i++){
		   System.out.println("LEXEMA Erro: " + erros.get(i).getLexema()  + " - TIPO: " + erros.get(i).getTipo());
		  }
		 }
		
	
	public void gravarResultadoEmArquivo(String fileName) {
//		primeira a��o, gravar os acertos
		Path p = Paths.get("results/Resultado de " + fileName);
		String acertos = "";
		String erros = "";
		for (int i = 0; i < tokens.size(); i++){
			acertos += "LEXEMA: \"" + tokens.get(i).getLexema() + "\" do Tipo: " + tokens.get(i).getTipo() + ". � o " + tokens.get(i).getPosicao()+ "� lexema da linha " + tokens.get(i).getLinhaDeOcorrencia() + "\r\n";
		}
		for (int i = 0; i < this.erros.size(); i++){
			erros += "LEXEMA: \"" + this.erros.get(i).getLexema() + "\" do Tipo: " + this.erros.get(i).getTipo() + ". � o " + this.erros.get(i).getPosicao()+ "� lexema da linha " + this.erros.get(i).getLinhaDeOcorrencia() + "\r\n";
		}
		String separator = "\r\n **N�o foram encontrados erros**";
		if (this.erros.size() != 0)
			separator = "\r\n --- ERROS --- \r\n";
		
		String final1 = acertos + separator + erros;
		try {
			Files.write(p, final1.getBytes());
//			System.out.println("Gravado!");
		}
		catch (Exception e) {
			System.out.println("Falha ao escrever: " + e);
		}
	}
	
	public void printTokensErros(){
		System.out.println("Quantidade de tokens: " + tokens.size());
		System.out.println("Quantidade de erros: " + erros.size());
	}

	private void identificarErroElementosAvulsos(String lexema, int linha, int posicao) {
		
		if (lexema.matches("-?[\\d]+[\\.]+")||lexema.matches("-?[\\.]+[0-9]+[a-zA-Z]*")||lexema.matches("-?[\\.]+[0-9]+[a-zA-Z]+")||lexema.matches("-?[0-9]+[[a-zA-Z_0-9]|_]*")) {

			erros.add(new Erro("Numero mal formado", lexema, linha, posicao));

		} else if (lexema.matches("[_]*[[a-zA-Z_0-9]|_|\\.]*")) {

			erros.add(new Erro("Identificador mal formado", lexema, linha, posicao));

		}  else if (!lexema.equals(" ") && !lexema.equals("") && !lexema.equals("\n") && !lexema.equals("\t")){
		
			//Lexemas formados por simbolos estranhos como $$$ %#%%, nao se sabe se � n�mero, id ou etc.
			erros.add(new Erro("Lexema mal formado", lexema, linha, posicao));
			
		}
	}

	

	public void carregarListas() {

		// Palavras reservadas
		palavrasReservadas.add("programa");
		palavrasReservadas.add("const");
		palavrasReservadas.add("var");
		palavrasReservadas.add("funcao");
		palavrasReservadas.add("inicio");
		palavrasReservadas.add("fim");
		palavrasReservadas.add("se");
		palavrasReservadas.add("entao");
		palavrasReservadas.add("senao");
		palavrasReservadas.add("enquanto");
		palavrasReservadas.add("faca");
		palavrasReservadas.add("leia");
		palavrasReservadas.add("escreva");
		palavrasReservadas.add("inteiro");
		palavrasReservadas.add("real");
		palavrasReservadas.add("booleano");
		palavrasReservadas.add("verdadeiro");
		palavrasReservadas.add("falso");
		palavrasReservadas.add("cadeia");
		palavrasReservadas.add("caractere");

		// Operadores logicos
		operadoresLogicos.add("nao");
		operadoresLogicos.add("e");
		operadoresLogicos.add("ou");

		// Operadores relacionais
		operadoresRelacionais.add("<>");
		operadoresRelacionais.add("=");
		operadoresRelacionais.add("<");
		operadoresRelacionais.add("<=");
		operadoresRelacionais.add(">");
		operadoresRelacionais.add(">=");

		// Operadores aritmeticos
		operadoresAritmeticos.add("+");
		operadoresAritmeticos.add("-");
		operadoresAritmeticos.add("*");
		operadoresAritmeticos.add("/");

		// Delimitadores
		delimitadores.add(";");
		delimitadores.add(",");
		delimitadores.add("(");
		delimitadores.add(")");
		delimitadores.add("}");

	}

	public void modoInicio() {

		// Define em qual modo o analisador vai iniciar.

		if (caracteres[0] == '\'') {

			delimitadorTokens1 = '\'';
			delimitadorTokens2 = ' '; // Verificar se espa�o delimita
			delimitadorTokens3 = '\n';
			delimitadorTokens4 = '\t';
			modo = CARACTERCONSTANTE;

		} else if (caracteres[0] == '"') {

			delimitadorTokens1 = '"';
			delimitadorTokens2 = '\t';
			delimitadorTokens3 = ' '; // escolher carater para o fim do programa
			delimitadorTokens4 = '\n';
			modo = CADEIACONSTANTE;

		} else if (caracteres[0] == '{') {

			delimitadorTokens1 = '}';
			delimitadorTokens2 = '}';
			delimitadorTokens3 = '\n';
			delimitadorTokens4 = ' ';// escolher carater para o fim do programa
			modo = COMENTARIO;

		} else {

			delimitadorTokens1 = ' ';
			delimitadorTokens2 = ' ';
			delimitadorTokens3 = ' ';
			delimitadorTokens4 = ' ';
			modo = ELEMENTOSAVULSOS;

		}
	}
}
