using LinqExamples;
using System;

/*
-- ORDER 
from
where
orderby
select
group
join
let
into
*/
class Program
{

    
    static public void Main(String[] args)
    {
        var students = LinqExamples.Generator.GenerateStudentsWithTopicsEasy();
        var task1Result = TaskProcessor.PerformTask1(students, 3);
        OutputHelper.Print2DList(task1Result);
        // --------------------------------

        var task2aResult = TaskProcessor.PerformTask2a(students);
        //OutputHelper.PrintTask2aResult(task2aResult);
        // -------------------

        var task2bResult = TaskProcessor.PerformTask2b(students);
        //OutputHelper.PrintTask2bResult(task2bResult);

        // -------------------
        var task3aResult = TaskProcessor.PerformTask3a(students);
        //OutputHelper.PrintStudents(task3aResult);
        // -------------------

        var task3bResult = TaskProcessor.PerformTask3b(students);
        //OutputHelper.PrintStudents(task3bResult);

        //-----------
        TaskProcessor.PerformTask4();

    }

    

}