package de.melanx.simplebackups.platform;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.melanx.simplebackups.SimpleBackups;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Resolves the bundled English language file for logs and clients without SimpleBackups.
 * Fabric has no server-side equivalent of NeoForge's FMLTranslations helper.
 */
public final class ServerTranslations {

    private static final Map<String, String> EN_US = loadEnglishTranslations();

    private ServerTranslations() {
    }

    public static String format(String key, Object... parameters) {
        String pattern = EN_US.getOrDefault(key, key);
        try {
            return String.format(Locale.ROOT, pattern, parameters);
        } catch (RuntimeException e) {
            SimpleBackups.LOGGER.warn("Failed to format server translation {}", key, e);
            return pattern;
        }
    }

    private static Map<String, String> loadEnglishTranslations() {
        Map<String, String> translations = new HashMap<>();
        String resource = "/assets/" + SimpleBackups.MODID + "/lang/en_us.json";
        try (InputStream stream = ServerTranslations.class.getResourceAsStream(resource)) {
            if (stream == null) {
                SimpleBackups.LOGGER.warn("Missing bundled language resource {}", resource);
                return Map.of();
            }

            JsonObject json = JsonParser.parseReader(new InputStreamReader(stream, StandardCharsets.UTF_8)).getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                if (entry.getValue().isJsonPrimitive() && entry.getValue().getAsJsonPrimitive().isString()) {
                    translations.put(entry.getKey(), entry.getValue().getAsString());
                }
            }
        } catch (IOException | RuntimeException e) {
            SimpleBackups.LOGGER.warn("Failed to load bundled server translations", e);
        }

        return Map.copyOf(translations);
    }
}
