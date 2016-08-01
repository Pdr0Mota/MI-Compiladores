package analyzers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

public class Init {
	private boolean hasFiles = false;
	File arquivos[];
	String[] pathToSave;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Init n = new Init();
		n.readAllFiles("files");
		System.out.println("---------------------------------------------------");
		
		if (n.compilate()){
			System.out.println("---------------------------------------------------\n\r **Compila��o conclu�da**");
		} else {
			System.out.println("Falha na compila��o");
		}
	}
	
//	Fun��o de debug - Pode apagar p�s t�rmino
	public void printPaths(){
		if (arquivos == null){
			System.out.println("N�o existem arquivos no atributo.");
			return;
		}
		if (arquivos.length == 0){
			System.out.println("N�o existem arquivos no atributo.");
			return;
		}
		for (int i = 0; i < arquivos.length; i++){
			System.out.println(arquivos[i]);
		}
	}
	
	
	
//	rotina respons�vel por receber o caminho do 
	public void readAllFiles(String path) {
		
		File diretorio = new File(path);
		
		// Array com os diret�rios de todos os arquivos
		arquivos = diretorio.listFiles();
		
		System.out.println("Numero de arquivos encontrados: " + arquivos.length);
		if (arquivos.length > 0){
			hasFiles = true;
		}
		
	}
	
	public boolean compilate(){
		if (!hasFiles){
			JOptionPane.showMessageDialog(null, "N�o existem arquivos!");
			System.out.println("N�o existem arquivos");
			return false;
		} else {
//			iniciando leitura dos arquivos
			for (int i = 0; i < arquivos.length; i++){

				Leitor l = new Leitor(arquivos[i].getPath());
				l.ler();
				l.preparar();
				AnalisadorLexico n = new AnalisadorLexico();
				n.analisarCodigo(l.getCaracteres());

				n.gravarResultadoEmArquivo(arquivos[i].getName());		
				
			}
			printPaths();
			return true;
		}
	}

}
