package de.hglabor.plugins.kitapi.util.gui.component.picker;

public enum Char {

    A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z;

    public char getChar(boolean upperCase) {
        if(upperCase) {
            return name().toUpperCase().charAt(0);
        } else {
            return name().toLowerCase().charAt(0);
        }
    }

}
