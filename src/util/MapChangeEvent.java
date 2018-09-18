package util;

public class MapChangeEvent<K,V> {
    private ChangeType type;

    private K changedKey;
    private V oldValue;
    private V newValue;

    public MapChangeEvent(ChangeType type, K changedKey, V oldValue, V newValue){
        if(type != ChangeType.PUT && type != ChangeType.REMOVE){
            throw new IllegalArgumentException("Wrong constructor for type "+type.toString());
        }
        this.type= type;
        this.oldValue= oldValue;
        this.changedKey= changedKey;
        this.newValue= newValue;
    }

    public MapChangeEvent(ChangeType type){
        if(type == ChangeType.PUT){
            throw new IllegalArgumentException("Wrong constructor for type "+type.toString());
        }
        this.type= type;
        this.changedKey= null;
        this.changedKey= null;
    }

    public ChangeType getType(){
        return this.type;
    }

    public K getChangedKey() {
        return changedKey;
    }

    public V getOldValue() {
        return oldValue;
    }

    public V getNewValue() {
        return newValue;
    }

    public enum ChangeType{
        PUT,
        CLEAR,
        REMOVE,
        PUTALL;
    }
}
