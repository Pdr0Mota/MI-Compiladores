package util;

//Classe que armazena informacoes sobre os erros gerados
public class Erro {

	private String tipo;
	private String lexema;
	private int linhaDeOcorrencia;
	private int posicao;

	public Erro(String tipo, String lexema, int linha, int position) {
		this.tipo = tipo;
		this.lexema = lexema;
		this.linhaDeOcorrencia = linha;
		posicao = position;
	}

	public String toString() {

		return "Linha: " + linhaDeOcorrencia + " Posição: " + posicao + "|" + tipo + " " + lexema + "\n";

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

	public int getPosicao() {
		return posicao;
	}

}
