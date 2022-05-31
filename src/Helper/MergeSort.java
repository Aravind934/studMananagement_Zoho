package Helper;

import SchoolEntity.Management;
import SchoolEntity.Marks;
import SchoolEntity.Student;

import java.util.Arrays;

public class MergeSort {
    Management management = Management.getInstance();

    public Student[] merge(Student[] arr1, Student[] arr2) {
        Student[] res = new Student[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] == null) {
                i++;
                continue;
            }
            if (arr2[j] == null) {
                j++;
                continue;
            }
            ;
            Marks marks1 = arr1[i].getMarks();
            Marks marks2 = arr2[j].getMarks();
            if (marks1.getTotal() >= marks2.getTotal()) {
                res[k++] = arr1[i++];
                continue;
            }
            res[k++] = arr2[j++];
        }
        while (i < arr1.length) {
            if (arr1[i] == null) {
                i++;
                continue;
            }
            res[k++] = arr1[i++];
        }
        while (j < arr2.length) {
            if (arr2[j] == null) {
                j++;
                continue;
            }
            res[k++] = arr2[j++];
        }
        return res;
    }

    public Student[] sort(Student[] arr) {
        if (arr.length <= 1) return arr;
        int mid = arr.length / 2;
        Student[] left = sort(Arrays.copyOfRange(arr, 0, mid));
        Student[] right = sort(Arrays.copyOfRange(arr, mid, arr.length));
        Student[] res = merge(left, right);
        System.out.println(res);
        return res;
    }
}
