package com.gameoflife;

/**
 * Panel de bienvenida con visualización mejorada y colores ANSI.
 * Muestra un mensaje de bienvenida atractivo al inicio del programa.
 */
public class WelcomePanel {
    // Códigos de color ANSI
    private static final String RESET = Colors.RESET;
    private static final String GREEN = Colors.GREEN;
    private static final String CYAN = Colors.CYAN;
    private static final String YELLOW = Colors.YELLOW;
    private static final String PURPLE = Colors.PURPLE;

    /**
     * Muestra un mensaje de bienvenida colorido y visualmente atractivo.
     */
    public static void showWelcomeMessage() {
        String welcome = CYAN + """
        
         ██████╗  █████╗ ███╗   ███╗███████╗     ██████╗ ██████╗      ██╗     ██╗███████╗███████╗
        ██╔════╝ ██╔══██╗████╗ ████║██╔════╝     ██╔══██╗██╔════╗     ██║     ██║██╔════╝██╔════╝
        ██║  ███╗███████║██╔████╔██║█████╗       ██║  ██║██████╔╝     ██║     ██║█████╗  ███████╗
        ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝       ██║  ██║██╔═══╝      ██║     ██║██╔══╝  ██║
        ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗     ██████╔╝██║          ███████╗██║██║     ███████╗
         ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝     ╚═════╝ ╚═╝          ╚══════╝╚═╝╚═╝     ╚══════╝
        """ + RESET;

        System.out.println(welcome);
        System.out.println(YELLOW + "                        ★ The Game of Life by CodeWithBotina ★" + RESET);
        System.out.println(GREEN + "\n¡Bienvenido a *Game Of Life*!" + RESET);
        System.out.println(PURPLE + "\n──────────────────────────────────────────────────────────" + RESET);
        System.out.println(CYAN + "\nUn autómata celular donde células viven y mueren según reglas simples.");
        System.out.println(CYAN + "Observa cómo patrones complejos emergen de reglas simples.\n");
    }
}


