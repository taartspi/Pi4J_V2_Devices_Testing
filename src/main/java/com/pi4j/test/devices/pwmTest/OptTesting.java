package com.pi4j.test.devices.pwmTest;

import java.util.Optional;








public class OptTesting {



    public static void main(String[] args) throws Exception {

       // Optional.of(Integer.parseInt(chipNum));

        Optional<Integer> test =  Optional.of(42);
        System.out.println("foo()");

        Optional<Optional<Integer>> optionalInt = Optional.of(foo());
         if(optionalInt.get().equals(test)){
             System.out.println("foo() Expected   equal");
             System.out.println(optionalInt.get().get());
         }else  {
             System.out.println("foo()Error  NOT equal");
             System.out.println(optionalInt.get().get());
         }
        if ( optionalInt.get().isPresent()) {
            System.out.println("foo() Expected: True isPresent");
        } else{
            System.out.println("foo()Error , False isPresent");
        }


        System.out.println("foo1()");
        Optional<Optional<Integer>> optionalInt1 = Optional.of(foo1());
        if ( optionalInt1.get().isPresent()) {
             System.out.println("foo1()Error: True isPresent");
         } else{
             System.out.println("foo1() Expected , False isPresent");
         }

    }

    static public Optional<Integer> foo(){
        return  Optional.of(42);
    }

    static public Optional<Integer> foo1(){
        return  Optional.empty();
    }


}

