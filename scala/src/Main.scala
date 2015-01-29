object Main {

  class Animal
  class Cat extends Animal
  class Dog extends Animal


  class ArraysCovariance //example invalid at compile-time
  {
    def test() ={
      /*val cats: Array[Cat] = Array[Cat](new Cat(), new Cat())
      val animals: Array[Animal] = cats;
      animals(0) = new Dog();*/
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


  def main(args: Array[String]) {
    new ArraysCovariance().test()
    new ArraysContravariance().test()
  }
}
