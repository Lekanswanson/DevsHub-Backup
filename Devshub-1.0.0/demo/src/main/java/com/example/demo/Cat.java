package com.example.demo;

import java.util.*;

public class Cat {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int T = scanner.nextInt();
        for(int i = 0; i <= T; i++){
            String SJ = scanner.nextLine();

            if(SJ.length() % 2 == 1){
                for(int k = 0; k < (SJ.length()+1)/2; k++){
                    System.out.print(SJ.charAt(k*2));
                }

                System.out.print(" ");

                for(int j = 0; j < (SJ.length()-1)/2; j++){
                    System.out.print(SJ.charAt(j*2+1));
                }
            }
            else{
                for(int k = 0; k < SJ.length()/2; k++){
                    System.out.print(SJ.charAt(k*2));
                }

                System.out.print(" ");

                for(int j = 0; j < SJ.length()/2; j++){
                    System.out.print(SJ.charAt(j*2+1));
                }
            }

            System.out.print("\n");
        }

        scanner.close();
    }
}