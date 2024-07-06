using Lab10.ViewModels;
using Lab10.ViewModels.Lab10.ViewModels;

namespace Lab10.Services
{
    public interface IShopService
    {
        List<Article> GetArticles(int? categoryId = null);
        Article GetArticle(int id);
        List<Category> GetCategories();
        void AddArticle(Article article);
        
        void RemoveArticle(int id);
        void UpdateArticle(Article article);
        Category GetCategory(int categoryId);

        void AddCategory(Category category);

        void RemoveCategory(int id);
    }
}
