package de.hglabor.plugins.kitapi.util.gui.component;

public interface ValueComponent {

    <K> void setValue(K value);

    <K> K getValue();

}
