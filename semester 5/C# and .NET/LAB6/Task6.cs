using System;

class Task6
{

    public static (int evenIntsCount, int positiveDoublesCount, int longStringsCount, int otherTypesCount) CountMyTypes(params object[] elements)
    {
        // Initialize counters
        int evenIntsCount = 0;
        int positiveDoublesCount = 0;
        int longStringsCount = 0;
        int otherTypesCount = 0;

        // Iterate through the elements
        foreach (var element in elements)
        {
            switch (element)
            {
                case int intValue when intValue % 2 == 0:
                    evenIntsCount++;
                    break;
                case double doubleValue when doubleValue > 0:
                    positiveDoublesCount++;
                    break;
                case string stringValue when stringValue.Length >= 5:
                    longStringsCount++;
                    break;
                default:
                    otherTypesCount++;
                    break;
            }
        }

        // Return a tuple with the counts
        return (evenIntsCount, positiveDoublesCount, longStringsCount, otherTypesCount);
    }
}
