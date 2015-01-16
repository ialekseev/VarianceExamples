using System;
using System.Collections.Generic;

namespace VarianceExamples
{
    class Program
    {
        public class Animal { }
        public class Cat: Animal { }
        public class Dog : Animal { }


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
            interface IAnimalFarm<out T> where T: Animal
            {
                T ProduceAnimal();
            }

            class AnimalFarm : IAnimalFarm<Animal>
            {
                public Animal ProduceAnimal()
                {
                    return new Animal();
                }
            }
            
            class CatFarm : IAnimalFarm<Cat> 
            {
                public Cat ProduceAnimal()
                {
                    return new Cat();
                }
            }

            public static void Test()
            {
                IAnimalFarm<Cat> catFarm = new CatFarm();
                IAnimalFarm<Animal> animalFarm = catFarm; //OK, because covariant
                Animal animal = animalFarm.ProduceAnimal();
            }
        }

        class GenericsContravariance
        {
            interface IAnimalFarm<in T> where T : Animal
            {
                void FeedAnimal(T animal);
            }

            class AnimalFarm : IAnimalFarm<Animal>
            {
                public void FeedAnimal(Animal animal)
                {
                    //feed animal
                }
            }

            class CatFarm : IAnimalFarm<Cat>
            {
                public void FeedAnimal(Cat animal)
                {
                    //feed cat
                }
            }

            public static void Test()
            {
                IAnimalFarm<Animal> animalFarm = new AnimalFarm();
                IAnimalFarm<Cat> catFarm = animalFarm; //OK, because contravariant
                catFarm.FeedAnimal(new Cat()); 
            }
        }

        class GenericsInvariance //example invalid at compile-time
        {
            interface IAnimalFarm<T> where T : Animal
            {
                T ProduceAnimal();
                void FeedAnimal(T animal);
            }

            class AnimalFarm : IAnimalFarm<Animal>
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

            class CatFarm : IAnimalFarm<Cat>
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
                /*IAnimalFarm<Cat> catFarm = new CatFarm();
                IAnimalFarm<Animal> animalFarm = catFarm; //NOT OK, because invariant
                Animal animal = animalFarm.ProduceAnimal();*/
            }

            public static void Test2()
            {
                /*IAnimalFarm<Animal> animalFarm = new AnimalFarm();
                IAnimalFarm<Cat> catFarm = animalFarm; //NOT OK, because invariant
                catFarm.FeedAnimal(new Cat());*/
            }
        }

        static void Main(string[] args)
        {
            ArraysCovariance.Test();
            ArraysContravariance.Test();
            
            GenericsCovariance.Test();
            GenericsContravariance.Test();            
            GenericsInvariance.Test1();
            GenericsInvariance.Test2();
        }
    }
}
