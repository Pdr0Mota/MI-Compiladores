package analyzers;

import util.Erro;
import util.Token;

public class Testes2 {

	public static void main(String[] args) {
		
		//usados na regex dos comentarios
		String simbolos = "\\#|\\$|\\%|\\?|\\@|\\^|\\~|\\|\\*|:|\\<|\\>|_|!|\\.";
		String simbolosOperadores = "\\+|\\-|\\=|\\&|\\||/|\\\\";
		String simbolosDelimitadores = "\\;|\\,|\\(|\\)|\\{|\\}|\\[|\\]";
		
		String lexema = "{ Testando cadeia de comentario ' \" ( ; )\nsaltar linha\nlinha com tabulacao\n<> + - * / \\ -\n;\n}";
		
		String lexema2 = "{ Testando cadeia de comentario ' \" ( ; )\nsaltar linha\nlinha com tabulacao\n}";
		
		if (lexema.matches("\\{[[a-zA-Z_0-9]| |\n|\t|\'|\"|"
				+ simbolos + "|" + simbolosDelimitadores + "|"
				+ simbolosOperadores + "]*\\}")) {

			System.out.println("Add como Comentario:");

		} else {

			System.out.println("Nao reconheceu o comentario: ");

		}
		
	}
}
