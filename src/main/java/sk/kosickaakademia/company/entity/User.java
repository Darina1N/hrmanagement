package sk.kosickaakademia.company.entity;

import sk.kosickaakademia.company.enumerator.Gender;

public class User {
    private int id;
    private String fname;
    private String lname;
    private int age;
    private Gender gender;

    public User(int id, String fname, String lname, int age, int gender) {
        this(fname, lname, age, gender); // volám druhý konštruktor, musí byť prvý ešte pred zadaním zvyšných parametrov tohto konštruktora
        this.id = id;
    }

    public User(String fname, String lname, int age, int gender) {
        this.fname = fname;
        this.lname = lname;
        this.age = age;
        this.gender = gender==0?Gender.Male : gender==1?Gender.Female : Gender.Other;
        //terárny operátor ktorý mi pri hodnote 0 dá M, hodnote 1 dá F a pri akomkoľvek inom čísle dá Other
    }

    public int getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }
}





