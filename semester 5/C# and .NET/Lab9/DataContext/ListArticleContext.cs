using Lab9.ViewModels;
using Lab9.ViewModels.Lab9.ViewModels;
using System;
using System.Reflection;

namespace Lab9.DataContext
{
    public class ListArticleContext : IArticleContext
    {
        List<Article> articles = new List<Article>() {
        new Article(0,"Iphone15",4999, new DateTime(2024,1,1),Category.Smartphones),
        new Article(1,"Airpods Max",2499, new DateTime(2024,1,2),Category.Headphones),
        new Article(2,"MacBook Air",6479, new DateTime(2024,1,3),Category.Laptops),
        new Article(3,"Ultra 2",4419, new DateTime(2024,1,4),Category.Smartwatches),
};
        public void AddArticle(Article article)
        {
            int nextNumber = articles.Max(a => a.Id) + 1;
            article.Id = nextNumber;
            articles.Add(article);
        }

        public Article GetArticle(int id)
        {
            return articles.FirstOrDefault(a => a.Id == id);
        }

        public List<Article> GetArticles()
        {
            return articles;
        }

        public void RemoveArticle(int id)
        {
            Article articleToRemove = articles.FirstOrDefault(a => a.Id == id);
            if (articleToRemove != null)
                articles.Remove(articleToRemove);
        }

        public void UpdateArticle(Article updatedArticle)
        {
            Article articleToUpdate = articles.FirstOrDefault(a => a.Id == updatedArticle.Id);

            if (articleToUpdate != null)
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
