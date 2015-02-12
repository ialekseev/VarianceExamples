class Animal { }
class Cat extends Animal { }
class Dog extends Animal { }

class ArraysCovariance //valid at compile-time, but fails at runtime
{
    public void test()
    {
        Cat[] cats = new Cat[] { new Cat(), new Cat() };
        Animal[] animals = cats;
        animals[0] = new Dog(); //runtime error here
    }
}

class ArraysContravariance //invalid at compile-time
{
    public void test()
    {
        /*Animal[] animals = new Animal[] { new Cat(), new Cat() };
        Dog[] dogs = animals; //compile-time error here
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
        AnimalFarm<? super Cat> catFarm = animalFarm; //OK
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
        AnimalFarm<? super Cat> catFarm = animalFarm; //OK
        catFarm.feedAnimal(new Cat());
    }
}

class ReturnTypeCovariance //invalid at compile-time
{
    class AnimalFarm
    {
        public Animal produceAnimal()
        {
            return new Animal();
        }
    }

    class CatFarm extends AnimalFarm
    {
        @Override
        public Cat produceAnimal()
         {
             return new Cat();
         }
    }

    public void test()
    {
        CatFarm catFarm = new CatFarm();
        Cat cat = catFarm.produceAnimal();
    }
}

class ParameterTypeContravariance
{
    class AnimalFarm
    {
        public void feedAnimal(Cat animal)
        {
        }
    }

    class CatFarm extends AnimalFarm
    {
        /*@Override
        public void feedAnimal(Animal animal)
        {
        }*/
    }

    public void test()
    {
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

        new ReturnTypeCovariance().test();

        new ParameterTypeContravariance().test();
    }
}
