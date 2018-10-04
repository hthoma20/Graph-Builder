package util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Permutor <T> {
    private ArrayList<T> master;

    public static void main(String[] args){
        ArrayList<Character> chars= new ArrayList<Character>();

        Permutor<Character> permutor= new Permutor<>(new HashSet(chars));

        List<List<Character>> permutations= permutor.getPermutations();

        for(List<Character> str : permutations){
            for(Character ch : str){
                System.out.print(ch);
            }
            System.out.println();
        }

        System.out.println(permutations.size());
    }

    public Permutor(Set<T> set){
        this.master= new ArrayList<>(set);
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
}
