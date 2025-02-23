package com.gameoflife;

/**
 * Clase principal que inicia el Juego de la Vida.
 * Implementa el famoso autómata celular diseñado por John Conway.
 * Este programa simula la evolución de células en una cuadrícula
 * basándose en reglas simples que determinan si una célula vive, muere o nace.
 */
public class GameOfLife {
    public static void main(String[] args) {
        // Mostrar el panel de bienvenida con colores
        WelcomePanel.showWelcomeMessage();

        // Iniciar con efectos de sonido
        MusicPlayer.playStartSound();

        // Procesar y validar los argumentos de entrada
        GameArguments gameArgs = new GameArguments(args);
        if (!gameArgs.validate()) { // Validar los parámetros
            System.out.println("Corrija los parámetros y vuelva a intentarlo.");
            return;
        }

        // Crear e inicializar la cuadrícula con los parámetros configurados
        Grid grid = createGridFromArguments(gameArgs);

        // Iniciar la simulación
        grid.run();

        // Sonido de finalización
        MusicPlayer.playEndSound();
    }

    /**
     * Crea una instancia de la cuadrícula utilizando los parámetros validados.
     *
     * @param gameArgs Objeto GameArguments con los parámetros configurados
     * @return Una instancia de Grid lista para la simulación
     */
    private static Grid createGridFromArguments(GameArguments gameArgs) {
        return new Grid(
                gameArgs.getWidth(),
                gameArgs.getHeight(),
                gameArgs.getPopulation(),
                gameArgs.getNeighborhood(),
                gameArgs.getGenerations(),
                gameArgs.getSpeed()
        );
    }
}










