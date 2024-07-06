using Lab9.ViewModels;
using Lab9.ViewModels.Lab9.ViewModels;

namespace Lab9.DataContext
{
    public interface IArticleContext
       
    {
        List<Article> GetArticles();
        Article GetArticle(int id);
        void AddArticle(Article article);
        void RemoveArticle(int id);
        void UpdateArticle(Article article);


    }
}
