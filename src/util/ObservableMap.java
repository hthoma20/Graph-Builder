package util;

import java.util.*;

public class ObservableMap<K,V> implements Map<K,V> {
    HashMap<K,V> map;

    List<MapChangeListener<K,V>> listeners= new ArrayList<>();

    public ObservableMap(){
        this.map= new HashMap<>();
    }

    public ObservableMap(int initialCapacity){
        this.map= new HashMap<>(initialCapacity);
    }

    public ObservableMap(int initialCapacity, float loadFactor){
        this.map= new HashMap<>(initialCapacity, loadFactor);
    }

    public ObservableMap(Map<? extends K,? extends V> m){
        this.map= new HashMap<>(m);
    }

    public void addMapChangeListener(MapChangeListener l){
        listeners.add(l);
    }


    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        V oldVal= map.get(key);

        V returnVal= map.put(key,value);

        sendEvent(new MapChangeEvent<>(MapChangeEvent.ChangeType.PUT,key,oldVal,value));

        return returnVal;
    }

    @Override
    public V remove(Object key) {
        V oldVal= map.get(key);

        V returnVal= map.remove(key);

        sendEvent(new MapChangeEvent<>(MapChangeEvent.ChangeType.REMOVE,key,oldVal,null));

        return returnVal;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);

        sendEvent(new MapChangeEvent(MapChangeEvent.ChangeType.PUTALL));
    }

    @Override
    public void clear() {
        map.clear();

        sendEvent(new MapChangeEvent(MapChangeEvent.ChangeType.CLEAR));
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    private void sendEvent(MapChangeEvent e){
        for(MapChangeListener listener : listeners){
            listener.mapChanged(e);
        }
    }
}
