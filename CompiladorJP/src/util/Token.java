package util;

//Classe que armazena informacoes sobre os tokens gerados
public class Token {
	private String tipo;
	private String lexema;
	private int linhaDeOcorrencia;
	private int colunaDeOcorrencia;

	public Token(String tipo, String lexema, int linha) {
		this.tipo = tipo;
		this.lexema = lexema;
		this.linhaDeOcorrencia = linha;
		//this.colunaDeOcorrencia = coluna;
	}

	public String toString() {
		return "Linha: " + linhaDeOcorrencia + " Coluna: " + colunaDeOcorrencia + "|" + tipo + " " + lexema + "\n";
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}

	public int getLinhaDeOcorrencia() {
		return linhaDeOcorrencia;
	}

	public int getColunaDeOcorrencia() {
		return colunaDeOcorrencia;
	}
	
	
}
