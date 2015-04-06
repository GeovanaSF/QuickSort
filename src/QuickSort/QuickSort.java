/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QuickSort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Geovana
 */
public class QuickSort {
  private String[] data;
  private String[] all;

  private int len,cont_tr,cont_cp;

  public QuickSort(int max) {
    data = new String[max];
    all = new String[max];
    len = 0;
    cont_tr = 0;
    cont_cp = 0;
  }

  public void insert(String value, String all) {
    data[len] = value; // insert and increment size
    this.all[len] = all; //insere toda a informação
    //System.out.println("all "+this.all[len]+"data "+data[len]);
    len++;
  }

  public void display() {
    System.out.print("Data:");
    for (int j = 0; j < len; j++)
      System.out.print(data[j] + "\n ");
    System.out.println("");
    /*System.out.print("All:");
    for (int j = 0; j < len; j++)
      System.out.print(all[j] + "\n ");
    System.out.println("");*/
  }

  public void quickSort() {
    recQuickSort(0, len - 1);
  }

  public void recQuickSort(int left, int right) {
    int size = right - left + 1;
    if (size <= 3) // manual sort if small
      manualSort(left, right);
    else // quicksort if large
    {
      String median = medianOf3(left, right);
      int partition = partitionIt(left, right, median);
      recQuickSort(left, partition - 1);
      recQuickSort(partition + 1, right);
    }
  }

  public String medianOf3(int left, int right) {
    int center = (left + right) / 2;
    // order left & center
    cont_cp++;
    if (data[left].compareTo(data[center])>0)
      swap(left, center);
    // order left & right
    cont_cp++;
    if (data[left].compareTo(data[right])>0)
      swap(left, right);
    // order center & right
    cont_cp++;
    if (data[center].compareTo(data[right])>0)
      swap(center, right);

    swap(center, right - 1); // put pivot on right
    return data[right - 1]; // return median value
  }

  public void swap(int dex1, int dex2) {
    cont_tr++;
    String temp = data[dex1];
    data[dex1] = data[dex2];
    data[dex2] = temp;
    String temp1 = all[dex1];
    all[dex1] = all[dex2];
    all[dex2] = temp1;
  }

  @SuppressWarnings("empty-statement")
  public int partitionIt(int left, int right, String pivot) {
    int leftPtr = left; // right of first elem
    int rightPtr = right - 1; // left of pivot

    while (true) {
      //       find bigger
      while (data[++leftPtr].compareTo(pivot)<0)
        cont_cp++;
      //       find smaller
      while (data[--rightPtr].compareTo(pivot)>0)
          cont_cp++;
        
      if (leftPtr >= rightPtr) // if pointers cross, partition done
        break;    
      else
        // not crossed, so
        swap(leftPtr, rightPtr); // swap elements
    }
    swap(leftPtr, right - 1); // restore pivot
    return leftPtr; // return pivot location
  }

  public void manualSort(int left, int right) {
    int size = right - left + 1;
    if (size <= 1)
      return; // no sort necessary
    if (size == 2) { // 2-sort left and right
        cont_cp++;
        if (data[left].compareTo(data[right]) > 0)
        swap(left, right);
      return;
    } else // size is 3
    { // 3-sort left, center, & right
      cont_cp++;
      if (data[left].compareTo(data[right - 1])>0)
        swap(left, right - 1); // left, center
      cont_cp++;
      if (data[left].compareTo(data[right])>0)
        swap(left, right); // left, right
      cont_cp++;
      if (data[right - 1].compareTo(data[right])>0)
        swap(right - 1, right); // center, right
    }
  }
  
  public void inserir_nomes(QuickSort qs){
      BufferedReader br = null;
      int cont=0;
      try {
          br = new BufferedReader(new FileReader(new File("saida.txt")));
          String s = br.readLine(),nome;
          while(/*cont<10)/*/s != null)
          {
              nome = s.substring(21,s.length());
              qs.insert(nome,s);
              s = br.readLine();
              cont++;
          }
      } catch (FileNotFoundException ex) {
          Logger.getLogger(QuickSort.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
          Logger.getLogger(QuickSort.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
          try {
              br.close();
          } catch (IOException ex) {
              Logger.getLogger(QuickSort.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
  }
  
  public void salvar(QuickSort qs){
      BufferedWriter bw = null;
      try {
          bw = new BufferedWriter(new FileWriter(new File("saida_ordenada.txt")));
          int cont = 0;
          while(cont<len){
              bw.write(all[cont],0,all[cont].length());
              bw.newLine();
              cont++;
          }
      } catch (IOException ex) {
          Logger.getLogger(QuickSort.class.getName()).log(Level.SEVERE, null, ex);
      } finally {
          try {
              bw.close();
          } catch (IOException ex) {
              Logger.getLogger(QuickSort.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
  }

  public static void main(String[] args) {
      System.out.println("Começou");
      int maxSize = 1000000;
      QuickSort arr = new QuickSort(maxSize);
      arr.inserir_nomes(arr);
      //arr.display();
      arr.quickSort();
      //arr.display();
      System.out.println("Ordenação concluída");
      System.out.println("Número de Trocas: "+arr.cont_tr);
      System.out.println("Numero de Comparações: "+arr.cont_cp);
      arr.salvar(arr);
      System.out.println("Acabou");
  }
}

