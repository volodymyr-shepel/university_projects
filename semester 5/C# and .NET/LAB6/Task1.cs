using System;

class Task1
{
    
    public static void DisplayPersonInfo((string Name, string Surname, int Age, double Salary) person)
    {
        Console.WriteLine($"Name: {person.Name}");
        Console.WriteLine($"Surname: {person.Surname}");
        Console.WriteLine($"Age: {person.Age}");
        Console.WriteLine($"Salary: {person.Salary}");
        Console.WriteLine("---------------------");
    }

    
    public static void DisplayPersonInfo(string name, string surname, int age, double salary)
    {
        Console.WriteLine($"Name: {name}");
        Console.WriteLine($"Surname: {surname}");
        Console.WriteLine($"Age: {age}");
        Console.WriteLine($"Salary: {salary}");
        Console.WriteLine("---------------------");
    }
}
