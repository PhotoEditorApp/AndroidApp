package com.netcracker_study_autumn_2020.library.data;

public class BaseSorter<T> {
    abstract class MicroComparator {
        abstract boolean compare(T o1, T o2);
    }

    private MicroComparator innerComparator;

    //public List<T> sort(List<T> data, MicroComparator customComparator){
    // List<T> buffer = data.

    //}

    //private void

}
