package de.melanx.simplebackups;

import net.neoforged.fml.loading.FMLPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ToolsLoader {

    private static final Path TOOLS_DIR = FMLPaths.CONFIGDIR.get().resolve(SimpleBackups.MODID).resolve("external-dependencies");
    public static final Path RELATIVE_TOOLS_DIR = FMLPaths.GAMEDIR.get().relativize(TOOLS_DIR);
    private static final Logger LOGGER = LoggerFactory.getLogger(ToolsLoader.class);
    private static URLClassLoader classLoader = null;

    public static void init() {
        if (!Files.isDirectory(ToolsLoader.TOOLS_DIR)) {
            try {
                Files.createDirectories(ToolsLoader.TOOLS_DIR);
            } catch (IOException e) {
                LOGGER.error("Failed to create external-dependencies directory", e);
            }

            return;
        }

        List<URL> urls = new ArrayList<>();
        try (Stream<Path> stream = Files.list(ToolsLoader.TOOLS_DIR)) {
            stream.filter(p -> p.toString().endsWith(".jar")).forEach(jar -> {
                try {
                    urls.add(jar.toUri().toURL());
                    LOGGER.info("Loaded tools jar: {}", jar.getFileName());
                } catch (Exception e) {
                    LOGGER.warn("Failed to load tools jar: {}", jar.getFileName(), e);
                }
            });
        } catch (IOException e) {
            LOGGER.warn("Failed to scan external-dependencies directory", e);
            return;
        }

        if (!urls.isEmpty()) {
            classLoader = new URLClassLoader(urls.toArray(URL[]::new), ToolsLoader.class.getClassLoader());
        }
    }

    public static boolean isZstdAvailable() {
        return ToolsLoader.isClassAvailable("com.github.luben.zstd.Zstd");
    }

    public static boolean isClassAvailable(String className) {
        if (classLoader == null) return false;
        try {
            Class.forName(className, false, classLoader);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * Wraps the given stream in a ZstdOutputStream loaded from the tools classloader.
     * The returned stream is safe to cast to {@link OutputStream} since ZstdOutputStream extends it.
     *
     * @param level ZSTD compression level (1–19), or -1 for the ZSTD default (3)
     */
    public static OutputStream wrapWithZstd(OutputStream out, int level) throws IOException {
        if (classLoader == null) {
            throw new IOException("zstd-jni is not available in external-dependencies");
        }
        try {
            Class<?> clazz = classLoader.loadClass("com.github.luben.zstd.ZstdOutputStream");
            if (level < 0) {
                return (OutputStream) clazz.getConstructor(OutputStream.class).newInstance(out);
            }
            return (OutputStream) clazz.getConstructor(OutputStream.class, int.class).newInstance(out, level);
        } catch (ReflectiveOperationException e) {
            throw new IOException("Failed to create ZstdOutputStream", e);
        }
    }
}
