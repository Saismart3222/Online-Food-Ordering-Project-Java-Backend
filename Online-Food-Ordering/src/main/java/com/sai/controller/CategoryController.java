package com.sai.controller;

import com.sai.model.Category;
import com.sai.model.User;
import com.sai.service.CategoryServices;
import com.sai.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/category")
public class CategoryController {

    @Autowired
    private CategoryServices categoryService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category,
                                                   @RequestHeader("Authorization ") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Category createdCategory  = categoryService.createCategory(category.getName(),user.getId());

        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    @GetMapping("/category/restaurant")
    public ResponseEntity<List<Category>> getRestaurantCategory(
                                                   @RequestHeader("Authorization ") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Category> categories  = categoryService.findCategoryByRestaurantId(user.getId());

        return new ResponseEntity<>(categories, HttpStatus.CREATED);
    }
}
