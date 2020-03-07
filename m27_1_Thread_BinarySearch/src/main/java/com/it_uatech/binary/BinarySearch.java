package com.it_uatech.binary;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class BinarySearch {

    <T extends Number> Optional<T> search(List<T> list, T element, Comparator<T> comparator){
        if (list.isEmpty()) throw new SearchException("List is empty");

        List<T> sortedList = list.stream().parallel().sorted(comparator).collect(Collectors.toList());
        int indexFirstElement = 0;
        int indexLastElement = sortedList.size() - 1;
        T searchedElement = binarySearch(sortedList,element, comparator, indexFirstElement,indexLastElement);
        return Optional.ofNullable(searchedElement);
    }

     private <T extends Number> T binarySearch(List<T> list, T element, Comparator<T> comparator, int indexFirstElement, int indexLastElement){
        int elementIndex = (indexLastElement - indexFirstElement)/2 + indexFirstElement;
        if (elementIndex < 0 || elementIndex >= list.size() || indexFirstElement > indexLastElement) return null;

        int comparingResult = comparator.compare(element, list.get(elementIndex));
        if (comparingResult == 0) return element;
        if (comparingResult > 0){
            return binarySearch(list,element,comparator,(elementIndex + 1),indexLastElement);
        }else {
            return binarySearch(list,element,comparator,indexFirstElement,(elementIndex - 1));
        }
     }

}
