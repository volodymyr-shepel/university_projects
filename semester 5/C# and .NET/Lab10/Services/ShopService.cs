using Lab10.Data;
using Lab10.ViewModels;
using Lab10.ViewModels.Lab10.ViewModels;
using Microsoft.EntityFrameworkCore;

namespace Lab10.Services
{
    public class ShopService : IShopService
    {
        private readonly ShopDbContext _dbContext;

        public ShopService(ShopDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public void AddCategory(Category category)
        {
			_dbContext.Categories.Add(category);
			_dbContext.SaveChanges();
		}
        public void AddArticle(Article article)
        {

            if (article.ImageFile != null)
            {
                // Generate a random number as the ImageFileName
                string randomFileName = $"{Guid.NewGuid().ToString()}{Path.GetExtension(article.ImageFile.FileName)}";

                string uploadFolder = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", "upload");
                string filePath = Path.Combine(uploadFolder, randomFileName);

                using (var fileStream = new FileStream(filePath, FileMode.Create))
                {
                    article.ImageFile.CopyTo(fileStream);
                }

                article.ImageFileName = randomFileName;
            }

    
            _dbContext.Articles.Add(article);
            _dbContext.SaveChanges();
        }


        public Article GetArticle(int id)
        {
            return _dbContext.Articles.Find(id);
        }


        public List<Article> GetArticles(int? categoryId = null)
        {
            IQueryable<Article> articles = _dbContext.Articles.Include(a => a.Category);

            // Filter by category if categoryId is specified
            if (categoryId.HasValue)
            {
                articles = articles.Where(a => a.CategoryId == categoryId);
            }

            return articles.ToList();
        }

        public List<Category> GetCategories()
        {
            var categories = _dbContext.Categories.ToList();
            return categories;
        }
        public Category GetCategory(int id)
        {
            return _dbContext.Categories.Find(id);
        }

        public void RemoveArticle(int id)
        {
            var article = _dbContext.Articles.Find(id);
            if (article != null)
            {
                // Remove associated image file
                if (!string.IsNullOrEmpty(article.ImageFileName))
                {
                    string imagePath = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", "upload", article.ImageFileName);

                    if (System.IO.File.Exists(imagePath))
                    {
                        System.IO.File.Delete(imagePath);
                    }
                }

                
                _dbContext.Articles.Remove(article);
                _dbContext.SaveChanges();
            }
        }
        public void RemoveCategory(int id)
        {
			// Remove the category itself
			

			// Get all articles belonging to the specified category ID
			var articlesToRemove = _dbContext.Articles.Where(a => a.CategoryId == id).ToList();
			foreach (var article in articlesToRemove)
			{
				RemoveArticle(article.Id);
			}

			var categoryToRemove = _dbContext.Categories.Find(id);

			if (categoryToRemove != null)
			{
				_dbContext.Categories.Remove(categoryToRemove);
			}

			_dbContext.SaveChanges();




		}

		public void UpdateArticle(Article updatedArticle)
        {
            var existingArticle = _dbContext.Articles.Find(updatedArticle.Id);

            if (existingArticle != null)
            {
                existingArticle.Name = updatedArticle.Name;
                existingArticle.Price = updatedArticle.Price;
                existingArticle.CategoryId = updatedArticle.CategoryId;

                if (updatedArticle.ImageFile != null)
                {
                    // Delete the existing image file
                    if (!string.IsNullOrEmpty(existingArticle.ImageFileName))
                    {
                        string imagePath = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", "upload", existingArticle.ImageFileName);

                        if (System.IO.File.Exists(imagePath))
                        {
                            System.IO.File.Delete(imagePath);
                        }
                    }

                    // Generate a random number as the new ImageFileName
                    string randomFileName = $"{Guid.NewGuid().ToString()}{Path.GetExtension(updatedArticle.ImageFile.FileName)}";

                    string uploadFolder = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", "upload");
                    string filePath = Path.Combine(uploadFolder, randomFileName);

                    using (var fileStream = new FileStream(filePath, FileMode.Create))
                    {
                        updatedArticle.ImageFile.CopyTo(fileStream);
                    }

                    existingArticle.ImageFileName = randomFileName;
                }

                // Mark the entity as modified and save changes
                _dbContext.Entry(existingArticle).State = EntityState.Modified;
                _dbContext.SaveChanges();
            }
        }

    }
}
