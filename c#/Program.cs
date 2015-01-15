namespace VarianceExamples
{
    class Program
    {
        public class Animal { }
        public class Cat: Animal { }
        public class Dog : Animal { }


        static void ArrayCovariance() //valid at compile-time, but fails at runtime
        {
            Cat[] cats = new Cat[] { new Cat(), new Cat() };
            Animal[] animals = cats;
            animals[0] = new Dog(); 
        }

        /*static void ArrayContravariance() //invalid at compile-time
        {
            Animal[] animals = new Animal[] { new Cat(), new Cat() };
            Dog[] dogs = animals;
            dogs[0] = new Dog();            
        }*/

        static void Main(string[] args)
        {
            ArrayCovariance();            
        }
    }
}
