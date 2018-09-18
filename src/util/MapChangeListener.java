package util;

public interface MapChangeListener<K,V> {
    void mapChanged(MapChangeEvent<K,V> e);
}
