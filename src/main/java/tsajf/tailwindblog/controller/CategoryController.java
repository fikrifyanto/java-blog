package tsajf.tailwindblog.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import tsajf.tailwindblog.entity.Category;
import tsajf.tailwindblog.repository.CategoryRepository;

@Controller
public class CategoryController {

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/admin/category")
    public String index(Model model) {
        model.addAttribute("page", "category");
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/category/index";
    }

    @GetMapping("/admin/category/create")
    public String create(Model model) {
        model.addAttribute("category", new Category());
        return "admin/category/create";
    }

    @PostMapping("/admin/category/store")
    public String store(@ModelAttribute @Valid Category store, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("category", store);
            return "admin/category/create";
        }

        categoryRepository.save(store);
        return "redirect:/admin/category";
    }

    @GetMapping("/admin/category/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        model.addAttribute("category", category);
        return "admin/category/edit";
    }

    @PostMapping("/admin/category/update/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute @Valid Category update, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("category", update);
            return "admin/category/edit";
        }

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        category.setName(update.getName());
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/admin/category/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/category";
    }

}
