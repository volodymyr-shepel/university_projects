using System.ComponentModel.DataAnnotations;

namespace Lab9.ViewModels
{
    
    namespace Lab9.ViewModels
    {
        public class Article
        {
            
            public int Id { get; set; }

            [Required(ErrorMessage = "Name is required.")]
            [MinLength(3, ErrorMessage = "Name must be at least 3 characters.")]
            public string Name { get; set; }

            [Range(0, double.MaxValue, ErrorMessage = "Price must be greater than or equal to 0.")]
            public double Price { get; set; }

            public DateTime ExpirationDate { get; set; }

            public Category Category { get; set; }

            public Article(int id, string name, double price, DateTime expirationDate, Category category)
            {
                Id = id;
                Name = name;
                Price = price;
                ExpirationDate = expirationDate;
                Category = category;
            }
            public Article() { 
            
            }
        }
    }
}
