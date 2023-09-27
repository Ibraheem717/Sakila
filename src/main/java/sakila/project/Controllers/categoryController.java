package sakila.project.Controllers;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sakila.project.Repository.catagoryRepository;
import sakila.project.Repository.filmCatagoryRepository;
import sakila.project.entities.Category;

import static sakila.project.ProjectApplication.*;

@RestController 
@RequestMapping(path="/catagory") 
@CrossOrigin(origins = "http://localhost:3000")
public class categoryController {
    @Autowired 
    private catagoryRepository catagoryRepo;
    @Autowired 
    private filmCatagoryRepository filmCatagoryRepo;
    private String returnString(String extra) {
        return "Category " + extra;
    }
    @PostMapping(path="/add") 
    public @ResponseBody Map<String, String> addNewCategory (@RequestBody HashMap<String, String> information) {
        Category n = new Category();
        n.setName(information.get("name"));
        n.setLast_update();
        if (catagoryRepo.SearchCategory(n.getName().toUpperCase())!=null)
            return returnValue(returnString(EXIST));
        catagoryRepo.save(n);
        return returnValue(SAVED);
    }
    @PutMapping(path="/update") 
    public @ResponseBody Map<String, String> updateCategory (@RequestBody HashMap<String, String> information) {
        Category SearchedCategory = catagoryRepo.SearchCategory(information.get("name"));
        Category newCategory = catagoryRepo.SearchCategory(information.get("newname"));
        if (SearchedCategory!=null && newCategory==null) {
            SearchedCategory.setLast_update();
            SearchedCategory.setName(information.get("newname"));
            catagoryRepo.save(SearchedCategory);
            return returnValue(SAVED);
        }
        if (newCategory!=null)
            return returnValue(returnString(EXIST));
        return returnValue(returnString(NONEXIST));
    }
    @DeleteMapping(path="/delete") 
    public @ResponseBody Map<String, String> deleteCatagory (@RequestBody HashMap<String, String> givenCategory) {
        Category delCategory = catagoryRepo.SearchCategory(givenCategory.get("name").toUpperCase());
        if (delCategory!=null) {
            filmCatagoryRepo.DeleteByActorID(delCategory.getCategory_id());
            catagoryRepo.delete(delCategory);
            return returnValue(DELETED);
        }
        return returnValue(returnString(NONEXIST));
    }
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Category> getAllCategorys() {
        return catagoryRepo.findAll();
    }
    @GetMapping(path="/get")
    public @ResponseBody Map<String, String> getCategory(@RequestParam String givenCategory ) {
        Category category = catagoryRepo.SearchCategory(givenCategory.toUpperCase());
        Map<String, String> cate = new HashMap<>();
        if (category!=null)
            cate.put("name", category.getName());
        else
            cate = returnValue(returnString(NONEXIST));
        return cate;
    }
}