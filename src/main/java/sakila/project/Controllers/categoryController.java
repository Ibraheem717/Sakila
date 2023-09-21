package sakila.project.Controllers;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sakila.project.Repository.catagoryRepository;
import sakila.project.Repository.filmCatagoryRepository;
import sakila.project.entities.Category;

@RestController 
@RequestMapping(path="/catagory") 
@CrossOrigin(origins = "http://localhost:3000")
public class categoryController {
    @Autowired 
    private catagoryRepository catagoryRepo;
    @Autowired 
    private filmCatagoryRepository filmCatagoryRepo;
    @PostMapping(path="/add") 
    public @ResponseBody HashMap<String, String> addNewAddress (@RequestBody Category infomation) {
        Category n = infomation;
        n.setLast_update();
        if (catagoryRepo.SearchCategory(infomation.getName().toUpperCase())!=null)
            return new HashMap<>(){{put("output","Category already exist");}};
        catagoryRepo.save(n);
        return new HashMap<>(){{put("output","Saved");}};
    }
    @PutMapping(path="/update") 
    public @ResponseBody HashMap<String, String> updateCategory (@RequestBody HashMap<String, String> information) {
        Category SerchedCategory = catagoryRepo.SearchCategory(information.get("country"));
        Category newCategory = catagoryRepo.SearchCategory(information.get("newCategory"));
        if (SerchedCategory!=null && newCategory==null) {
            SerchedCategory.setLast_update();
            SerchedCategory.setName(information.get("newCategory"));
            catagoryRepo.save(SerchedCategory);
            return new HashMap<>(){{put("output","Saved");}};
        }
        return new HashMap<>(){{put("output","Category doesn't exist");}};
    }
    @DeleteMapping(path="/delete") 
    public @ResponseBody HashMap<String, String> deleteCatagory (@RequestBody HashMap<String, String> givenCategory) {
        Category delCategory = catagoryRepo.SearchCategory(givenCategory.get("name").toUpperCase());
        if (delCategory!=null) {
            filmCatagoryRepo.DeleteByActorID(delCategory.getCategory_id());
            catagoryRepo.delete(delCategory);
            return new HashMap<>(){{put("output","Deleted");}};
        }
        return new HashMap<>(){{put("output","Category doesn't exist");}};
    }
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Category> getAllCategorys() {
        return catagoryRepo.findAll();
    }
    @GetMapping(path="/get")
    public @ResponseBody HashMap<String, String> getCategory(@RequestParam String givenCategory ) {
        Category category = catagoryRepo.SearchCategory(givenCategory.toUpperCase()); 
        if (category!=null)
            return new HashMap<String, String>() {{
                put("name", category.getName());
            }};
        return new HashMap<String, String>() {{
            put("outcome" , "Category doesn't exist");
        }};
    }
}