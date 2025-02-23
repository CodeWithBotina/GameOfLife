package com.gameoflife;

import java.util.Random;
import java.io.IOException;
/**
 * Clase que representa la cuadrícula del Juego de la Vida.
 * Esta clase contiene la lógica para inicializar, actualizar y mostrar la cuadrícula,
 * así como para aplicar las reglas de Conway que determinan la evolución de las células.
 */
public class Grid {
    // Símbolos para representar el estado de las células
    private static final String ALIVE = "\u001B[42m \uD83C\uDF1E \u001B[0m"; // Fondo verde para célula viva
    private static final String DEAD = "\u001B[41m \uD83D\uDC80 \u001B[0m";  // Fondo rojo para célula muerta

    // Códigos de color ANSI para mejorar la visualización en la consola
    private static final String RESET = Colors.RESET;
    private static final String GREEN = Colors.GREEN;
    private static final String YELLOW = Colors.YELLOW;
    private static final String BOLD = Colors.BOLD;
    private static final String RED = Colors.RED;

    // Parámetros de configuración de la cuadrícula
    private final int width;
    private final int height;
    private final int generations;
    private final int speed;
    private final int neighborhood;
    private String[][] grid;

    /**
     * Constructor de la cuadrícula.
     * Inicializa la cuadrícula con los parámetros proporcionados y la población inicial.
     *
     * @param width Ancho de la cuadrícula (número de columnas).
     * @param height Alto de la cuadrícula (número de filas).
     * @param population Población inicial. Puede ser "rnd" para una población aleatoria
     *                   o un patrón específico en formato de cadena.
     * @param neighborhood Tipo de vecindario (1-5) para contar células vecinas.
     * @param generations Número de generaciones a simular. Si es 0, la simulación es infinita.
     * @param speed Velocidad de simulación en milisegundos entre generaciones.
     */
    public Grid(int width, int height, String population, int neighborhood, int generations, int speed) {
        // Asigna los valores de los parámetros a las variables de la clase.
        this.width = width;
        this.height = height;
        this.generations = generations;
        this.speed = speed;
        this.neighborhood = neighborhood;

        // Inicializa la matriz de la cuadrícula con las dimensiones especificadas.
        this.grid = new String[height][width];

        // Llama al método para inicializar la cuadrícula con la población especificada.
        initializeGrid(population);
    }

    /**
     * Inicializa la cuadrícula con la población especificada.
     * Este método primero llena la cuadrícula con células muertas y luego, dependiendo
     * del valor de 'population', la llena con un patrón específico o de forma aleatoria.
     * @param population Población inicial. Puede ser "rnd" para una población aleatoria o un patrón específico en formato de cadena.
     */
    private void initializeGrid(String population) {
        // Llena toda la cuadrícula con células muertas.
        fillGridWithDeadCells();

        // Verifica si la población es aleatoria ("rnd").
        if ("rnd".equals(population)) {
            // Llena la cuadrícula con células vivas o muertas de forma aleatoria.
            fillGridRandomly();
        } else {
            // Llena la cuadrícula con un patrón específico.
            fillGridWithPattern(population);
        }
    }

    /**
     * Llena toda la cuadrícula con células muertas.
     * Este método recorre cada celda de la cuadrícula y la establece como muerta (DEAD).
     */
    private void fillGridWithDeadCells() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = DEAD;
            }
        }
    }

    /**
     * Llena la cuadrícula con células vivas o muertas de forma aleatoria.
     * Este método utiliza un generador de números aleatorios para decidir si una célula
     * estará viva (ALIVE) o muerta (DEAD).
     */
    private void fillGridRandomly() {
        // Crea una instancia de Random para generar números aleatorios.
        Random random = new Random();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // Usa Random para decidir si la célula estará viva o muerta.
                // nextBoolean() devuelve true o false de forma aleatoria.
                grid[i][j] = random.nextBoolean() ? ALIVE : DEAD;
            }
        }
    }

    /**
     * Llena la cuadrícula con un patrón específico.
     * Este método toma una cadena que representa el patrón de población inicial y lo aplica
     * a la cuadrícula. El patrón debe estar en formato de cadena, donde cada fila está separada
     * por el carácter '#' y cada celda está representada por '1' (viva) o '0' (muerta).
     *
     * @param population Cadena que representa el patrón de población inicial.
     */
    private void fillGridWithPattern(String population) {
        // Divide la cadena de población en filas usando el carácter '#' como separador.
        String[] rows = population.split("#");

        for (int i = 0; i < rows.length && i < height; i++) {
            // Elimina espacios en blanco al principio y al final de la fila.
            String row = rows[i].trim();

            for (int j = 0; j < row.length() && j < width; j++) {
                grid[i][j] = (row.charAt(j) == '1') ? ALIVE : DEAD;
            }
        }
    }

    /**
     * Imprime la cuadrícula en la consola con un formato mejorado, incluyendo un borde
     * y colores para resaltar las células vivas y muertas. Además, muestra el título
     * de la generación actual y las estadísticas de población (células vivas y muertas).
     *
     * @param generation Número de la generación actual.
     */
    public void printGrid(int generation) {
        printGenerationTitle(generation);
        int aliveCount = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j].equals(ALIVE)) {
                    System.out.print(GREEN + grid[i][j] + RESET);
                    aliveCount++;
                } else {
                    System.out.print(grid[i][j]);
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        printPopulationStatistics(aliveCount);
    }

    /**
     * Imprime el título de la generación actual en la consola.
     * Este método muestra un título diferente dependiendo de si es la generación inicial (semilla)
     * o una generación posterior. El título se muestra en negrita y color amarillo para resaltarlo.
     *
     * @param generation Número de la generación actual. Si es 0, se considera la generación semilla.
     */
    private void printGenerationTitle(int generation) {
        if (generation == 0) {
            System.out.println(BOLD + YELLOW + "\n▓▓▓ Generación Semilla ▓▓▓" + RESET);
        } else {
            System.out.println(BOLD + YELLOW + "\n▓▓▓ Generación " + generation + " ▓▓▓" + RESET);
        }
    }

    /**
     * Imprime las estadísticas de población, mostrando el número de células vivas y muertas
     * en la generación actual. Este método calcula el número de células muertas restando
     * el número de células vivas (aliveCount) del total de células en la cuadrícula (width * height).
     *
     * @param aliveCount Número de células vivas en la generación actual.
     */
    private void printPopulationStatistics(int aliveCount) {
        int deadCount = (width * height) - aliveCount;
        System.out.println(
                GREEN + "Células Vivas: " + aliveCount + RESET + " | " + RED + "Células Muertas: " + deadCount + RESET);
    }

    /**
     * Calcula la siguiente generación de células en la cuadrícula aplicando las reglas de Conway.
     * Este método crea una nueva cuadrícula (newGrid) y, para cada célula, determina su estado
     * en la siguiente generación basándose en su estado actual y el número de vecinos vivos.
     * Finalmente, la cuadrícula actual (grid) se actualiza con la nueva generación.
     */
    private void nextGeneration() {
        // Crea una nueva cuadrícula (newGrid) con las mismas dimensiones que la cuadrícula actual.
        String[][] newGrid = new String[height][width];

        // Recorre cada célula de la cuadrícula actual.
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int neighbors = countLiveNeighbors(i, j);
                boolean isAlive = grid[i][j].equals(ALIVE);
                newGrid[i][j] = determineCellState(isAlive, neighbors);
            }
        }
        // Actualiza la cuadrícula actual (grid) con la nueva generación (newGrid).
        grid = newGrid;
    }

    /**
     * Determina el estado de una célula en la siguiente generación basándose en su estado actual
     * y el número de vecinos vivos. Este método aplica las reglas del Juego de la Vida:
     * 1. Subpoblación, 2. Supervivencia, 3. Sobrepoblación y 4. Reproducción.
     *
     * @param isAlive Indica si la célula está viva en la generación actual (true) o muerta (false).
     * @param neighbors Número de vecinos vivos de la célula.
     * @return El estado de la célula en la siguiente generación: ALIVE (viva) o DEAD (muerta).
     */
    private String determineCellState(boolean isAlive, int neighbors) {
        // Verifica si la célula está viva en la generación actual.
        if (isAlive) {
            // Regla 1: Subpoblación
            // Si una célula viva tiene menos de 2 vecinos vivos, muere por falta de apoyo.
            if (neighbors < 2) {
                return DEAD; // La célula muere por subpoblación.
            }
            // Regla 2: Supervivencia
            // Si una célula viva tiene 2 o 3 vecinos vivos, sobrevive a la siguiente generación.
            else if (neighbors <= 3) {
                return ALIVE; // La célula sobrevive.
            }
            // Regla 3: Sobrepoblación
            // Si una célula viva tiene más de 3 vecinos vivos, muere por sobrepoblación.
            else {
                return DEAD; // La célula muere por sobrepoblación.
            }
        }
        // Si la célula está muerta en la generación actual.
        else {
            // Regla 4: Reproducción
            // Si una célula muerta tiene exactamente 3 vecinos vivos, cobra vida por reproducción.
            if (neighbors == 3) {
                return ALIVE; // La célula cobra vida.
            }
            // Si no se cumple la Regla 4, la célula sigue muerta.
            else {
                return DEAD; // La célula sigue muerta.
            }
        }
    }

    /**
     * Cuenta los vecinos vivos de una célula en la posición (row, col) según el tipo de vecindario seleccionado.
     * Este método recorre todas las direcciones posibles del vecindario y verifica si las células en esas
     * posiciones están vivas. Solo se cuentan las células que están dentro de los límites de la cuadrícula.
     *
     * @param row Fila de la célula de la cual se quieren contar los vecinos.
     * @param col Columna de la célula de la cual se quieren contar los vecinos.
     * @return Número de vecinos vivos de la célula en la posición (row, col).
     */
    private int countLiveNeighbors(int row, int col) {
        // Contador para almacenar el número de vecinos vivos.
        int count = 0;

        int[][] directions = getNeighborhoodDirections();

        for (int[] dir : directions) {
            // Calcula la nueva posición (newRow, newCol) sumando la dirección actual a la posición (row, col).
            int newRow = row + dir[0]; // Nueva fila.
            int newCol = col + dir[1]; // Nueva columna.

            // Verifica si la nueva posición está dentro de los límites de la cuadrícula.
            if (isWithinBounds(newRow, newCol)) {
                // Si la célula en la nueva posición está viva, incrementa el contador.
                if (grid[newRow][newCol].equals(ALIVE)) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Obtiene las direcciones del vecindario según el tipo seleccionado.
     * Dependiendo del valor de 'neighborhood', se devuelven diferentes conjuntos de direcciones
     * que representan las posiciones de los vecinos de una célula en la cuadrícula.
     *
     * @return Matriz de direcciones del vecindario, donde cada fila es un par {fila, columna}
     *         que indica la posición relativa de un vecino.
     */
    private int[][] getNeighborhoodDirections() {
        // {-1, -1} => Diagonal superior izquierda.
        // {-1, 0} => Arriba.
        // {-1, 1} => Diagonal superior derecha.
        // {0, -1} => Izquierda.
        // {0, 1} => Derecha.
        // {1, -1} => Diagonal inferior izquierda.
        // {1, 0} => Abajo.
        // {1, 1} => Diagonal inferior derecha.
        return switch (neighborhood) {
            // Caso 1: Vecindario Von Neumann (solo vecinos ortogonales)
            case 1 -> new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

            // Caso 2: Vecindario Parcial (vecinos ortogonales + 2 diagonales)
            case 2 -> new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, 1}};

            // Caso 3: Vecindario Moore (todos los vecinos, 8 direcciones)
            case 3 -> new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

            // Caso 4: Vecindario Diagonal (solo vecinos diagonales)
            case 4 -> new int[][]{{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

            // Caso 5: Vecindario Horizontal (vecinos diagonales + horizontales)
            case 5 -> new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {1, -1}, {1, 0}, {1, 1}};

            // Caso por defecto: Si el valor de 'neighborhood' no es válido
            default -> {
                // Imprime un mensaje de advertencia indicando que se usará el vecindario de Moore por defecto.
                System.out.println("Vecindario no definido, usando Moore (n=3).");
                // Devuelve las direcciones del vecindario de Moore (caso 3) como valor por defecto.
                yield new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
            }
        };
    }
    /**
     * Verifica si una posición (fila, columna) está dentro de los límites de la cuadrícula.
     * Esto es útil para evitar errores al acceder a posiciones fuera de la matriz.
     *
     * @param row Fila de la posición que se quiere verificar.
     * @param col Columna de la posición que se quiere verificar.
     * @return true si la posición es válida (está dentro de los límites de la cuadrícula),
     *         false en caso contrario.
     */
    private boolean isWithinBounds(int row, int col) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    /**
     * Ejecuta la simulación del Juego de la Vida.
     * Este método controla el ciclo principal del juego, donde se generan y muestran
     * las diferentes generaciones de células en la cuadrícula.
     */
    public void run() {
        int generation = 0;

        while (generations == 0 || generation <= generations) {
            if (generation >= 0) {
                MusicPlayer.playGenerationSound();
            }

            printGrid(generation);

            if (generations == 0 && isSpacePressed()) {
                System.out.println(YELLOW + "\nSimulación detenida por el usuario." + RESET);
                break;
            }

            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                System.out.println("Simulación interrumpida.");
                break;
            }
            nextGeneration();
            generation++;
        }
        if (generations > 0 && generation > generations) {
            System.out.println(YELLOW + "\nSimulación completada: se alcanzó el límite de " + generations + " generaciones." + RESET);
        }
    }

    /**
     * Verifica si el usuario ha presionado la tecla ESPACIO para detener la simulación.
     * Este método revisa si hay entrada disponible en la consola y, si la hay, verifica
     * si la tecla presionada es el código ASCII correspondiente a la tecla ESPACIO (32).
     *
     * @return true si se presionó la tecla ESPACIO, false en caso contrario.
     */
    private boolean isSpacePressed() {
        try {
            // Verifica si hay datos disponibles en la entrada estándar (System.in).
            if (System.in.available() > 0) {
                // Lee el siguiente byte de la entrada estándar.
                int key = System.in.read();
                return key == 32; // 32 es el código ASCII de la tecla ESPACIO.
            }
        } catch (IOException e) {
            System.out.println("Error al leer entrada del usuario: " + e.getMessage());
        }
        return false;
    }
}


