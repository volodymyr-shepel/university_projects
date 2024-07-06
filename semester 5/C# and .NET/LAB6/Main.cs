using System.Diagnostics;

class Program
{
    static void Main()
    {

        //performTask1();
        //performTask2();
        //performTask3();
        performTask4();
        //performTask5();
       // performTask6();
    }
    static void performTask1()
    {
        Console.WriteLine("Performing TASK 1\n");
        var personTuple = ("Levi", "Ackerman", 18, 6666.66);


        Console.WriteLine("Entire tuple as a parameter\n");
        Task1.DisplayPersonInfo(personTuple);

        Console.WriteLine("Pass individual tuple elements as parameters\n");
        Task1.DisplayPersonInfo(personTuple.Item1, personTuple.Item2, personTuple.Item3, personTuple.Item4);


        Console.WriteLine("Use named variables and pass them as parameters\n");
        var (name, surname, age, salary) = personTuple;
        Task1.DisplayPersonInfo(name, surname, age, salary);


        Console.ReadLine();
    }

    static void performTask2()
    {
        Console.WriteLine("Performing TASK 2\n");
        string @class = "C# Programming";

        
        Console.WriteLine(@class);
        Console.WriteLine(nameof(@class));
    }
    static void performTask3()
    {
        Console.WriteLine("Performing Task 3");
        // Method 1: Sort
        int[] numbers = { 4, 2, 7, 1, 9 };
        Array.Sort(numbers);
        Console.WriteLine("Sorted Array:");
        foreach (var number in numbers)
        {
            Console.Write(number + " ");
        }
        Console.WriteLine();

        // Method 2: IndexOf
        string[] fruits = { "Apple", "Orange", "Banana", "Mango" };
        int index = Array.IndexOf(fruits, "Banana");
        Console.WriteLine($"Index of Banana: {index}");

        // Method 3: Copy
        int[] sourceArray = { 1, 2, 3, 4, 5 };
        int[] destinationArray = new int[5];
        Array.Copy(sourceArray, destinationArray, sourceArray.Length);
        Console.WriteLine("Copied Array:");
        foreach (var number in destinationArray)
        {
            Console.Write(number + " ");
        }
        Console.WriteLine();

        // Method 4: Reverse
        char[] characters = { 'A', 'B', 'C', 'D', 'E' };
        Array.Reverse(characters);
        Console.WriteLine("Reversed Array:");
        foreach (var character in characters)
        {
            Console.Write(character + " ");
        }
        Console.WriteLine();

        // Method 5: Clear
        int[] clearArray = { 1, 2, 3, 4, 5 };
        Array.Clear(clearArray, 1, 3);
        Console.WriteLine("Modified Array:");
        foreach (var number in clearArray)
        {
            Console.Write(number + " ");
        }
        Console.WriteLine();
    }

    static void performTask4()
    {
        var personAnonymous = new { Name = "Levi", Surname = "Ackerman", Age = 17, Salary = 6666.66 };

        Task4.DisplayPersonInfo(personAnonymous);
    }

    static void performTask5()
    {
        Task5.DrawCard("Ryszard", "Rys", 'X', 2, 20);
        Task5.DrawCard("John Doe", "JD", '*', 3, 25); 
        Task5.DrawCard("Jane Smith", borderChar: '+', minWidth: 15);
        Task5.DrawCard("Bob", "B", minWidth: 10); 

    }

    static void performTask6()
    {
        var result = Task6.CountMyTypes(1, "Hello", 3.14, 'A', 5, 10.5, "World");

        Console.WriteLine($"Even Ints: {result.evenIntsCount}");
        Console.WriteLine($"Positive Doubles: {result.positiveDoublesCount}");
        Console.WriteLine($"Strings with at least 5 characters: {result.longStringsCount}");
        Console.WriteLine($"Other Types: {result.otherTypesCount}");
    }
}
