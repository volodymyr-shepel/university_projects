
public class QuadraticSolver
{
    public static void Main(string[] args)
    {
        double a = GetUserInput("Enter coefficient a: ");
        double b = GetUserInput("Enter coefficient b: ");
        double c = GetUserInput("Enter coefficient c: ");

        
        double[] solutions = SolveQuadraticEquation(a, b, c);

        PrintResults(solutions);
    }

    private static double GetUserInput(string prompt)
    {
        Console.Write(prompt);
        return Convert.ToDouble(Console.ReadLine());
    }

    private static void PrintResults(double[] solutions)
    {
        if (solutions.Length == 0)
        {
            Console.WriteLine("No real solutions.");
        }
        else if (solutions.Length == 1)
        {
            Console.WriteLine($"One real solution: {solutions[0]:F2}");
        }
        else if(solutions.Length == 3)
        {
            Console.WriteLine("Infinite number of solutions");
        }
        else
        {
            Console.WriteLine($"Two real solutions: {solutions[0]:F2} and {solutions[1]:F2}");
        }
    }


    public static double[] SolveQuadraticEquation(double a, double b, double c)
    {
        if (a == 0)
        {
            return HandleLinearEquation(b, c);
        }

        double discriminant = b * b - 4 * a * c;
        int numberOfSolutions = (discriminant > 0) ? 2 : (discriminant == 0) ? 1 : 0;

       
        if (numberOfSolutions == 0)
        {
            return new double[0]; // No real solutions
        }
        else if (numberOfSolutions == 1)
        {
            double root = -b / (2 * a);
            return new double[] { root };
        }
        else
        {
            return CalculateRoots(a, b, discriminant);
        }
    }

    private static double[] HandleLinearEquation(double b, double c)
    {
        if (b == 0 && c == 0)
        {
            return new double[] { 0, 0, 0 }; // Infinite number of solutions
        }
        else if (b == 0)
        {
            return new double[0]; // No real solutions
        }
        else
        {
            return new double[] { -c / b };
        }
    }

    private static double[] CalculateRoots(double a, double b, double discriminant)
    {
        double root1 = (-b + Math.Sqrt(discriminant)) / (2 * a);
        double root2 = (-b - Math.Sqrt(discriminant)) / (2 * a);
        return new double[] { root1, root2 };
    }
}
