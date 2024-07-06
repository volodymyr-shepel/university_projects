using LinqExamples;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Lab7
{
    public class Topic
    {
        public int Id { get; set; }
        public string Name { get; set; }

        public Topic(int id, string name)
        {
            Id = id;
            Name = name;
        }
    }

    public class Student
    {
        public int Id { get; set; }
        public int Index { get; set; }
        public string Name { get; set; }
        public Gender Gender { get; set; }
        public bool Active { get; set; }
        public int DepartmentId { get; set; }

        public List<int> TopicIds { get; set; }

        public Student(int id, int index, string name, Gender gender, bool active, int departmentId, List<int> topicIds)
        {
            Id = id;
            Index = index;
            Name = name;
            Gender = gender;
            Active = active;
            DepartmentId = departmentId;
            TopicIds = topicIds;
        }

        public override string ToString()
        {
            return $"{Id}: {Name}, {Gender}, {(Active ? "Active" : "Not Active")}, DepartmentId: {DepartmentId}, TopicIds: {string.Join(", ", TopicIds)}";
        }
    }
    public class Calculator
    {
        public double Result { get; set; }
        public static int myVal = 0;

        public double Calculate(double a, double b, string operation)
        {
            switch (operation)
            {
                case "Add":
                    return a + b;
                case "Subtract":
                    return a - b;
                case "Multiply":
                    return a * b;
                case "Divide":
                    if (b != 0)
                        return a / b;
                    else
                        throw new ArgumentException("Cannot divide by zero");
                default:
                    throw new ArgumentException("Invalid operation");
            }
        }
    }

    public static class Generator
    {
        public static List<Topic> GetAllTopics()
        {
            // Sample list of all topics available
            return new List<Topic>
            {
                new Topic(1, "C#"),
                new Topic(2, "PHP"),
                new Topic(3, "algorithms"),
                new Topic(4, "C++"),
                new Topic(5, "fuzzy logic"),
                new Topic(6, "Basic"),
                new Topic(7, "Java"),
                new Topic(8, "JavaScript"),
                new Topic(9, "neural networks"),
                new Topic(10, "web programming")
            };
        }
        public static List<Topic> GetAllTopicsByAsking()
        {
            // just take all the topics from all the students
            Console.WriteLine("Enter the number of topics:");
            int numberOfTopics = int.Parse(Console.ReadLine());

            List<Topic> topics = new List<Topic>();

            for (int i = 1; i <= numberOfTopics; i++)
            {
                Console.Write($"Enter the name of topic {i}: ");
                string topicName = Console.ReadLine();
                topics.Add(new Topic(i, topicName));
            }

            return topics;
        }

        public static List<int> GetTopicIds(List<Topic> allTopics, List<string> topicNames)
        {
            // Get topic IDs based on topic names

            // modify to use joins (more efficient)
            return allTopics
                .Where(topic => topicNames.Contains(topic.Name))
                .Select(topic => topic.Id)
                .ToList();
        }
    }
    
}
