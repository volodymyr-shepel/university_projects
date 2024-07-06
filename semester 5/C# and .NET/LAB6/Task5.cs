using System;

class Task5
{
    public static void DrawCard(string firstLine, string secondLine = "", char borderChar = '*', int borderWidth = 2, int minWidth = 20)
    {
        // Calculate the minimum width required for the business card
        int totalWidth = Math.Max(minWidth, Math.Max(firstLine.Length + 2 * borderWidth, secondLine.Length + 2 * borderWidth));

        // Draw the top border
        DrawBorder(borderChar, totalWidth, borderWidth);

        // Draw the first line with padding
        DrawCenteredText(firstLine, borderChar, borderWidth, totalWidth);

        // Draw the second line with padding if provided
        if (!string.IsNullOrEmpty(secondLine))
        {
            DrawCenteredText(secondLine, borderChar, borderWidth, totalWidth);
        }

        // Draw the bottom border
        DrawBorder(borderChar, totalWidth, borderWidth);
    }

    static void DrawBorder(char borderChar, int width, int height)
    {
        for (int i = 0; i < height; i++)
        {
            string border = new string(borderChar, width);
            Console.WriteLine(border);
        }
    }

    static void DrawCenteredText(string text, char borderChar, int borderWidth, int totalWidth)
    {
        int padding = (totalWidth - text.Length - 2 * borderWidth) / 2;
        string paddedText = $"{new string(borderChar, borderWidth)}{new string(' ', padding + 1)}{text}{new string(' ', padding)}{new string(borderChar, borderWidth)}";
        Console.WriteLine(paddedText);
    }
}
