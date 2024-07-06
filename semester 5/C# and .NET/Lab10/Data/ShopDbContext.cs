using Lab10.ViewModels;
using Lab10.ViewModels.Lab10.ViewModels;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace Lab10.Data
{
    public class ShopDbContext : DbContext
    {
        public ShopDbContext(DbContextOptions<ShopDbContext> options) : base(options)
        {
        }
        public DbSet<Article> Articles { get; set; }

        public DbSet<Category> Categories { get; set; }

    }
}
