package sakila.project;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class ProjectApplication {

	public static final String SAVED = "Saved";
	public static final String DELETED = "Deleted";
	public static final String EXIST = "already exist";
	public static final String NONEXIST = "doesn't exist";

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	public static Map<String, String> returnValue(String value) {
		Map<String, String> dict = new HashMap<>();
		dict.put("output", value);
		return dict;
	}
	@RequestMapping(value = "/")
	public @ResponseBody String healthCheck() {
		return "bob";
	}
}
