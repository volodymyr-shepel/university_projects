using Lab10.Services;
using Lab10.ViewModels.Lab10.ViewModels;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;

namespace Lab10.Controllers
{
    public class ShopController : Controller
    {
        private IShopService _shopService;

        

        public ShopController(IShopService shopService)
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
        public ActionResult Details(int id)
        {
            ViewBag.Categories = _shopService.GetCategories();
            return View(_shopService.GetArticle(id));
        }

















    }

}

