import AlmacenadorResultados.AlmacenadorResultados;
import Buffer.Buffer;
import CSVReader.CSVReader;
import KNNHandler.KNNHandler;
import Task.MNISTask;
import Task.PoisonPill;
import Workers.ThreadPool;
import Workers.WorkerCounter;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) {

        // PENDIENTE VERSION IMAGEN Y VERSION CSV
        // PENDIENTE LEER IMAGEN
        // PENDIENTE RESOLVER K CERCANOS

        // Variables modificables por el usuario
        int cantidadDeThreads = parseInt(args[0]);
        int tamano_buffer = parseInt(args[1]);
        int k = parseInt(args[2]);

        // Inicializo clases necesarias
        Buffer buffer = new Buffer(tamano_buffer);
        WorkerCounter workerCounter = new WorkerCounter(0);
        ThreadPool threadPool = new ThreadPool(cantidadDeThreads, buffer, workerCounter);
        AlmacenadorResultados almacenadorResultados = new AlmacenadorResultados();

        // Inicializo los threads
        threadPool.start();

        // Leo el CSV de entrenamiento
        CSVReader csvReader = new CSVReader();
        List<List<String>> datos_entrenamiento = csvReader.read(60000);

        // Particiono las lineas de entrenamiento
        int tamano_particion = 10000;
        List<List<List<String>>> particiones = new ArrayList<>();
        for(int i = 0; i < datos_entrenamiento.size(); i += tamano_particion) {
            particiones.add(datos_entrenamiento.subList(i, Math.min(i + tamano_particion, datos_entrenamiento.size())));
        }

        // Creo las tasks segun cantidad de particiones
        for(List<List<String>> particion : particiones) {
            MNISTask mnisTask = new MNISTask(particion, null, almacenadorResultados);

            buffer.write(mnisTask);
        }

        // Creo una PoisonPill para cada thread
        for(int i = 0; i < cantidadDeThreads; i++) {
            PoisonPill poisonPill = new PoisonPill();

            buffer.write(poisonPill);
        }

        KNNHandler knnHandler = new KNNHandler(almacenadorResultados);

        // Una vez conseguidos los resultados del knnHandler, comparar para ver el resultado a devolver
    }
}