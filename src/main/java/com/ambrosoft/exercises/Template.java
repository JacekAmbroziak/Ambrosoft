package com.ambrosoft.exercises;

public class Template {


    static void zasada(String czlowiek, int ileZostalo, int depth) {
        if (ileZostalo > 0) {

            zasada("matka:" + czlowiek, ileZostalo - 1, depth + 1);

            int spaces = depth;
            while (spaces > 0) {
                System.out.print("   ");
                spaces = spaces - 1;
            }
            System.out.println(ileZostalo + " czlowiek = " + czlowiek);

            zasada("ojciec:" + czlowiek, ileZostalo - 1, depth + 1);
        }
    }


    public static void main(String[] args) {

        zasada("Marzena", 5, 0);

    }


}
