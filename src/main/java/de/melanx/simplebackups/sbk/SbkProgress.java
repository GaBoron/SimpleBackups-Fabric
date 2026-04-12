package de.melanx.simplebackups.sbk;

@FunctionalInterface
public interface SbkProgress {

    void onFile(int completed, int total, String currentPath);

    SbkProgress SILENT = (_, _, _) -> {};
}
