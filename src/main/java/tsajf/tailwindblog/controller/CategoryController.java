package tsajf.tailwindblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import tsajf.tailwindblog.entity.Category;
import tsajf.tailwindblog.repository.CategoryRepository;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/admin/category")
    public String index(Model model) {
        model.addAttribute("title", "Category List");
        model.addAttribute("page", "admin/category/index");
        model.addAttribute("categories", categoryRepository.findAll());
        return "admin/fragments/layout";
    }

    @GetMapping("/admin/category/create")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("title", "Add Category");
        model.addAttribute("page", "admin/category/create");
        return "admin/fragments/layout";
    }

    @PostMapping("/admin/category/store")
    public String create(@ModelAttribute Category store) {
        categoryRepository.save(store);
        return "redirect:/admin/category";
    }

    @GetMapping("/admin/category/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        model.addAttribute("title", "Edit Category");
        model.addAttribute("page", "admin/category/edit");
        model.addAttribute("category", category);
        return "admin/fragments/layout";
    }

    @PostMapping("/admin/category/update/{id}")
    public String update(@PathVariable("id") Integer id, @ModelAttribute Category update) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        categoryRepository.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/admin/category/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        categoryRepository.deleteById(id);
        return "redirect:/admin/category";
    }

}
