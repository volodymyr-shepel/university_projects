using Lab7;
using LinqExamples;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

class TaskProcessor
{
    public static List<List<StudentWithTopics>> PerformTask1(List<StudentWithTopics> students, int n)
    {
        // return as query, enumerate
        var result = students
            .OrderBy(student => student.Name)
            .ThenBy(student => student.Index)
            .Select((student, index) => new { student, index })
            .GroupBy(pair => pair.index / n)
            .Select(group => group.Select(pair => pair.student).ToList())
            .ToList();

        return result;
    }

    public static IEnumerable<(string Topic, int Frequency)> PerformTask2a(List<StudentWithTopics> students)
    {
        var flattenedTopics = students.SelectMany(student => student.Topics);

        var topicFrequencies = flattenedTopics
            .GroupBy(topic => topic)
            .OrderByDescending(group => group.Count())
            .Select(group => (Topic: group.Key, Frequency: group.Count()));

        return topicFrequencies;
    }

    public static IEnumerable<(Gender Gender, IEnumerable<(string Topic, int Frequency)> Topics)> PerformTask2b(List<StudentWithTopics> students)
    {
        var sortedTopicsByGender = students
            .GroupBy(student => student.Gender)
            .Select(genderGroup => (
                Gender: genderGroup.Key,
                Topics: genderGroup
                    .SelectMany(student => student.Topics)
                    .GroupBy(topic => topic)
                    .OrderByDescending(group => group.Count())
                    .Select(group => (Topic: group.Key, Frequency: group.Count()))
            ));

        return sortedTopicsByGender;
    }
    public static List<Student> PerformTask3a(List<StudentWithTopics> studentsWithTopics)
    {
        
        var allTopics = Lab7.Generator.GetAllTopics();

        // Transform StudentWithTopics objects into Student objects
        var students = studentsWithTopics.Select(studentWithTopics =>
            new Student(
                studentWithTopics.Id,
                studentWithTopics.Index,
                studentWithTopics.Name,
                studentWithTopics.Gender,
                studentWithTopics.Active,
                studentWithTopics.DepartmentId,
                Lab7.Generator.GetTopicIds(allTopics, studentWithTopics.Topics)
            )
        ).ToList();

        return students;
    }

    public static List<Student> PerformTask3b(List<StudentWithTopics> studentsWithTopics)
    {

        var allTopics = Lab7.Generator.GetAllTopicsByAsking();

        // Transform StudentWithTopics objects into Student objects
        var students = studentsWithTopics.Select(studentWithTopics =>
            new Student(
                studentWithTopics.Id,
                studentWithTopics.Index,
                studentWithTopics.Name,
                studentWithTopics.Gender,
                studentWithTopics.Active,
                studentWithTopics.DepartmentId,
                Lab7.Generator.GetTopicIds(allTopics, studentWithTopics.Topics)
            )
        ).ToList();

        return students;
    }

    public static void PerformTask4()
    {
   
        string className = "Lab7.Calculator"; // Lab7 because of namespace
        Type classType = Type.GetType(className);

        if (classType == null)
        {
            Console.WriteLine($"Class '{className}' not found");
        }

        // Create an instance of the selected class
        object instance = Activator.CreateInstance(classType);

        // Choose a method to invoke using reflection
        string methodName = "Calculate";
        MethodInfo methodInfo = classType.GetMethod(methodName);

        if (methodInfo == null)
        {
            Console.WriteLine($"Method '{methodName}' not found in class '{className}'");
        }

        // Prepare parameters for the method
        object[] parameters = { 10.0, 5.0, "Add" };

        // Invoke the method and store the result
        object result = methodInfo.Invoke(instance, parameters);

        // Display the result
        Console.WriteLine($"Result of {className}.{methodName}({parameters[0]}, {parameters[1]}, '{parameters[2]}'): {result}");


        FieldInfo info = classType.GetField("myVal", BindingFlags.Public | BindingFlags.Static);
        info.SetValue(null, 99); // The first argument is null for static fields

        // Display the updated value
        Console.WriteLine($"New value of field: {Calculator.myVal}");
    }
}



