package test;

import java.util.Arrays;

import datos.Funciones;

public class QuickSortSecuencial {

	public static void quickSort(int[] arr, int low, int high) {
		// El if verifica si el arreglo ya se encuentra ordenado, caso contrario entra
		// en el bucle
		// de llamadas a la funcion quicksort
		if (low < high) {
			// Se llama al metodo partition para encontrar la posicion correcta del pivote
			// Recibimos el valor pi(i + 1) de partition del arreglo que introducimos
			int pi = partition(arr, low, high);
			// Realizamos quickSort recursivamente a la particion izquierda del pivote
			quickSort(arr, low, pi - 1);
			// Realizamos quickSort recursivamente a la particion derecha del pivote
			quickSort(arr, pi + 1, high);
		}
	}

	private static int partition(int[] arr, int low, int high) {
		// Se elige el final del arreglo como pivote
		int pivot = arr[high];
		// Se inicializa una variable i, que se usará para realizar un seguimiento del
		// índice del último elemento más pequeño que el pivote.
		int i = low - 1;

		// Se genera un bucle for, que funcionará hasta que j sea igual o mayor a
		// high(el final del arreglo), provocando que se recorra todo el arreglo
		for (int j = low; j < high; j++) {
			// Este if se encarga de que mientras nuestro "puntero" izquierdo j sea menor al
			// pivote ocurra lo siguiente

			if (arr[j] < pivot) {
				// Se incrementa i dado que se encontró un elemento menor que el pivote
				i++;
				// Intercambia el elemento actual (arr[j]) con el elemento en la posición i, que
				// es el último elemento más pequeño que el pivote. Esto coloca todos los
				// elementos menores que el pivote a la izquierda de i, y los elementos mayores
				// o iguales al pivote a la derecha
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}
		// todos los elementos menores que el pivote estarán a la izquierda de i, y
		// todos los elementos mayores o iguales al pivote estarán a la derecha.
		// Entonces, i + 1 es la posición donde debería estar el pivote después de la
		// partición por lo tanto se utiliza temp para almacenarlo
		int temp = arr[i + 1];
		// Se intercambia el pivote (arr[high]) con el elemento en la posición i + 1.
		// Esto coloca el pivote en su posición correcta en el arreglo
		arr[i + 1] = arr[high];
		arr[high] = temp;
		// devolvemos i+1 como índice del pivote después de la partición
		return i + 1;
	}

	public static void main(String[] args) {

		// Declaramos la variables startTime y endTime para guardar el tiempo de
		// ejecucion
		// Inicializamos el arreglo arr el cual utilizaremos para generar nuestro array
		// con numeros aleatorios
		// n sera la cantidad de elementos de nuestro arreglo
		long startTime;
		long endTime;
		int[] array;
		int n = 300000000;
		// Generamos un arreglo con numeros aleatorios
		array = Funciones.generarArrayAleatorio(n);
		// Comenzamos a tomar el tiempo de nuestro algoritmo de ordenamiento
		startTime = System.currentTimeMillis();
		// Generamos una copia del arreglo, la cual vamos a ordenar
		int arrayCopiaQuickSort[] = Arrays.copyOf(array, array.length);
		// LLamamos a la funcion QuickSort, dandole los parametros necesarios, dando
		// paso a la
		// recursividad de la funcion y el ordenamiento del arreglo
		quickSort(arrayCopiaQuickSort, 0, (array.length - 1));
		// Dejamos de contar el tiempo de nuestro algoritmo, considerando su ejecución
		// finalizada
		endTime = System.currentTimeMillis();
		// Devolvemos cuantos microsegundos tardó nuestro algoritmo
		System.out.println(" \n QuickSort-> Demoró: " + (endTime - startTime) + " ms \n");

	}

}
