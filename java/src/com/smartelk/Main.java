package com.smartelk;

class Animal { }
class Cat extends Animal { }
class Dog extends Animal { }

class ArraysCovariance //example valid at compile-time, but fails at runtime
{
    public static void Test()
    {
        Cat[] cats = new Cat[] { new Cat(), new Cat() };
        Animal[] animals = cats;
        animals[0] = new Dog();
    }
}

class ArraysContravariance //example invalid at compile-time
{
    public static void Test()
    {
        /*Animal[] animals = new Animal[] { new Cat(), new Cat() };
        Dog[] dogs = animals;
        dogs[0] = new Dog();*/
    }
}

class GenericsCovariance
{
    interface IAnimalFarm<T>
    {
        T ProduceAnimal();
    }

    class AnimalFarm implements IAnimalFarm<Animal>
    {
        public Animal ProduceAnimal()
        {
            return new Animal();
        }
    }

    class CatFarm implements IAnimalFarm<Cat>
    {
        public Cat ProduceAnimal()
        {
            return new Cat();
        }
    }

    public static void Test()
    {
        IAnimalFarm<Cat> catFarm = new GenericsCovariance().new CatFarm();
        IAnimalFarm<? extends Animal> animalFarm = catFarm; //OK
        Animal animal = animalFarm.ProduceAnimal();
    }
}

class GenericsContravariance
{
    interface IAnimalFarm<T>
    {
        void FeedAnimal(T animal);
    }

    class AnimalFarm implements IAnimalFarm<Animal>
    {
        public void FeedAnimal(Animal animal)
        {
            //feed animal
        }
    }

    class CatFarm implements IAnimalFarm<Cat>
    {
        public void FeedAnimal(Cat animal)
        {
            //feed cat
        }
    }

    public static void Test()
    {
        IAnimalFarm<Animal> animalFarm = new GenericsContravariance().new AnimalFarm();
        IAnimalFarm<? super Animal> catFarm = animalFarm; //OK
        catFarm.FeedAnimal(new Cat());
    }
}

class GenericsVariance //Generic type parameter is presented both in input & output positions
{
    interface IAnimalFarm<T>
    {
        T ProduceAnimal();
        void FeedAnimal(T animal);
    }

    class AnimalFarm implements IAnimalFarm<Animal>
    {
        public Animal ProduceAnimal()
        {
            return new Animal();
        }

        public void FeedAnimal(Animal animal)
        {
            //feed animal
        }
    }

    class CatFarm implements IAnimalFarm<Cat>
    {
        public Cat ProduceAnimal()
        {
            return new Cat();
        }

        public void FeedAnimal(Cat animal)
        {
            //feed cat
        }
    }

    public static void Test1()
    {
        IAnimalFarm<Cat> catFarm = new GenericsVariance().new CatFarm();
        IAnimalFarm<? extends Animal> animalFarm = catFarm; //OK
        Animal animal = animalFarm.ProduceAnimal();
    }

    public static void Test2()
    {
        IAnimalFarm<Animal> animalFarm = new GenericsVariance().new AnimalFarm();
        IAnimalFarm<? super Animal> catFarm = animalFarm; //OK
        catFarm.FeedAnimal(new Cat());
    }
}

public class Main {
    public static void main(String[] args) {
        ArraysCovariance.Test();
        ArraysContravariance.Test();

        GenericsCovariance.Test();
        GenericsContravariance.Test();

        GenericsVariance.Test1();
        GenericsVariance.Test2();
    }
}
