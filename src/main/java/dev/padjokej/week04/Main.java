package dev.padjokej.week04;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Map;

import static javax.swing.SwingUtilities.invokeLater;

public final class Main {
    private static LSystem koch() {
        Map<Character, String> rules = Map.of('F', "F-F++F-F");
        return new LSystem("F++F++F", rules, "F", 60);
    }

    private static LSystem sierpinski() {
        Map<Character, String> rules = Map.of('A', "+B-A-B+", 'B', "-A+B+A-");
        return new LSystem("A", rules, "AB", 60);
    }

    private static LSystem hilbert() {
        Map<Character, String> rules = Map.of('A', "-BF+AFA+FB-", 'B', "+AF-BFB-FA+");
        return new LSystem("A", rules, "F", 90);
    }

    private static LSystem dragon() {
        Map<Character, String> rules = Map.of('X', "X+YF+", 'Y', "-FX-Y");
        return new LSystem("FX", rules, "F", 90);
    }

    private static LSystem tree() {
        Map<Character, String> rules = Map.of('F', "FF+[+F-F-F]-[-F+F+F]");
        return new LSystem("----F", rules, "F", 25);
    }

    private static LSystem plant() {
        Map<Character, String> rules = Map.of('X', "F-[[X]+X]+F[+FX]-X", 'F', "FF");
        return new LSystem("---X", rules, "F", 30);
    }

    public static void main(String[] args) {
        // Le L-système à dessiner
        LSystem lSystem = plant().evolve(5);

        invokeLater(() -> {
            Path2D lSystemPath = LSystemPainter.paint(lSystem);

            JFrame mainWindow = new JFrame("L-Système");
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            mainWindow.getContentPane().setLayout(new BorderLayout());
            PathComponent pathComponent = new PathComponent();
            pathComponent.setPreferredSize(new Dimension(400, 400));
            pathComponent.setPath(lSystemPath);
            mainWindow.getContentPane().add(pathComponent, BorderLayout.CENTER);

            mainWindow.pack();
            mainWindow.setVisible(true);
        });
    }
}
