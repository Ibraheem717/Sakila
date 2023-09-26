package sakila.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sakila.project.Controllers.categoryController;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@WebMvcTest(categoryController.class)
class categoryControllerTest {

//    @Autowired
//    private categoryController categoryCon;
//
//    @Test
//    public void testGetUser() throws Exception {
//        HashMap<String , String> getOutput = new HashMap<>();
//        getOutput.put("name", "Animation");
//        when(categoryCon.getCategory("Animation"))
//                .thenReturn(getOutput);
//    }
//
//    @Test
//    public void testAddUser() {
//        HashMap<String , String> getOutput = new HashMap<>();
//        getOutput.put("name", "Doctor");
//        HashMap<String , String> output = new HashMap<>();
//        output.put("output", "Saved");
//        HashMap<String , String> changeOutput = new HashMap<>();
//        getOutput.put("name", "Doctor");
//        getOutput.put("newname" , "Doe");
//        HashMap<String , String> delOutput = new HashMap<>();
//        delOutput.put("name", "Doe");
//
//        when(categoryCon.addNewCategory(getOutput))
//                .thenReturn(output);
//
//        output.put("output", "Category already exist");
//        when(categoryCon.addNewCategory(getOutput))
//                .thenReturn(output);
//
//        when(categoryCon.getCategory("Doctor"))
//                .thenReturn(getOutput);
//
//        when(categoryCon.updateCategory(changeOutput))
//                .thenReturn(getOutput);
//
//        when(categoryCon.updateCategory(changeOutput))
//                .thenReturn(output);
//
//        output.put("output", "Deleted");
//        when(categoryCon.deleteCatagory(delOutput))
//                .thenReturn(getOutput);
//    }

}