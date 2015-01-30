import scala.collection.immutable.List

object Main {

  class Animal
  class Cat extends Animal
  class Dog extends Animal


  class ArraysCovariance //example invalid at compile-time
  {
    def test() ={
      /*val cats: Array[Cat] = Array[Cat](new Cat(), new Cat())
      val animals: Array[Animal] = cats;
      animals.update(0,  new Dog())*/
    }
  }

  class ArraysContravariance //example invalid at compile-time
  {
    def test() ={
      /*val animals: Array[Animal] = Array[Animal](new Cat(), new Cat());
      val dogs: Array[Dog] = animals;
      dogs(0) = new Dog();*/
    }
  }

  class ListsCovariance
  {
    def test() = {
      val cats:List[Cat] = List[Cat](new Cat(), new Cat())
      val animals:List[Animal] = cats //OK
      val newAnimals = animals.updated(0, new Dog())
    }
  }

  class GenericsInvariance //example invalid at compile-time
  {
    class AnimalFarm[T]{
      def produceAnimal(): Cat = new Cat()
      def feedAnimal[T](animal: T): AnimalFarm[T] = new AnimalFarm[T]
    }

    def test()={
      /*val catFarm:AnimalFarm[Cat] = new AnimalFarm[Cat]
      val animalFarm: AnimalFarm[Animal] = catFarm //invalid
      animalFarm.produceAnimal()*/
    }
  }

  class GenericsCovariance
  {
    class AnimalFarm[+T]{
      def produceAnimal(): Cat = new Cat()
      def feedAnimal[S>:T](animal: S): AnimalFarm[S] = new AnimalFarm[S]
    }

    def test() = {
      val catFarm:AnimalFarm[Cat] = new AnimalFarm[Cat]
      val animalFarm: AnimalFarm[Animal] = catFarm //OK, because covariant
      animalFarm.produceAnimal()
      val satiatedAnimal = animalFarm.feedAnimal(new Dog()) //still OK!
    }
  }

  class GenericsContravariance
  {
    trait AnimalFarm[-T]
    {
      def feedAnimal(animal: T)
    }

    class AnimalFarmDefault extends AnimalFarm[Animal]{
      def feedAnimal(animal: Animal): Unit ={
        //feed animal
      }
    }

    def test() = {
      val animalFarm:AnimalFarm[Animal] = new AnimalFarmDefault()
      val catFarm: AnimalFarm[Cat] = animalFarm
      catFarm.feedAnimal(new Cat())
    }
  }


  def main(args: Array[String]) {
    new ArraysCovariance().test()
    new ArraysContravariance().test()
    new ListsCovariance().test()
    new GenericsCovariance().test()
    new GenericsContravariance().test()
  }
}
