using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Lab10.ViewModels
{
    
    namespace Lab10.ViewModels
    {
        public class Article
        {
            
            public int Id { get; set; }

            [Required(ErrorMessage = "Name is required.")]
            [MinLength(3, ErrorMessage = "Name must be at least 3 characters.")]
            public string Name { get; set; }

            [Range(0, double.MaxValue, ErrorMessage = "Price must be greater than or equal to 0.")]
            public double Price { get; set; }

            // ? to specify that Image can be null
            public string? ImageFileName { get; set; } // Store the image file name


            // Foreign key property
            [ForeignKey("Category")]
            public int CategoryId { get; set; }

            // Navigation property
            public virtual Category Category { get; set; }

            [NotMapped] // Not mapped to the database
            public IFormFile? ImageFile { get; set; }

            public Article(int id, string name, double price, int categoryId, string imageFileName)
            {
                Id = id;
                Name = name;
                Price = price;
                CategoryId = categoryId;
                ImageFileName = imageFileName;
            }
                public Article() { 
            
                }
            }
    }
}
