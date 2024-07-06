using System.ComponentModel.DataAnnotations;

namespace Lab10.ViewModels
{
    public class Category
    {
        [Key]
        public int Id { get; set; }
        public string Name { get; set; }

        public Category(int id, string name)
        {
            Id = id;
            Name = name;
        }
        public Category()
        {

        }
    }
}
