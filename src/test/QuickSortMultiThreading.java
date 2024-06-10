package test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import datos.Funciones;

@SuppressWarnings("serial")
public class QuickSortMultiThreading extends RecursiveTask<Integer> {

	// Declaramos los atributos de nuestra claser
	int start;
	int end;
	int[] arr;

	private int partition(int start, int end, int[] arr) {
		// Declaramos las variables LowIndex y HighIndex con los valores de start y end
		// respectivamente
		int LowIndex = start, HighIndex = end;

		// Se elige una posicion aleatoria del arreglo como pivote
		int pivot = new Random().nextInt(HighIndex - LowIndex) + LowIndex;
		// Se realiza un intercambio entre el valor del pivote y el valor en la posición
		// HighIndex del arreglo para mover el pivote al final del arreglo.
		// Se almacena el valor de arr[HighIndex](El valor del final del arreglo) en t
		int t = arr[HighIndex];
		// Se reemplaza al valor de arr[HighIndex] por el del pivote elegido
		arr[HighIndex] = arr[pivot];
		// Se reemplaza el valor del pivote con el del final del arreglo
		arr[pivot] = t;
		// Se decrementa HighIndex para excluir la posición del pivote en futuras
		// comparaciones.
		HighIndex--;

		// Aca comienza la utilizacion de LowIndex y HighIndex como "Punteros"
		// Mientras nuestro LowIndex sea menor o igual a HighIndex
		while (LowIndex <= HighIndex) {

			// En caso de que arr[LowIndex] sea menor o igual al valor final del arreglo
			// nos movemos hacia la derecha en el arreglo
			// La sentencia continue genera que se salte nuevamente a la condicion del bucle
			// while
			if (arr[LowIndex] <= arr[end]) {
				LowIndex++;
				continue;
			}

			// En caso de que arr[HighIndex] sea mayor o igual al valor final del arreglo
			// nos movemos hacia la izquierda en el arreglo
			// La sentencia continue genera que se salte nuevamente a la condicion del bucle
			// while
			if (arr[HighIndex] >= arr[end]) {
				HighIndex--;
				continue;
			}
			// Una vez ambos "punteros" se encuentran en la misma posicion
			// Se utiliza t nuevamente como variable auxiliar para almacenar el valor de
			// arr[HighIndex]
			t = arr[HighIndex];
			// Realizamos un swap entre los valores de arr[HighIndex] y arr[LowIndex]
			arr[HighIndex] = arr[LowIndex];
			// Cambiamos el valor de arr[LowIndex] por el de t(Anteriormente HighIndex)
			arr[LowIndex] = t;
			// Nos movemos a la izquierda en el caso de HighIndex y a la derecha en el caso
			// de Low Index para salir del bucle
			HighIndex--;
			LowIndex++;
		}
		// se realiza unintercambio entre el valor de arr[HighIndex + 1 y el valor
		// originalmente en end, moviendo el pivote a su posición final en el arreglo.
		// Se utiliza t para almacenar la posicion en la que se encontraron los punteros
		t = arr[HighIndex + 1];
		// Se almacena el valor final del arreglo en arr[HighIndex + 1]
		arr[HighIndex + 1] = arr[end];
		// reemplazamos el valor de end por el valor con el que vamos a particionar el
		// arreglo
		arr[end] = t;
		// devolvemos HighIndex + 1 como la posición final del pivote después de la partición
		return HighIndex + 1;
	}

	// Creamos el constructor de la clase para su posterior utilización en la
	// funcion compute
	public QuickSortMultiThreading(int start, int end, int[] arr) {
		this.arr = arr;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Integer compute() {
		// El if verifica si el indice de inicio es mayor o igual al final del
		// indice indicando si el arreglo o subarreglo ya esta ordenado
		if (start >= end)
			return null;
		// Se llama al metodo partition para encontrar la posicion correcta del pivote
		// Recibimos el valor p(HighIndex + 1) de partition del arreglo que introducimos
		int p = partition(start, end, arr);

		// Se crean dos subproblemas para la particion de la izquierda y de la derecha
		QuickSortMultiThreading left = new QuickSortMultiThreading(start, p - 1, arr);

		QuickSortMultiThreading right = new QuickSortMultiThreading(p + 1, end, arr);

		// El subproblema izquierdo utiliza la funcion fork para executarse de manera
		// asincrónica
		// en un hilo separado
		left.fork();
		// El subproblema derecho es procesado usando el hilo actual
		right.compute();

		// el metodo join se asegura que el hilo de la izquierda haya completado su
		// proceso
		// antes de seguir
		left.join();

		// Retornamos nulo dado que no requerimos que devuelva nada
		// Todo este programa es ejecutado dentro de ForkJoinPool el cual maneja
		// eficientemente la creacion de hilos y su ejecucion
		return null;
	}

	// Nuestro main a ejecutar
	public static void main(String args[]) {

		// Declaramos la variables startTime y endTime para guardar el tiempo de
		// ejecucion
		// Inicializamos el arreglo arr el cual utilizaremos para generar nuestro array
		// con numeros aleatorios
		// n sera la cantidad de elementos de nuestro arreglo
		long startTime;
		long endTime;
		int[] arr;
		int n = 300000000;

		// Generamos un arreglo con numeros aleatorios
		arr = Funciones.generarArrayAleatorio(n);
		// Generamos una copia del arreglo, la cual vamos a ordenar
		int arrayCopiaQuickSortMultiThreading[] = Arrays.copyOf(arr, arr.length);

		// Se utiliza ForkJoinPool para mantener la
		// creacion de hilos en base a nuestros recursos
		// La piscina de hilos se encarga de mantener la cantidad necesaria de hilos
		// activos o disponibles a partir de añadir dinamicamente, suspender o reanudar
		// hilos de trabajo internos, incluso si algunas tareas estan estancadas
		// esperando
		// a unirse al resto
		ForkJoinPool pool = ForkJoinPool.commonPool();
		// Comenzamos a tomar el tiempo de nuestro algoritmo de ordenamiento
		startTime = System.currentTimeMillis();

		// Invocamos a la pileta de hilos y utilizamos nuestro primer hilo para ordenar
		// el arreglo
		pool.invoke(

				new QuickSortMultiThreading(0, n - 1, arrayCopiaQuickSortMultiThreading));

		// Dejamos de contar el tiempo de nuestro algoritmo, considerando su ejecución
		// finalizada
		endTime = System.currentTimeMillis();
		// Devolvemos cuantos microsegundos tardó nuestro algoritmo
		System.out.println("\n QuickSort Concurrente-> Demoró: " + (endTime - startTime) + " ms \n");
	}
}