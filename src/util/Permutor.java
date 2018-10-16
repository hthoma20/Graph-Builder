package util;

import java.lang.reflect.Array;
import java.util.*;

public class Permutor<T> implements Iterable<List<T>>{
    private ArrayList<T> master;
    private List<List<T>> permutations;

    public static void main(String[] args){
        ArrayList<Integer> set= new ArrayList<>();
        for(int i=0; i<11 ; i++){
            set.add(i);
        }

        Permutor<Integer> permutor= new Permutor<>(set);

        /*for(List<Integer> str : permutor){
            for(Integer val : str){
                System.out.print(val+",");
            }
            System.out.println();
        }*/

        System.out.println(permutor.size());
    }

    public Permutor(Collection<T> set){
        this.master= new ArrayList<>(set);
        this.permutations= getPermutations();
    }

    public List<List<T>> getPermutations(){
        return getPermutations(master);
    }

    private List<List<T>> getPermutations(ArrayList<T> choices){

        if(choices.size() == 1){
            List<List<T>> singleList= new ArrayList<List<T>>(1);
            singleList.add(choices);
            return singleList;
        }

        ArrayList<List<T>> permutations= new ArrayList<>();

        for(int i=0;i<choices.size();i++) {
            T choice = choices.get(i);

            ArrayList<T> subChoices = (ArrayList<T>) choices.clone();
            subChoices.remove(i);

            List<List<T>> subPermutations = getPermutations(subChoices);

            //add our choice to the list,
            //and add this list to all the lists
            for (List<T> list : subPermutations) {
                list.add(choice);
                permutations.add(list);
            }
        }

        return permutations;
    }

    public int size(){
        return permutations.size();
    }

    @Override
    public Iterator<List<T>> iterator() {
        return permutations.iterator();
    }
}
