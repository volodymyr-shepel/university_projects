using Lab10.Services;
using Lab10.ViewModels;
using Microsoft.AspNetCore.Mvc;

namespace Lab10.Controllers
{
    public class CategoryController : Controller
    {
        private IShopService _shopService;



        public CategoryController(IShopService shopService)
        {
            _shopService = shopService;
        }


        [HttpGet]
        public ActionResult Index([FromQuery] int? categoryId)
        {
            
            var categories = _shopService.GetCategories();
            return View(categories);
        }

        [HttpGet]
        public IActionResult Create()
        {
            
            return View();
        }
        [HttpGet]
        public ActionResult Delete(int id)
        {
            return View(_shopService.GetCategory(id));
        }

        [HttpGet]
        public ActionResult Edit(int id)
        {
            
            return View(_shopService.GetCategory(id));
        }
        [HttpGet]
        public ActionResult Details(int id)
        {
            return View(_shopService.GetCategory(id));
        }

		[HttpPost]
		[ValidateAntiForgeryToken]
		public IActionResult Create(Category category)
		{

			
			try
			{
				_shopService.AddCategory(category);
				return RedirectToAction(nameof(Index));
			}
			catch
			{
				return View();
			}
		}

		[HttpPost]
		[ValidateAntiForgeryToken]
		public ActionResult RemoveCategory(int id)
		{
			try
			{
				_shopService.RemoveCategory(id);
				return RedirectToAction(nameof(Index));
			}
			catch
			{
				return View();
			}
		}





	}
}
