package sk.kosickaakademia.company.enumerator;

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
