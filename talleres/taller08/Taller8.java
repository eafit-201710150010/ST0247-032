/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taller8;

import java.util.Arrays;

/**
 *
 * @author ljpalaciom
 */
public class Taller8 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int a[] = {-1, 15, 8, 28, 4,8,0};
        quickSort(a);
        System.out.println(Arrays.toString(a));
    }

   public static void quickSort(int a[]) {
        quickSort(a, 0, a.length - 1);
    }

    private static void quickSort(int a[], int left, int right) {
        if (right > left) {
            int pared = left;
            int pivote = right;
            while (pared < pivote) {
                if (a[pared] > a[pivote]) {
                    int temp = a[pivote];
                    a[pivote] = a[pared];
                    if (pivote - pared == 1) {
                        a[pared] = temp;
                    } else {
                        a[pared] = a[pivote - 1];
                        a[pivote - 1] = temp;
                    }
                    pivote--;
                } else {
                    pared++;
                }
            }
            quickSort(a, left, pivote - 1);
            quickSort(a, pivote + 1, right);
        }
    }
// [4,9,6,13,8]
// 9

    public static void mergeSort(int a[]) {
        mergeSort(a, 0, a.length - 1);
    }

    private static void mergeSort(int a[], int l, int r) {
        if (r > l) {
            int mid = (r + l) / 2;
            mergeSort(a, l, mid);
            mergeSort(a, mid + 1, r);
            merge(a, l, r, mid);
        }
    }

    private static void merge(int a[], int l, int r, int mid) {
        int temp[] = new int[r - l + 1];
        int i = l;
        int j = mid + 1;
        int k = 0;
        while (i <= mid && j <= r) {
            if (a[i] <= a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        while (i <= mid) {
            temp[k++] = a[i++];
        }
        while (j <= r) {
            temp[k++] = a[j++];
        }
        for (i = 0; i < temp.length; i++, l++) {
            a[l] = temp[i];
        }
    }

}
