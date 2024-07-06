using Lab9.DataContext;
using Lab9.ViewModels.Lab9.ViewModels;
using Microsoft.AspNetCore.Mvc;

namespace Lab9.Controllers
{
    public class ArticleController : Controller
    {
        private IArticleContext _articleContext;

        public ArticleController(IArticleContext articleContext)
        {
            this._articleContext = articleContext;
        }

        public ActionResult Index()
        {
            return View(_articleContext.GetArticles());
        }

        public ActionResult AnotherIndex() 
        {
            return View(_articleContext.GetArticles());
        }
        
        public ActionResult Details(int id)
        {
            return View(_articleContext.GetArticle(id)); 
        }

        
        public ActionResult Create()
        {
            return View();
        }

        
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create(Article article) 
        {
            try
            {
                if (ModelState.IsValid)                
                    _articleContext.AddArticle(article);  
                return RedirectToAction(nameof(Index));
            }
            catch
            {
                return View();
            }
        }

        
        public ActionResult Edit(int id)
        {
            return View(_articleContext.GetArticle(id)); 
        }

        
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(int id, Article article) 
        {

            try
            {
                if (ModelState.IsValid)
                {
                    article.Id = id; // added
                    _articleContext.UpdateArticle(article); //added
                }
                return RedirectToAction(nameof(Index));
            }
            catch
            {
                return View();
            }
        }

        
        public ActionResult Delete(int id)
        {
            return View(_articleContext.GetArticle(id)); 
        }

        
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Delete(int id, IFormCollection collection) 
        {
            try
            {
                _articleContext.RemoveArticle(id); 
                return RedirectToAction(nameof(Index));
            }
            catch
            {
                return View();
            }
        }
    }
}

