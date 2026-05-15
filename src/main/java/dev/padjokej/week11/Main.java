package dev.padjokej.week11;

import java.io.*;

public class Main {

    static void main(String[] args) {
        if (args.length < 2) throw new RuntimeException("Please provide a file to compress, and whether you want to (c)ompress or e(x)tract");

        LZWState state = (args[0].equalsIgnoreCase("c"))
                ? LZWState.COMPRESS : (args[0].equalsIgnoreCase("x"))
                                      ? LZWState.DECOMPRESS : LZWState.NULL;

        if (state == LZWState.NULL) throw new RuntimeException("Option " + args[0] + " is not a valid action");

        String source = args[1], destination;
        destination = (args.length < 3)
                ? (state == LZWState.COMPRESS) ? source.concat(".lzw") : source.substring(0, source.length() - 4)
                : args[2];

        try {
            boolean _ = new File(destination).createNewFile();
        } catch (IOException e) {
            System.err.println("Could not create file \"" + destination + "\"");
            System.exit(1);
        }

        try (FileOutputStream fileWriter = new FileOutputStream(destination);
             FileInputStream fileReader = new FileInputStream(source);) {
            switch (state) {
                case COMPRESS -> compress(fileReader, fileWriter);
                case DECOMPRESS -> decompress(fileReader, fileWriter);
                default -> {}
            }

        } catch (IOException e) {
            System.err.println("Could not open files");
            System.err.println(e);
            System.exit(1);
        }
    }

    static void compress(FileInputStream fileReader, FileOutputStream fileWriter) {
        LZWOutputStream compressor = new LZWOutputStream(fileWriter);
        try {
            fileReader.transferTo(compressor);
        } catch (IOException e) {
            System.err.println("Could not compress file:");
            System.err.println(e);
            System.exit(1);
        }
    }

    static void decompress(FileInputStream fileReader, FileOutputStream fileWriter) {
        LZWInputStream decompressor = new LZWInputStream(fileReader);
        try {
            decompressor.transferTo(fileWriter);
        } catch (IOException e) {
            System.err.println("Could not decompress file:");
            System.err.println(e);
            System.exit(1);
        }
    }

    private static enum LZWState {
        COMPRESS,
        DECOMPRESS,
        NULL,;
    }
}
