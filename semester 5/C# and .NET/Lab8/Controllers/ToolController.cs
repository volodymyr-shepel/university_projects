using Lab8.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.CodeAnalysis.Elfie.Serialization;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace Lab8.Controllers
{
    public class ToolController : Controller
    {
        public IActionResult Index()
        {
            return View();
        }

        [HttpGet("Tool/Solve/{a}/{b}/{c}")]
        public IActionResult Solve(int a, int b, int c)
        {
            double[] solutions = ToolService.SolveQuadraticEquation(a, b, c);
            ViewData["a"] = a;
            ViewData["b"] = b;
            ViewData["c"] = c;
            ViewData["Solutions"] = solutions;

            if (solutions.Length == 0)
            {
                ViewData["solutionClass"] = "no-solutions";
                ViewData["solutionText"] = "No real solutions.";
           
            }
            else if (solutions.Length == 1)
            {
                ViewData["solutionClass"] = "one-solution";
                ViewData["solutionText"] = "One real solution: " + solutions[0];
                
            }
            else if (solutions.Length == 3)
            {
                ViewData["solutionClass"] = "infinite-solutions";
                ViewData["solutionText"] = "Infinite number of solutions.";
            }
            else
            {
            ViewData["solutionClass"] = "two-solutions";
            ViewData["solutionText"] = "Two real solutions: " + solutions[0] + " and " + solutions[1];
            }

            return View();
        }

    }
    
}
