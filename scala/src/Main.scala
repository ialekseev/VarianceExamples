import scala.collection.immutable.List

object Main {

  class Animal
  class Cat extends Animal
  class Dog extends Animal


  class ArraysCovariance //invalid at compile-time
  {
    def test() ={
      /*val cats: Array[Cat] = Array[Cat](new Cat(), new Cat())
      val animals: Array[Animal] = cats //invalid
      animals.update(0,  new Dog())*/
    }
  }

  class ArraysContravariance //invalid at compile-time
  {
    def test() ={
      /*val animals: Array[Animal] = Array[Animal](new Cat(), new Cat());
      val dogs: Array[Dog] = animals; //invalid
      dogs(0) = new Dog();*/
    }
  }

  class ImmutableListsCovariance
  {
    def test() = {
      val cats:List[Cat] = List[Cat](new Cat(), new Cat())
      val animals:List[Animal] = cats //OK
      val newAnimals = animals.updated(0, new Dog())
    }
  }

  class MutableListsCovariance //invalid at compile-time
  {
    def test() = {
      /*val cats:scala.collection.mutable.ListBuffer[Cat] = scala.collection.mutable.ListBuffer[Cat](new Cat(), new Cat())
      val animals:scala.collection.mutable.ListBuffer[Animal] = cats //invalid
      animals.update(0, new Dog())*/
    }
  }

  class GenericsInvariance //invalid at compile-time
  {
    trait AnimalFarm[T]
    {
      def produceAnimal(): T
      def feedAnimal(animal: T): Unit
    }

    class AnimalFarmDefault extends AnimalFarm[Animal]{
      def produceAnimal(): Animal = new Animal()
      def feedAnimal(animal: Animal): Unit = {
        //feed animal
      }
    }

    class CatFarm extends AnimalFarm[Cat]{
      def produceAnimal(): Cat = new Cat()
      def feedAnimal(animal: Cat): Unit = {
        //feed animal
      }
    }

    def test()={
      /*val catFarm:AnimalFarm[Cat] = new CatFarm()
      val animalFarm: AnimalFarm[Animal] = catFarm //invalid
      val animal: Animal = animalFarm.produceAnimal()*/
    }
  }

  class GenericsCovariance
  {
    trait AnimalFarm[+T]
    {
      def produceAnimal(): T
    }

    class CatFarm extends AnimalFarm[Cat]{
      def produceAnimal(): Cat = new Cat()
    }

    def test() = {
      val catFarm:AnimalFarm[Cat] = new CatFarm()
      val animalFarm: AnimalFarm[Animal] = catFarm //OK
      val animal: Animal = animalFarm.produceAnimal()
    }
  }

  class GenericsContravariance
  {
    trait AnimalFarm[-T]
    {
      def feedAnimal(animal: T): Unit
    }

    class AnimalFarmDefault extends AnimalFarm[Animal]
    {
      def feedAnimal(animal: Animal): Unit = {
        //feed animal
      }
    }

    def test() = {
      val animalFarm:AnimalFarm[Animal] = new AnimalFarmDefault()
      val catFarm: AnimalFarm[Cat] = animalFarm //OK
      catFarm.feedAnimal(new Cat())
    }
  }

  class GenericsCovarianceWithLowerBound
  {
    trait AnimalFarm[+T]
    {
      def produceAnimal(): T
      def feedAnimal[S>:T](animal: S): Unit
    }

    class AnimalFarmDefault extends AnimalFarm[Animal]{
      def produceAnimal(): Animal = new Animal()
      def feedAnimal[S >: Animal](animal: S): Unit = {
        //feed animal
      }
    }

    class CatFarm extends AnimalFarm[Cat]{
      def produceAnimal(): Cat = new Cat()
      def feedAnimal[S >: Cat](animal: S): Unit = {
        //feed animal
      }
    }

    def test()={
      val catFarm:AnimalFarm[Cat] = new CatFarm()
      val animalFarm: AnimalFarm[Animal] = catFarm //OK
      val animal: Animal = animalFarm.produceAnimal()
      animalFarm.feedAnimal(new Dog) //still OK!
    }
  }

  class ReturnTypeCovariance
  {
    class AnimalFarm
    {
       def produceAnimal(): Animal = new Animal();
    }

    class CatFarm extends AnimalFarm
    {
      override def produceAnimal(): Cat = new Cat();
    }

    def test()
    {
      val catFarm: CatFarm = new CatFarm()
      val cat: Cat = catFarm.produceAnimal()
    }
  }

  class ParameterTypeContravariance //invalid at compile-time
  {
      class AnimalFarm
      {
         def feedAnimal(animal: Cat)= {
         }
      }

      class CatFarm extends AnimalFarm
      {
         /*override def feedAnimal(animal: Animal)={
         }*/
      }

      def test()
      {
      }
  }

  def main(args: Array[String]) {
    new ArraysCovariance().test()
    new ArraysContravariance().test()

    new ImmutableListsCovariance().test()

    new GenericsInvariance().test()
    new GenericsCovariance().test()
    new GenericsCovarianceWithLowerBound().test()
    new GenericsContravariance().test()

    new ReturnTypeCovariance().test()

    new ParameterTypeContravariance().test()
  }
}
