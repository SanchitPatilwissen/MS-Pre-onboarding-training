interface Bird{
    void eat();
    void makeSound();
}

interface FlyAble{
    void fly();
}

class Crow implements Bird, FlyAble{
    public void eat(){
        System.out.println("Crow is eating");
    }

    public void makeSound(){
        System.out.println("Caw Caw");
    }

    public void fly(){
        System.out.println("Crow is flying");
    }
}

class Penguin implements Bird{
    public void eat(){
        System.out.println("Penguin is eating");
    }

    public void makeSound(){
        System.out.println("Penguin sound");
    }
}

class Parrot implements Bird, FlyAble{
    public void eat(){
        System.out.println("Parrot is eating");
    }

    public void makeSound(){
        System.out.println("Parrot makes sound");
    }

    public void fly(){
        System.out.println("Parrot is flying");
    }
}

// Each class has only single responsibility --> S

// If we need to add more bird types, we can do so without modifying existing classes --> O

// Bird classes depend on Bird and FlyAble interfaces, not on concrete implementations --> L

// Bird interface is not forced to implement fly method if it doesn't fly --> I

// Abstracting flying behavior into FlyAble interface --> D

public class Assignment {
    public static void main(String[] args){
        Bird crow = new Crow();
        crow.eat();
        crow.makeSound();
        ((FlyAble) crow).fly();

        Bird penguin = new Penguin();
        penguin.eat();
        penguin.makeSound();

        Bird parrot = new Parrot();
        parrot.eat();
        parrot.makeSound();
        ((FlyAble) parrot).fly();
    }    
}
