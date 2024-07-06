using System;

class Task4
{
    public static void DisplayPersonInfo(dynamic person)
    {
        
       
        Console.WriteLine($"Name: {person.Name}");
        Console.WriteLine($"Surname: {person.Surname}");
        Console.WriteLine($"Age: {person.Age}");
        Console.WriteLine($"Salary: {person.Salary}");
        Console.WriteLine("---------------------");
    }
}
