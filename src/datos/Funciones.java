package datos;

import java.util.Random;

public class Funciones {

	public static int[] generarArrayAleatorio(int n) {
		// Declaramos un array auxiliar que posteriormente vamos a devolver
		int[] arr = new int[n];

		// Generación de números aleatorios
		Random random = new Random();
		// Se llena cada espacio del arreglo con un número aleatorio
		// entre 0 y n
		for (int i = 0; i < n; i++) {
			arr[i] = random.nextInt(n);
		}
		//devolvemos el array auxiliar
		return arr;
	}

	public static void mostrarArray(int[] arr) {
		// Generamos un bucle foreach que se encargara de imprimir todos los elementos del
		// arreglo por pantalla
		for (int num : arr) {
			System.out.print(+ num + "  ");
		}
	}

}
