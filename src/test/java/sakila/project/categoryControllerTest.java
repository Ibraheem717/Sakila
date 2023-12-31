package sakila.project;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import sakila.project.controllers.CategoryController;
import sakila.project.repository.CatagoryRepository;
import sakila.project.repository.FilmCatagoryRepository;
import sakila.project.entities.Category;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@WebMvcTest(CategoryController.class)
class categoryControllerTest {

    @MockBean
    private CatagoryRepository categoryRepo;
    @MockBean
    private FilmCatagoryRepository filmCategoryRepo;
    @Autowired
    private MockMvc mvc;
    private final String JSON = "{" +
            "\"name\":\"Bob\"" +
            "}";
    private final String NEWJSON = "{" +
            "\"name\":\"Bob\"," +
            "\"newname\":\"Door\"" +
            "}";

    @Test
    @DisplayName("Add New Category -- Success")
    void testAddNewCategorySuccess() throws Exception {
        when(categoryRepo.save(any(Category.class))).thenReturn(new Category());
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/catagory/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        verify(categoryRepo, times(1)).save(any(Category.class));
        assertTrue(responseJSON.contains("\"output\":\"Saved\""));
    }
    @Test
    @DisplayName("Add New Category -- Fail")
    void testAddNewCategoryFailure() throws Exception {
        when(categoryRepo.SearchCategory(anyString())).thenReturn(new Category()); // Category already exists
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/catagory/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        System.out.println("Response JSON: " + responseJSON);
        verify(categoryRepo, times(1)).SearchCategory(anyString());
        assertTrue(responseJSON.contains("\"output\":\"Category already exist\""));
    }
    @Test
    @DisplayName("Update Category -- Success")
    void testUpdateCategorySuccess() throws Exception {
        when(categoryRepo.SearchCategory(anyString())).thenReturn(new Category()); // Category exists but with different name
        when(categoryRepo.SearchCategory("Door")).thenReturn(null); // New name is available
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/catagory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(NEWJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        verify(categoryRepo, times(2)).SearchCategory(anyString());
        verify(categoryRepo, times(1)).save(any(Category.class));
        assertTrue(responseJSON.contains("\"output\":\"Saved\""));
    }

    @Test
    @DisplayName("Update Category -- Fail (Name Taken)")
    void testUpdateCategoryNameTaken() throws Exception {
        when(categoryRepo.SearchCategory(anyString())).thenReturn(new Category());
        when(categoryRepo.SearchCategory("Door")).thenReturn(new Category());
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/catagory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(NEWJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Get the response JSON and assert it
        String responseJSON = result.getResponse().getContentAsString();
        verify(categoryRepo, times(2)).SearchCategory(anyString());
        assertTrue(responseJSON.contains("\"output\":\"Category already exist\""));
    }
    @Test
    @DisplayName("Update Category -- Fail (Category doesn't exist)")
    void testUpdateCategoryNotFound() throws Exception {
        when(categoryRepo.SearchCategory("Rick")).thenReturn(null);
        String failedNEWJSON = "{\"name\":\"Rick\",\"newname\":\"Ben\"}";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/catagory/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(failedNEWJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        verify(categoryRepo, times(2)).SearchCategory(anyString());
        assertTrue(responseJSON.contains("\"output\":\"Category doesn't exist\""));
    }
    @Test
    @DisplayName("Get Category -- Success")
    void testGetCategorysSuccess() throws Exception {
        Category cate = new Category();
        cate.setName("Lizzy");
        when(categoryRepo.SearchCategory("LIZZY")).thenReturn(cate);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/catagory/get")
                        .param("givenCategory", "Lizzy"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        verify(categoryRepo, times(1)).SearchCategory("LIZZY");
        assertTrue(responseJSON.contains("\"name\":\"Lizzy\""));
    }
    @Test
    @DisplayName("Get Category -- Failed")
    void testGetCategoryCategoryNotFound() throws Exception {
        when(categoryRepo.SearchCategory("Muguire")).thenReturn(null);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/catagory/get")
                .param("givenCategory", "Muguire"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        System.out.println("Response JSON: " + responseJSON);
        verify(categoryRepo, times(1)).SearchCategory("MUGUIRE");
        assertTrue(responseJSON.contains("\"output\":\"Category doesn't exist\""));
    }
    private Category createCategoryWithID() {
        Category act = createCategory("Joker");
        act.setCategory_id((byte) 1);
        act.setLast_update();
        return act;
    }
    @Test
    @DisplayName("Delete Category -- Success")
    void testDeleteCategorySuccess() throws Exception {
        Category act = createCategoryWithID();
        when(categoryRepo.SearchCategory("JOKER")).thenReturn(act);
        doNothing().when(filmCategoryRepo).DeleteByActorID((byte) 1);
        doNothing().when(categoryRepo).delete(any(Category.class));
        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/catagory/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Joker\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        verify(categoryRepo, times(1)).SearchCategory("JOKER");
        verify(filmCategoryRepo, times(1)).DeleteByActorID((byte) 1);
        verify(categoryRepo, times(1)).delete(any(Category.class));
        assertTrue(responseJSON.contains("\"output\":\"Deleted\""));
    }
    @Test
    @DisplayName("Delete Category -- Failed")
    void testDeleteCategoryNotFound() throws Exception {
        when(categoryRepo.SearchCategory("Joker")).thenReturn(null);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/catagory/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Joker\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        verify(categoryRepo, times(1)).SearchCategory("JOKER");
        assertTrue(responseJSON.contains("\"output\":\"Category doesn't exist\""));
    }
    private Category createCategory(String name) {
        Category Category = new Category();
        Category.setName(name);
        return Category;
    }
    @Test
    @DisplayName("Get All Category's")
    void testGetAllCategory() throws Exception {
        List<Category> categories = new ArrayList<>();
        categories.add(createCategory("The"));
        categories.add(createCategory("Can"));
        categories.add(createCategory( "Fix"));
        categories.add(createCategory("No"));
        when(categoryRepo.findAll()).thenReturn(categories);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/catagory/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        verify(categoryRepo, times(1)).findAll();
        assertTrue(responseJSON.contains("\"name\":\"The\""));
        assertTrue(responseJSON.contains("\"name\":\"Can\""));
        assertTrue(responseJSON.contains("\"name\":\"Fix\""));
        assertTrue(responseJSON.contains("\"name\":\"No\""));
    }

}