package util;

import graph.Vertex;

import java.util.ArrayList;

public class DegreeSequence implements MapChangeListener<Vertex, Integer> {
    private ArrayList<Integer> sequence;

    private ObservableMap<Vertex, Integer> degrees;

    public DegreeSequence(ObservableMap<Vertex, Integer> degrees){
        this.degrees= degrees;
        degrees.addMapChangeListener(this);

        initSequence();
    }

    private void initSequence(){
        this.sequence= new ArrayList<>(degrees.size());
        for(Integer val : degrees.values()){
            add(val);
        }
    }

    private void add(Integer x){
        //adds the specified int to the sequence
        for(int i=0;i<sequence.size();i++){
            if(x > sequence.get(i)){
                sequence.add(i,x);
                return;
            }
        }

        sequence.add(x);
    }

    private void change(Integer oldNum, Integer newNum){
        sequence.remove(oldNum);
        add(newNum);
    }

    @Override
    public void mapChanged(MapChangeEvent<Vertex, Integer> e) {
        switch(e.getType()){
            case PUT:
                change(e.getOldValue(),e.getNewValue());
                break;
            case CLEAR:
            case PUTALL:
                initSequence();
                break;
            case REMOVE:
                sequence.remove(e.getOldValue());
                break;
        }
    }

    public String toString(){
        if(sequence.size() < 1){
            return "";
        }

        String str= "";
        for(int i=0;i<sequence.size()-1;i++){
            str+= sequence.get(i)+",";
        }

        return str+sequence.get(sequence.size()-1);
    }
}
