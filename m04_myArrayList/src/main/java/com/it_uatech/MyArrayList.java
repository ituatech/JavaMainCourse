package com.it_uatech;


import java.util.*;
import java.util.function.UnaryOperator;


public class MyArrayList<T> implements List<T>, RandomAccess, Iterable<T> {

    private final int CAPASITY = 10;
    private Object[] objects;
    private int size;

    public MyArrayList() {
        this.objects = new Object[this.CAPASITY];
    }

    //for realisation: static <T> void sort(List<T> list, Comparator<? super T> c)
    @Override
    public void sort(Comparator<? super T> c) {
        Arrays.sort((T[])objects,0,size,c);
    }
    // ---------------------------------------------------------------------------

    //for realisation: static <T> void copy(List<? super T> dest, List<? extends T> src)
    private void grow() {
        objects = Arrays.copyOf(objects,(objects.length>>1)+objects.length);
    }

    @Override
    public void add(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index is bigger then array size");
        }
        if (size == objects.length) {
            grow();
        }
        System.arraycopy(objects,index,objects,index+1,size-index);
        objects[index] = element;
        size++;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index is bigger then array size");
        }
        T prevElement = (T) objects[index];
        System.arraycopy(objects,index+1,objects,index,size-(index+1));
        size--;
        return prevElement;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index is bigger then array size");
        }
        T prevElement = (T) objects[index];
        objects[index] = element;
        return prevElement;
    }

    @Override
    public ListIterator<T> listIterator() {
        ListIterator<T> listIterator = new MyListIterator(0);
        return listIterator;
    }

    private class MyListIterator implements ListIterator<T> {

        private int cursor;          // index of next element to return
        private int lastRet = -1;    // index of last element returned; -1 if no such

        private MyListIterator(int index) {
            this.cursor = index;
        }


        @Override
        public boolean hasNext() {
            return cursor < MyArrayList.this.size;
        }

        @Override
        public T next() {
            T element = null;
            if (this.hasNext()) {
                element = (T) MyArrayList.this.objects[cursor];
                lastRet = cursor++;
            } else {
                throw new NoSuchElementException();
            }
            return element;
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public T previous() {
            T element = null;
            if (this.hasPrevious()) {
                cursor--;
                lastRet = cursor;
                element = (T) MyArrayList.this.objects[cursor];
            } else {
                throw new NoSuchElementException();
            }
            return element;
        }

        @Override
        public int nextIndex() {
            return cursor;
        }

        @Override
        public int previousIndex() {
            return cursor - 1;
        }

        @Override
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            MyArrayList.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
        }

        @Override
        public void set(T element) {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            MyArrayList.this.set(lastRet, element);
        }

        @Override
        public void add(T element) {
            MyArrayList.this.add(cursor, element);
            cursor++;
            lastRet = -1;
        }
    }

    // ----------------------------------------------------------------------------

    //for realisation: addAll(Collection<? super T> c, T... elements)
    @Override
    public boolean add(T t) {
        boolean result = false;
        if(size>=objects.length){
            grow();
        }
        objects[size] = t;
        size++;
        result = true;
        return result;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = false;
        for(int i = 0; i<size; i++){
            if(o.equals(objects[i])){
                result = true;
                MyArrayList.this.remove(i);
                break;
            }
        }
        return result;
    }
    // ----------------------------------------------------------------------------

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        if(size>0){
        objects = new Object[CAPASITY];
        size=0;
        }
    }

    @Override
    public boolean contains(Object o) {
        boolean result = false;
        for(int i = 0; i<size; i++){
            if(o.equals(objects[i])){
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index is bigger then array size");
        }
        return (T) objects[index];
    }


    @Override
    public int indexOf(Object o) {
        int result = -1;
        for(int i = 0; i<size; i++){
            if(o.equals(objects[i])){
                result = i;
                break;
            }
        }
        return result;
    }

    public int getArrayLength(){
        return objects.length;
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(objects,size);
    }
    // ------------------ NOT IMPLEMENTED ------------------------------------------


    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        throw new RuntimeException("no method 'replaceAll()' programmed");
    }

    @Override
    public Spliterator<T> spliterator(){
        throw new RuntimeException("no method 'spliterator()' programmed");
    }


    @Override
    public Iterator<T> iterator() {
        throw new RuntimeException("no method 'iterator()' programmed");
    }


    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new RuntimeException("no method 'toArray(T1[] a)' programmed");
    }



    @Override
    public boolean containsAll(Collection<?> c) {
        throw new RuntimeException("no method 'containsAll(Collection<?> c)' programmed");
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new RuntimeException("no method 'addAll(Collection<? extends T> c)' programmed");
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new RuntimeException("no method 'addAll(int index, Collection<? extends T> c)' programmed");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new RuntimeException("no method 'removeAll(Collection<?> c)' programmed");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new RuntimeException("no method 'retainAll(Collection<?> c)' programmed");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new RuntimeException("no method 'lastIndexOf(Object o)' programmed");
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new RuntimeException("no method 'listIterator(int index)' programmed");
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new RuntimeException("no method 'subList(int fromIndex, int toIndex)' programmed");
    }
}
