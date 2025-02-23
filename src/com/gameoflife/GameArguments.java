package com.gameoflife;

/**
 * Clase que maneja y valida los argumentos de entrada para configurar el juego.
 * Procesa parámetros como dimensiones, velocidad, y tipo de vecindario.
 */
public class GameArguments {
    // Parámetros configurables del juego
    private final int width;          // Ancho de la cuadrícula
    private final int height;         // Alto de la cuadrícula
    private final int generations;    // Número de generaciones a simular (0=infinito)
    private final int speed;          // Velocidad en ms entre generaciones
    private final String population;  // Población inicial (formato string o "rnd")
    private final int neighborhood;   // Tipo de vecindario para contar células

    // Códigos de color ANSI
    private static final String RESET = Colors.RESET;
    private static final String GREEN = Colors.GREEN;
    private static final String CYAN = Colors.CYAN;
    private static final String RED = Colors.RED;

    /**
     * Constructor que procesa y valida los argumentos de entrada.
     *
     * @param args Argumentos en formato "param=valor"
     */
    public GameArguments(String[] args) {
        // Procesar los argumentos
        Parameters params = processArguments(args);

        // Mostrar parámetros configurados
        printConfiguration(params);

        // Validar parámetros
        validateParameters(params);

        // Asignar valores validados
        this.width = params.width;
        this.height = params.height;
        this.generations = params.generations;
        this.speed = params.speed;
        this.population = params.population;
        this.neighborhood = params.neighborhood;
    }

    /**
     * Procesa los argumentos de entrada y los almacena en un objeto Parameters.
     *
     * @param args Argumentos en formato "param=valor"
     * @return Objeto Parameters con los valores procesados
     */
    private Parameters processArguments(String[] args) {
        Parameters params = new Parameters();

        try {
            for (String arg : args) {
                String[] parts = arg.split("=");
                if (parts.length != 2) continue;

                switch (parts[0]) {
                    case "w": params.width = Integer.parseInt(parts[1]); break; // Ancho
                    case "h": params.height = Integer.parseInt(parts[1]); break; // Alto
                    case "g": params.generations = Integer.parseInt(parts[1]); break; // Generaciones
                    case "s": params.speed = Integer.parseInt(parts[1]); break; // Velocidad
                    case "p": params.population = parts[1]; break; // Población
                    case "n": params.neighborhood = Integer.parseInt(parts[1]); break; // Vecindario
                }
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + "Error: Formato de número inválido. Verifique los valores numéricos." + RESET);
            System.exit(1);
        }

        return params;
    }

    /**
     * Muestra la configuración actual del juego.
     *
     * @param params Objeto Parameters con los valores procesados
     */
    private void printConfiguration(Parameters params) {
        System.out.println(CYAN + "▶ Configuración del juego:" + RESET);
        System.out.println("  • " + GREEN + "Ancho = " + params.width + RESET);
        System.out.println("  • " + GREEN + "Alto = " + params.height + RESET);
        System.out.println("  • " + GREEN + "Generaciones = " + params.generations + RESET);
        System.out.println("  • " + GREEN + "Velocidad = " + params.speed + " ms" + RESET);
        System.out.println("  • " + GREEN + "Población = " + params.population + RESET);
        System.out.println("  • " + GREEN + "Vecindario = " + params.neighborhood + RESET + "\n");
    }

    /**
     * Valida que los parámetros cumplan con las restricciones definidas.
     *
     * @param params Objeto Parameters con los valores procesados
     */
    private void validateParameters(Parameters params) {
        StringBuilder missingParams = new StringBuilder();
        if (params.width == null) missingParams.append("w, ");
        if (params.height == null) missingParams.append("h, ");
        if (params.generations == null) missingParams.append("g, ");
        if (params.speed == null) missingParams.append("s, ");
        if (params.population == null) missingParams.append("p, ");

        if (!missingParams.isEmpty()) {
            System.out.println(RED + "Error: Faltan los siguientes parámetros obligatorios: " +
                    missingParams.substring(0, missingParams.length() - 2) + RESET);
            System.out.println("Ejemplo: java GameOfLife w=20 h=20 g=100 s=500 p=rnd n=3");
            System.exit(1);
        }
    }

    /**
     * Verifica que los parámetros cumplan con las restricciones definidas.
     * Este método es privado y solo se usa internamente.
     *
     * @return true si todos los parámetros son válidos, false en caso contrario
     */
    private boolean isValid() {
        StringBuilder errors = new StringBuilder();

        // Validación de dimensiones
        if (!(width == 10 || width == 20 || width == 40 || width == 80)) {
            errors.append("Ancho debe ser 10, 20, 40 u 80\n");
        }

        if (!(height == 10 || height == 20 || height == 40)) {
            errors.append("Alto debe ser 10, 20 o 40\n");
        }

        // Validación de velocidad
        if (!(speed >= 250 && speed <= 1000)) {
            errors.append("Velocidad debe estar entre 250 y 1000 ms\n");
        }

        // Validación de vecindario
        if (!(neighborhood >= 1 && neighborhood <= 5)) {
            errors.append("Vecindario debe estar entre 1 y 5\n");
        }

        // Generaciones negativas no permitidas
        if (generations < 0) {
            errors.append("Generaciones no puede ser negativo\n");
        }

        // Si hay errores, mostrarlos
        if (!errors.isEmpty()) {
            System.out.println(RED + "Error: Parámetros inválidos:" + RESET);
            System.out.print(errors);
            return false;
        }

        return true;
    }

    // Getters para acceder a los parámetros validados
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getGenerations() { return generations; }
    public int getSpeed() { return speed; }
    public String getPopulation() { return population; }
    public int getNeighborhood() { return neighborhood; }

    /**
     * Clase interna para almacenar temporalmente los parámetros procesados.
     */
    private static class Parameters {
        Integer width = null;
        Integer height = null;
        Integer generations = null;
        Integer speed = null;
        String population = null;
        Integer neighborhood = 3; // Valor por defecto
    }

    /**
     * Método público para validar los parámetros.
     * Llama internamente al método privado `isValid`.
     *
     * @return true si todos los parámetros son válidos, false en caso contrario
     */
    public boolean validate() {
        return isValid();
    }
}


