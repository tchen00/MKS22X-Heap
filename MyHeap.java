import java.util.*;
import java.io.*;

public class MyHeap{

  private static void pushDown(int[]data, int size, int index){
    /*
       - size  is the number of elements in the data array.
       - push the element at index i downward into the correct position. This will swap with the larger of the child nodes provided thatchild is larger. This stops when a leaf is reached, or neither child is larger. [ should be O(logn) ]
       - precondition: index is between 0 and size-1 inclusive
       - precondition: size is between 0 and data.length-1 inclusive.
    */

    // tracker for sorting
    boolean sort = false;
    // while it is not sort
    while (!sort) {
      int a = index * 2 + 1; // left side
      int b = index * 2 + 2; // right child
      // if it doesn't have children
      if (a >= size && b >= size) {
        sort = true;
      }
      // if it does have children
      else {
        // finding the larger child haha
        int max = Math.max(data[index], data[a]);
        if (b < size) {
          // finding maximum
          max = Math.max(max, data[b]);
        }
        // storing a temp of current data
        int temp = data[index];
        if (max == data[index]) sort = true; // if parent is larger -- mission complete ; sort true
        else { // if not, do swapping
          // left is bigger
          if (max == data[a]){
            // performing the swap
            data[index] = data[a];
            data[a] = temp;
            index = a;
          }
          // right is bigger
          else {
            // performing the swap
            data[index] = data[b];
            data[b] = temp;
            index = b;
          }
        }
      }
    }
  }

  private static void pushUp(int[]data,int index){
    /*
       - push the element at index i up into the correct position. This will swap it with the parent node until the parent node is larger or the root is reached. [ should be O(logn) ]
       - precondition: index is between 0 and data.length-1 inclusive.
    */

    //tracker for sorting
    boolean sort = false;
    // while not sort
    while (!sort) {
      // if ther e is no parent
      if (index == 0) {
        sort = true;
      }
      else {
        int parent = (index - 1) / 2;
        // temp for swapping
        int temp = data[index];
        // if child is greater --
        if (temp > data[parent]){
          // swap
          data[index] = data[parent];
          data[parent] = temp;
          index = parent;
        }
        else sort = true;
      }
    }



  }

  public static void heapify(int[] data){
    /*
      - convert the array into a valid heap. [ should be O(n) ]
    */

    // second to last row of heap
    int row = (int)(Math.log(data.length) / Math.log(2));
    // starting from the right child
    for (int i = (int) Math.pow(2, row) - 2; i >= 0; i--){
      // push down the child
      pushDown(data, data.length, i);
    }


  }

  public static void heapsort(int[] data){
    /*
      - sort the array by converting it into a heap then removing the largest value n-1 times. [ should be O(nlogn) ]
    */

    heapify(data);
    for (int i = data.length - 1; i >= 0; i--){
      // keeping note of the max
      int temp = data[0];
      // swap and then fix later
      data[0] = data[i];
      data[i] = temp;
      pushDown(data, i, 0);
    }
  }

  public static void main(String[]args){
  System.out.println("Size\t\tMax Value\tmerge /builtin ratio ");
  int[]MAX_LIST = {1000000000,500,10};
  for(int MAX : MAX_LIST){
    for(int size = 31250; size < 2000001; size*=2){
      long qtime=0;
      long btime=0;
      //average of 5 sorts.
      for(int trial = 0 ; trial <=5; trial++){
        int []data1 = new int[size];
        int []data2 = new int[size];
        for(int i = 0; i < data1.length; i++){
          data1[i] = (int)(Math.random()*MAX);
          data2[i] = data1[i];
        }
        long t1,t2;
        t1 = System.currentTimeMillis();
        MyHeap.heapsort(data2);
        t2 = System.currentTimeMillis();
        qtime += t2 - t1;
        t1 = System.currentTimeMillis();
        Arrays.sort(data1);
        t2 = System.currentTimeMillis();
        btime+= t2 - t1;
        if(!Arrays.equals(data1,data2)){
          System.out.println("FAIL TO SORT!");
          System.exit(0);
        }
      }
      System.out.println(size +"\t\t"+MAX+"\t"+1.0*qtime/btime);
    }
    System.out.println();
  }
}

}
