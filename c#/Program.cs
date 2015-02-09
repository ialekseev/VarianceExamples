using System;
using System.Collections.Generic;

namespace VarianceExamples
{
    class Program
    {
        class Animal { }
        class Cat: Animal { }
        class Dog : Animal { }


        class ArraysCovariance //valid at compile-time, but fails at runtime
        {
            public static void Test() 
            {
                Cat[] cats = new Cat[] { new Cat(), new Cat() };
                Animal[] animals = cats;
                animals[0] = new Dog();
            }
        }

        class ArraysContravariance //invalid at compile-time
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
            
            public static void Test()
            {
                IAnimalFarm<Animal> animalFarm = new AnimalFarm();
                IAnimalFarm<Cat> catFarm = animalFarm; //OK, because contravariant
                catFarm.FeedAnimal(new Cat()); 
            }
        }

        class GenericsInvariance //invalid at compile-time
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
                animalFarm.FeedAnimal(new Cat());*/
            }

            public static void Test2()
            {
                /*IAnimalFarm<Animal> animalFarm = new AnimalFarm();
                IAnimalFarm<Cat> catFarm = animalFarm; //NOT OK, because invariant
                Cat animal = catFarm.ProduceAnimal();*/
            }
        }

        class ReturnTypeCovariance //invalid at compile-time
        {
            class AnimalFarm
            {
                public virtual Animal ProduceAnimal()
                {
                    return new Animal();
                }
            }

            class CatFarm: AnimalFarm
            {
                /*public override Cat ProduceAnimal()
                {
                    return new Cat();
                }*/
            }

            public static void Test()
            {                
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
            
            ReturnTypeCovariance.Test();
        }
    }
}
