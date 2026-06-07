package be.helha.clapetteChaussette.common.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Application configuration loaded once from a simple {@code key=value} file.
 * <p>
 * The file is parsed by hand: one setting per line, blank lines are ignored.
 * Access the values through {@link #getInstance()} anywhere in the application.
 * </p>
 */
public class AppConfig {

    private static final String CONFIG_FILE = "config.txt";

    private static AppConfig instance;

    private String serverHost;
    private int serverPort;

    private AppConfig() {
        load();
    }

    /**
     * Returns the single configuration instance, loading the file on first call.
     *
     * @return the shared {@link AppConfig} instance.
     */
    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    // Reads the config file line by line and fills the fields.
    private void load() {
        try (BufferedReader reader = Files.newBufferedReader(Path.of(CONFIG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                // skip blank lines
                if (line.isEmpty()) {
                    continue;
                }
                int separator = line.indexOf('=');
                // no '=' on the line, nothing to parse
                if (separator < 0) {
                    continue;
                }
                String key = line.substring(0, separator).trim();
                String value = line.substring(separator + 1).trim();
                apply(key, value);
            }
        } catch (IOException e) {
            throw new RuntimeException("Impossible de lire le fichier de configuration : " + CONFIG_FILE, e);
        }
    }

    // Stores one key/value pair in the matching field.
    private void apply(String key, String value) {
        switch (key) {
            case "serverHost" -> serverHost = value;
            case "serverPort" -> serverPort = Integer.parseInt(value);
            default -> System.err.println("Clé de configuration inconnue : " + key);
        }
    }

    /**
     * @return the host the client connects to.
     */
    public String getServerHost() {
        return serverHost;
    }

    /**
     * @return the TCP port the server listens on and the client connects to.
     */
    public int getServerPort() {
        return serverPort;
    }
}
