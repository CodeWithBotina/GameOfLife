package com.gameoflife;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Clase que maneja la reproducción de sonidos en el juego.
 */
public class MusicPlayer {

    // Rutas de los sonidos
    private static final String START_SOUND = "resources/soundtrack1.wav"; // Sonido de inicio
    private static final String GENERATION_SOUND = "resources/soundtrack2.wav"; // Sonido de generación
    private static final String END_SOUND = "resources/soundtrack3.wav"; // Sonido de finalización

    /**
     * Reproduce el sonido de inicio.
     */
    public static void playStartSound() {
        playSound(START_SOUND, false); // No en bucle
    }

    /**
     * Reproduce el sonido al generar una nueva generación.
     */
    public static void playGenerationSound() {
        new Thread(() -> playSound(GENERATION_SOUND, false)).start();
    }

    /**
     * Reproduce el sonido de finalización.
     */
    public static void playEndSound() {
        playSound(END_SOUND, false); // No en bucle
    }

    /**
     * Método genérico para reproducir sonidos.
     *
     * @param filePath Ruta del archivo de sonido
     * @param loop Si se debe reproducir en bucle
     */
    private static void playSound(String filePath, boolean loop) {
        try {
            File soundFile = new File(filePath);
            if (!soundFile.exists()) {
                System.out.println("Error: No se encontró el archivo " + filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Esperar a que termine el sonido antes de continuar (excepto en el sonido de generación)
            if (!loop && !filePath.equals(GENERATION_SOUND)) {
                clip.start();
                Thread.sleep(clip.getMicrosecondLength() / 1000); // Esperar a que termine el sonido
            } else {
                clip.start();
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            System.out.println("Error al reproducir la música: " + e.getMessage());
        }
    }
}

