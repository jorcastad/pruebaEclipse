package pruebaGit;

import java.util.Scanner;

public class version2 {

static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		int tamañoTablero = 5;
		String[][] tablero = new String[tamañoTablero][tamañoTablero];
		
		rellenarArray(tablero);
		partida(tablero);
	}
	
	/*
	 * recibe como parametro un array bidimensional de String
	 * cambia las casillas del array por numeros (String) del 1 al numero maximo en funcion del tamaño del array bidimensional
	 */
	static void rellenarArray(String[][] tablero) {
		int digito = 1;
		
		for(int i = 0; i < tablero.length; i++) {
			for(int j = 0; j < tablero[i].length; j++) {
				tablero[i][j] = digito + "";
				digito++;
			}
		}
	}
	
	/*
	 * recibe como parametro un array bidimensional String
	 * muestra por pantalla el tablero del 3 en ralla
	 */
	static void mostrarArray(String[][] tablero) {
		int espacios = calcularCifras(tablero.length);
		
		for(int i = 0; i < tablero.length; i++) {
			for(int j = 0; j < tablero[i].length; j++) {
				System.out.print(tablero[i][j] + " ".repeat(espacios - (tablero[i][j].length() -1)));
			}
			System.out.println();
		}
	}
	
	/*
	 * recibe como parametro un numero entero (en este caso sera el length del array tablero)
	 * calcula el numero de cifras que va atener la ultima casilla del tablero
	 */
	static int calcularCifras(int numero) {
		numero*=numero;
		int cifras = 1;
		
		while(numero / 10 > 0) {
			cifras++;
			numero/=10;
		}
		return cifras;
	}

	/*
	 * recibe comoparametro un array bididmensional de String
	 * pregunta al usuario en que casilla quiere colocar la x
	 * si la casilla esta libre coloca una x si no, vuelve a preguntar al usuario
	 */
	static boolean jugadaUsuario(String[][] tablero, int[] filaColumna) {
		int respuesta;
		boolean correcto = false, ganador;
		int fila = 0, columna = 0;
		
		do{
			mostrarArray(tablero);
			System.out.println("\nEn que casilla quieres colocar una X?");
			respuesta = comprobarRespuesta("Introduce un numero");
			
			if(respuesta > 0 && respuesta <= tablero.length * tablero[0].length) {
				fila = respuesta/tablero.length;
				columna = respuesta%tablero.length - 1;
				if(respuesta%tablero.length == 0) {
					fila--;
					columna = tablero.length - 1;
				}
				if(tablero[fila][columna] != "X" && tablero[fila][columna] != "O") {
					tablero[fila][columna] = "X";
					correcto = true;
				}
			}	
			if(correcto == false)
				System.out.println("La casilla no existe o ya ha sido ocupada");
		}while(correcto == false);
		
		filaColumna[0] = fila;
		filaColumna[1] = columna;
		ganador = comprobarGanador(tablero, fila, columna, "X");
		
		return ganador;

	}

	/*
	 * recibe como parametro un array bidimensional de String
	 * cambia por un O la primera casilla libre sacada de forma aleatoria
	 */
	static boolean jugadaMaquina(String[][] tablero, int fila, int columna) {
		boolean ganador;
		
		mostrarArray(tablero);
		System.out.println("\nJugada de la maquina");
		
		if(comprobarColumna(tablero, columna) == true) 
			ganador = jugadaColumnaM(tablero, columna);
		else if(comprobarFila(tablero, fila) == true)
			ganador = jugadaFilaM(tablero, fila);
		else
			ganador = jugadaDiagonalM(tablero, fila, columna);
			
		return ganador;
	}
	
	/*
	 * recibe como parametro el array tablero y la columna que se quiere comprobar
	 * comprueba si hay algun espacio libre en la columna
	 * devuelve un valor booleano, true si hay algun espacio y false si no
	 */
	static boolean comprobarColumna(String[][] tablero, int columna) {
		boolean espacioLibre = false;
		
		for(int i = 0; i < tablero.length && espacioLibre == false; i++) {
			if(tablero[i][columna] != "X" && tablero[i][columna] != "O")
				espacioLibre = true;
		}
		
		return espacioLibre;
	}
	
	/*
	 * recibe como parametro el array tablero y la columna donde se va a realizar la jugada
	 * va sacando numeros aleatorios que serviran como la fila hasta que encuentre una casilla libre
	 * en la primera casilla libre que encuentre colocara un O y acabara el metodo
	 */
	static boolean jugadaColumnaM(String[][] tablero, int columna) {
		boolean correcto = false, ganador;
		int fila;
		
		do{
			fila = (int)(Math.random()*tablero.length);	
			
			if(tablero[fila][columna] != "X" && tablero[fila][columna] != "O") {
				tablero[fila][columna] = "O";
				correcto = true;
			}
			
		}while(correcto == false);
		
		ganador = comprobarGanador(tablero, fila, columna, "O");
		
		return ganador;
	}
	
	/*
	 * recibe como parametro el array tablero y la fila que se quiere comprobar
	 * el metodo comprueba que en esa fila exista una casilla libre
	 * devuelve un valor booleano, true si hay una casilla libre, false en caso contrario
	 */
	static boolean comprobarFila(String[][] tablero, int fila) {
		boolean espacioLibre = false;
			
			for(int i = 0; i < tablero.length || espacioLibre == false; i++) {
				if(tablero[fila][i] != "X" && tablero[fila][i] != "O")
					espacioLibre = true;
			}
			
		return espacioLibre;
	}
	
	/*
	 * recibe como parametro el array tablero y la fila donde se quiere realizar la jugada
	 * el metodo calcula un numero aleatorio que sera la columna 
	 * en el momento que se encuentre un valor de columna que junto con el de fila sea una casilla vacia
	 * rellenara esta casilla con un O y acabara el metodo
	 */
	static boolean jugadaFilaM(String[][] tablero, int fila) {
		boolean correcto = false, ganador;
		int columna;
		
		do{
			columna = (int)(Math.random()*tablero[fila].length);	
			
			if(tablero[fila][columna] != "X" && tablero[fila][columna] != "O") {
				tablero[fila][columna] = "O";
				correcto = true;
			}
			
		}while(correcto == false);

		ganador = comprobarGanador(tablero, fila, columna, "O");
		
		return ganador;
	}
	
	/*
	 * recibe como parametro el array tablero y la fila y columna donde a colocado una X el usuario
	 * aleatoriamente colocara un O en una de la las dos diagonales de la casilla seleccionada por el usuario
	 */
	static boolean jugadaDiagonalM(String[][] tablero, int fila, int columna) {
		int numeroAl; 
		int opcion;
		boolean posible = false, ganador;
		int nuevaFila, nuevaColumna;
		
		while(posible == false) {
			opcion = (int)(Math.random()*4);
			numeroAl = (int)(Math.random()*(tablero.length - 1) + 1);
			
			switch(opcion) {
			case 0:
				if(fila + numeroAl < tablero.length && columna + numeroAl < tablero.length) {
					nuevaFila = fila + numeroAl;
					nuevaColumna = columna + numeroAl;
					if(tablero[nuevaFila][nuevaColumna] != "O" && tablero[nuevaFila][nuevaColumna] != "X") {
						tablero[nuevaFila][nuevaColumna] = "O";
						posible = true;
					}		
				}	
				break;
			case 1:
				if(fila - numeroAl >= 0 && columna - numeroAl >= 0) {
					nuevaFila = fila - numeroAl;
					nuevaColumna = columna - numeroAl;
					if(tablero[nuevaFila][nuevaColumna] != "O" && tablero[nuevaFila][nuevaColumna] != "X") {
						tablero[nuevaFila][nuevaColumna] = "O";
						posible = true;
					}
				}	
				break;
			case 2:
				if(fila + numeroAl < tablero.length && columna - numeroAl >= 0) {
					nuevaFila = fila + numeroAl;
					nuevaColumna = columna - numeroAl;
					if(tablero[nuevaFila][nuevaColumna] != "O" && tablero[nuevaFila][nuevaColumna] != "X") {
						tablero[nuevaFila][nuevaColumna] = "O";
						posible = true;
					}
				}	
				break;
			case 3:
				if(fila - numeroAl >= 0 && + numeroAl < tablero.length) {
					nuevaFila = fila - numeroAl;
					nuevaColumna = columna + numeroAl;
					if(tablero[nuevaFila][nuevaColumna] != "O" && tablero[nuevaFila][nuevaColumna] != "X") {
						tablero[nuevaFila][nuevaColumna] = "O";
						posible = true;
					}
				}	
				break;
			default:
			}
		}
		
		ganador = comprobarGanador(tablero, fila, columna, "O");
		
		return ganador;
	}
	
	/*
	 * recibe como parametro un String
	 * este String es una frase que avisa al usuario de que ha introducido un valor no valido
	 * devuelve un valor entero y se asegura que el usuario no escribe una palabra o palabras
	 */
	static int comprobarRespuesta(String frase) {
		
		while(!sc.hasNextInt()) {
			System.out.println(frase);
			sc.next();
		}
		return sc.nextInt();
	}
	
	/*
	 * recibe ecomo parametro un array bidimensional de String
	 * metodo principal donde se realiza la partida del 3 en raya
	 */
	static void partida(String[][] tablero) {
		int contadorJugadas = 0;
		boolean ganadorUsuario = false;
		boolean ganadorMaquina = false;
		int jugadasTotales = tablero.length * tablero.length;
		int[] filaColumna = new int[2];
		
		while(contadorJugadas < jugadasTotales && ganadorUsuario == false && ganadorMaquina == false) {
			ganadorUsuario = jugadaUsuario(tablero, filaColumna);
			contadorJugadas++;
			
			if(contadorJugadas < jugadasTotales && ganadorUsuario == false){
				ganadorMaquina = jugadaMaquina(tablero, filaColumna[0], filaColumna[1]);
				contadorJugadas++;
			}
		}
		mostrarArray(tablero);
		
		if(ganadorUsuario == true)
			System.out.println("El ganador ha sido el usuario");
		else if(ganadorMaquina == true)
			System.out.println("El ganador ha sido la maquina");
		else
			System.out.println("No hay ganador, es un empate");
	}

	/*
	 * recibe como parametro el array tablero, la fila y columna del ultimo movimiento y el simbolo del jugador o la maquina
	 * comprueba si hay una jugada ganadora alrededor de la ultima jugada
	 * devuelve true si hay ganador, false en caso contrario
	 */
	static boolean comprobarGanador(String[][] tablero, int fila, int columna, String simbolo) {
		boolean ganador = false;
		
		 ganador = ganadorFila(tablero, fila, columna, simbolo);
		 if (ganador == false)
			 ganador = ganadorColumna(tablero, fila, columna, simbolo);
		 if(ganador == false)
			 ganador = ganadorDiagonal(tablero, fila, columna, simbolo);
		
		return ganador;
	}
	
	/*
	 * recibe como parametro el array tablero, la fila y columna del ultimo movimiento y el simbolo del jugador o la maquina
	 * comprueba si hay una jugada ganadora en la fila de la ultima jugada
	 * devuelve true si hay ganador, false en caso contrario 
	 */
	static boolean ganadorFila(String[][] tablero,int fila, int columna, String simbolo) {
		boolean ganador = false;
		
		if(columna - 1 >= 0 && tablero[fila][columna -1] == simbolo) {
			if(columna - 2 >= 0 && tablero[fila][columna - 2] == simbolo)
				ganador = true;
			
			else if(columna + 1 < tablero.length && tablero[fila][columna + 1] == simbolo)
				ganador = true;
		}
		
		else if(columna + 1 < tablero.length && tablero[fila][columna + 1] == simbolo) {
			if(columna + 2 < tablero.length && tablero[fila][columna + 2] == simbolo)
				ganador = true;
		}	
		return ganador;
	}

	/*
	 * recibe como parametro el array tablero, la fila y columna del ultimo movimiento y el simbolo del jugador o la maquina
	 * comprueba si hay una jugada ganadora en la columna de la ultima jugada
	 * devuelve true si hay ganador, false en caso contrario 
	 */
	static boolean ganadorColumna(String[][] tablero,int fila, int columna, String simbolo) {
		boolean ganador = false;
		
		if(fila - 1 >= 0 && tablero[fila -1][columna] == simbolo) {
			if(fila - 2 >= 0 && tablero[fila - 2][columna] == simbolo)
				ganador = true;
			
			else if(fila + 1 < tablero.length && tablero[fila + 1][columna] == simbolo)
				ganador = true;
		}
		
		else if(fila + 1 < tablero.length && tablero[fila + 1][columna] == simbolo) {
			if(fila + 2 < tablero.length && tablero[fila + 2][columna] == simbolo)
				ganador = true;
		}
		
		return ganador;
	}

	/*
	 * recibe como parametro el array tablero, la fila y columna del ultimo movimiento y el simbolo del jugador o la maquina
	 * comprueba si hay una jugada ganadora en las diagonales de la ultima jugada
	 * devuelve true si hay ganador, false en caso contrario 
	 */
	static boolean ganadorDiagonal(String[][] tablero,int fila, int columna, String simbolo){
		boolean ganador = false;
				
		if((fila - 1 >= 0 && columna -1 >= 0) && tablero[fila -1][columna - 1] == simbolo) {
			if((fila - 2 >= 0 && columna - 2 >= 0) && tablero[fila - 2][columna - 2] == simbolo)
				ganador = true;
			
			else if((fila + 1 < tablero.length && columna + 1 < tablero.length) && tablero[fila + 1][columna + 1] == simbolo)
				ganador = true;
		}
		
		else if((fila + 1 < tablero.length && columna + 1 < tablero.length) && tablero[fila + 1][columna + 1] == simbolo) {
			if((fila + 2 < tablero.length && columna + 2 < tablero.length) && tablero[fila + 2][columna + 2] == simbolo)
				ganador = true;
		}
		
		else if((fila + 1 < tablero.length && columna - 1 >= 0) && tablero[fila + 1][columna - 1] == simbolo) {
			if((fila + 2 < tablero.length && columna - 2 >= 0) && tablero[fila + 2][columna - 2] == simbolo)
				ganador = true;
			
			else if((fila - 1 >= 0 && columna + 1 < tablero.length) && tablero[fila - 1][columna + 1] == simbolo)
				ganador = true;
		}
		
		else if((fila - 1 >= 0 && columna + 1 < tablero.length) && tablero[fila - 1][columna + 1] == simbolo) {
			if((fila - 2 >= 0 && columna + 2 < tablero.length) && tablero[fila - 2][columna + 2] == simbolo)
				ganador = true;
		}
		
		return ganador;
	}
}