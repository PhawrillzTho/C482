package controller;

/** This class is used as a global counter to generate and distribute new unique product and part ID's. */
public class Counter {
    public static int part_id_counter = 10;
    public static int product_id_counter = 99;

    /** This class will provide a new part Id.
     * @return  int A new part Id will be returned. */
    public static int get_new_part_id(){
        return ++part_id_counter;
    }

    /** This class will provide a new product Id.
     * @return  int A new product Id will be returned. */
    public static int get_new_product_id(){
        return ++product_id_counter;
    }
}