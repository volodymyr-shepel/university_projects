using Lab10.Services;
using Lab10.ViewModels.Lab10.ViewModels;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;

namespace Lab10.Controllers
{
    public class ArticleController : Controller
    {

        private IShopService _shopService;



        public ArticleController(IShopService shopService)
        {
            _shopService = shopService;
        }

        [HttpGet]
        public ActionResult Index([FromQuery] int? categoryId)
        {
            ViewBag.Categories = _shopService.GetCategories();

            var articles = _shopService.GetArticles(categoryId);
            return View(articles);
        }



        [HttpGet]
        public IActionResult Create()
        {
            ViewBag.Categories = _shopService.GetCategories();
            return View();
        }

        [HttpGet]
        public ActionResult Delete(int id)
        {
            return View(_shopService.GetArticle(id));
        }

        [HttpGet]
        public ActionResult Edit(int id)
        {
            ViewBag.Categories = _shopService.GetCategories();
            return View(_shopService.GetArticle(id));
        }

        [HttpGet]
        public ActionResult Details(int id)
        {
            return View(_shopService.GetArticle(id));
        }





        [HttpPost]
        [ValidateAntiForgeryToken]
        public IActionResult Create(Article article)
        {

            article.Category = _shopService.GetCategory(article.CategoryId);

            try
            {
                _shopService.AddArticle(article);
                return RedirectToAction(nameof(Index));
            }
            catch
            {
                return View();
            }
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(int id, Article article)
        {
            try
            {


                article.Id = id;
                _shopService.UpdateArticle(article);

                return RedirectToAction(nameof(Index));
            }
            catch
            {
                return View();
            }
        }

       

        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Delete(int id, IFormCollection collection)
        {
            try
            {
                _shopService.RemoveArticle(id);
                return RedirectToAction(nameof(Index));
            }
            catch
            {
                return View();
            }
        }



    }
}
