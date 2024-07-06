using Lab9.ViewModels.Lab9.ViewModels;
using Lab9.ViewModels;

namespace Lab9.DataContext
{
    public class DictionaryArticleContext : IArticleContext
    {
        private Dictionary<int, Article> articles = new Dictionary<int, Article>
        {
            { 0, new Article(0, "Iphone15", 4999, new DateTime(2024, 1, 1), Category.Smartphones) },
            { 1, new Article(1, "Airpods Max", 2499, new DateTime(2024, 1, 2), Category.Headphones) },
            { 2, new Article(2, "MacBook Air", 6479, new DateTime(2024, 1, 3), Category.Laptops) },
            { 3, new Article(3, "Ultra 2", 4419, new DateTime(2024, 1, 4), Category.Smartwatches) }
        };

        public void AddArticle(Article article)
        {
            int nextNumber = articles.Keys.Max() + 1;
            article.Id = nextNumber;
            articles.Add(article.Id, article);
        }

        public Article GetArticle(int id)
        {
            return articles.TryGetValue(id, out var article) ? article : null;
        }

        public List<Article> GetArticles()
        {
            return articles.Values.ToList();
        }

        public void RemoveArticle(int id)
        {
            articles.Remove(id, out _);
        }

        public void UpdateArticle(Article updatedArticle)
        {
            if (articles.TryGetValue(updatedArticle.Id, out var articleToUpdate))
            {
                // Update the properties of the existing article
                articleToUpdate.Name = updatedArticle.Name;
                articleToUpdate.Price = updatedArticle.Price;
                articleToUpdate.ExpirationDate = updatedArticle.ExpirationDate;
                articleToUpdate.Category = updatedArticle.Category;
            }
        }
    }
}
