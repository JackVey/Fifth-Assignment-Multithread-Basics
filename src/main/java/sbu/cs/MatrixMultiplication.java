package sbu.cs;

import java.util.*;

public class MatrixMultiplication {

    // You are allowed to change all code in the BlockMultiplier class
    public static class BlockMultiplier implements Runnable
    {
        int row_start;
        int row_end;
        int column_start;
        int column_end;
        List<List<Integer>> tempMatrixProduct = new ArrayList<>();
        List<List<Integer>> temp_A;
        List<List<Integer>> temp_B;
        public BlockMultiplier(int row_start, int row_end, int column_start, int column_end,
                               List<List<Integer>> temp_A, List<List<Integer>> temp_B) {
            this.row_start = row_start;
            this.row_end = row_end;
            this.column_start = column_start;
            this.column_end = column_end;
            this.temp_A = temp_A;
            this.temp_B = temp_B;
            for (int i = 0; i < row_end - row_start + 1; i++) {
                tempMatrixProduct.add(new ArrayList<>());
                for (int j = 0; j < column_end - column_start + 1; j++) {
                    tempMatrixProduct.get(i).add(0);
                }
            }
        }

        @Override
        public void run() {

            for (int i = 0; i < row_end - row_start + 1; i++) {
                for (int j = 0; j < column_end - column_start + 1; j++) {
                    tempMatrixProduct.get(i).set(j, multiplyArray(temp_A, temp_B, row_start + i, column_start + j));
                }
            }
        }
        public static int multiplyArray(List<List<Integer>> A, List<List<Integer>> B, int index_A, int index_B){
            int sum = 0;
            for (int i = 0; i < A.getFirst().size(); i++) {
                sum += A.get(index_A).get(i) * B.get(i).get(index_B);
            }
            return sum;
        }
    }

    /*
    Matrix A is of the form p x q
    Matrix B is of the form q x r
    both p and r are even numbers
    */
    public static List<List<Integer>> ParallelizeMatMul(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B)
    {
        List<List<Integer>> matrix_c = new ArrayList<>();
        for (int i = 0; i < matrix_A.size(); i++) {
            matrix_c.add(new ArrayList<>());
            for (int j = 0; j < matrix_B.getFirst().size(); j++) {
                matrix_c.get(i).add(0);
            }
        }
        BlockMultiplier BM1 = new BlockMultiplier(0, (matrix_A.size()/2) - 1, 0, (matrix_B.getFirst().size()/2) - 1,matrix_A,matrix_B);
        BlockMultiplier BM2 = new BlockMultiplier(0, (matrix_A.size()/2) - 1, matrix_B.getFirst().size()/2, matrix_B.getFirst().size() - 1,matrix_A,matrix_B);
        BlockMultiplier BM3 = new BlockMultiplier(matrix_A.size()/2, matrix_A.size() - 1, 0, (matrix_B.getFirst().size()/2) - 1,matrix_A,matrix_B);
        BlockMultiplier BM4 = new BlockMultiplier(matrix_A.size()/2, matrix_A.size() - 1, matrix_A.size()/2, matrix_B.getFirst().size() - 1,matrix_A,matrix_B);
        Thread newThread1 = new Thread(BM1);
        Thread newThread2 = new Thread(BM2);
        Thread newThread3 = new Thread(BM3);
        Thread newThread4 = new Thread(BM4);
        newThread1.start();
        newThread2.start();
        newThread3.start();
        newThread4.start();
        try {
            newThread1.join();
            newThread2.join();
            newThread3.join();
            newThread4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < matrix_c.size(); i++) {
            for (int j = 0; j < matrix_c.getFirst().size(); j++) {
                if (i <= (matrix_A.size()/2) - 1 && j <= (matrix_B.getFirst().size()/2) - 1){
                    matrix_c.get(i).set(j, BM1.tempMatrixProduct.get(i % 50).get(j));

                } else if (i <= (matrix_A.size()/2) - 1 && j >= matrix_B.getFirst().size()/2) {
                    matrix_c.get(i).set(j, BM2.tempMatrixProduct.get(i).get(j % 50));

                } else if (i >= matrix_A.size()/2 && j <= (matrix_B.getFirst().size()/2) - 1) {
                    matrix_c.get(i).set(j, BM3.tempMatrixProduct.get(i % 50).get(j));

                } else {
                    matrix_c.get(i).set(j, BM4.tempMatrixProduct.get(i % 50).get(j % 50));

                }
            }
        }
        /*
        TODO
            Parallelize the matrix multiplication by dividing tasks between 4 threads.
            Each thread should calculate one block of the final matrix product. Each block should be a quarter of the final matrix.
            Combine the 4 resulting blocks to create the final matrix product and return it.
         */
        return matrix_c;
    }



    public static void main(String[] args) {
        List<List<Integer>> A = new ArrayList<>();
        List<List<Integer>> B = new ArrayList<>();
        A.add(Arrays.asList(1, 2, 0));
        A.add(Arrays.asList(2, -6, 8));
        A.add(Arrays.asList(4, 3, 0));
        A.add(Arrays.asList(1, 0, 2));
        B.add(Arrays.asList(1, 0, -2, 4));
        B.add(Arrays.asList(-2, -5, -11, 3));
        B.add(Arrays.asList(6, -7, 8, 0));
        List<List<Integer>> C = MatrixMultiplication.ParallelizeMatMul(A,B);
        for (List<Integer> list : C){
            for (Integer i : list){
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}
