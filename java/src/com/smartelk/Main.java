package com.smartelk;

class Animal { }
class Cat extends Animal { }
class Dog extends Animal { }

class ArraysCovariance //example valid at compile-time, but fails at runtime
{
    public void test()
    {
        Cat[] cats = new Cat[] { new Cat(), new Cat() };
        Animal[] animals = cats;
        animals[0] = new Dog();
    }
}

class ArraysContravariance //example invalid at compile-time
{
    public void test()
    {
        /*Animal[] animals = new Animal[] { new Cat(), new Cat() };
        Dog[] dogs = animals;
        dogs[0] = new Dog();*/
    }
}

class GenericsCovariance
{
    interface AnimalFarm<T>
    {
        T produceAnimal();
    }

    class CatFarm implements AnimalFarm<Cat>
    {
        public Cat produceAnimal()
        {
            return new Cat();
        }
    }

    public void test()
    {
        AnimalFarm<Cat> catFarm = new CatFarm();
        AnimalFarm<? extends Animal> animalFarm = catFarm; //OK
        Animal animal = animalFarm.produceAnimal();
    }
}

class GenericsContravariance
{
    interface AnimalFarm<T>
    {
        void feedAnimal(T animal);
    }

    class AnimalFarmDefault implements AnimalFarm<Animal>
    {
        public void feedAnimal(Animal animal)
        {
            //feed animal
        }
    }

    public void test()
    {
        AnimalFarm<Animal> animalFarm = new AnimalFarmDefault();
        AnimalFarm<? super Animal> catFarm = animalFarm; //OK
        catFarm.feedAnimal(new Cat());
    }
}

class GenericsVariance //Generic type parameter is presented both in input & output positions
{
    interface AnimalFarm<T>
    {
        T produceAnimal();
        void feedAnimal(T animal);
    }

    class AnimalFarmDefault implements AnimalFarm<Animal>
    {
        public Animal produceAnimal()
        {
            return new Animal();
        }

        public void feedAnimal(Animal animal)
        {
            //feed animal
        }
    }

    class CatFarm implements AnimalFarm<Cat>
    {
        public Cat produceAnimal()
        {
            return new Cat();
        }

        public void feedAnimal(Cat animal)
        {
            //feed cat
        }
    }

    public void test1()
    {
        AnimalFarm<Cat> catFarm = new CatFarm();
        AnimalFarm<? extends Animal> animalFarm = catFarm; //OK
        Animal animal = animalFarm.produceAnimal();
    }

    public void test2()
    {
        AnimalFarm<Animal> animalFarm = new AnimalFarmDefault();
        AnimalFarm<? super Animal> catFarm = animalFarm; //OK
        catFarm.feedAnimal(new Cat());
    }
}

public class Main {
    public static void main(String[] args) {
        new ArraysCovariance().test();
        new ArraysContravariance().test();

        new GenericsCovariance().test();
        new GenericsContravariance().test();

        new GenericsVariance().test1();
        new GenericsVariance().test2();
    }
}
