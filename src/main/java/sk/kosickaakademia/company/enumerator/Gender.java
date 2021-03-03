package sk.kosickaakademia.company.enumerator;

import sk.kosickaakademia.company.entity.User;

public enum Gender {
    Male(0), Female(1), Other(2);

    private int value;

    Gender(int value){
        this.value=value;
    }

    public int getValue(){
        return value;
    }

}
