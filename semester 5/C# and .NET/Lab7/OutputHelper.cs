using Lab7;
using LinqExamples;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

class OutputHelper
{
    // Task1
    public static void Print2DList<T>(List<List<T>> twoDList)
    {
        foreach (var row in twoDList)
        {
            foreach (var item in row)
            {
                Console.WriteLine(item);
            }
            Console.WriteLine("\n----------------------");
        }
    }

    public static void PrintTask2aResult(IEnumerable<(string Topic, int Frequency)> topicFrequencies)
    {
        foreach (var topic in topicFrequencies)
        {
            Console.WriteLine($"{topic.Topic}: {topic.Frequency}");
        }
    }

    public static void PrintTask2bResult(IEnumerable<(Gender Gender, IEnumerable<(string Topic, int Frequency)> Topics)> sortedTopicsByGender)
    {
        foreach (var genderGroup in sortedTopicsByGender)
        {
            Console.WriteLine($"Gender: {genderGroup.Gender}");

            foreach (var topic in genderGroup.Topics)
            {
                Console.WriteLine($"{topic.Topic}: {topic.Frequency}");
            }

            Console.WriteLine();
        }
    }
    public static void PrintStudents(List<Student> students)
    {
        foreach (var student in students)
        {
            Console.WriteLine(student);
        }
    }


}