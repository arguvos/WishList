package org.example.wishlist.api;

import org.example.wishlist.service.exception.WishItemNotFoundException;
import org.example.wishlist.model.WishItem;
import org.example.wishlist.service.WishService;
import org.example.wishlist.service.model.WishItemFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static org.example.wishlist.config.AppConstant.LOGIN_PAGE;
import static org.example.wishlist.config.AppConstant.REDIRECT;

@Controller
@RequestMapping("/")
public class WishEndpoint {

    private final WishService wishService;

    @Autowired
    public WishEndpoint(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping("/user/{user}")
    public String index(@PathVariable("user") String user, Model model) {
        wishService.addAttributesForIndex(model, ListFilter.ALL, user);
        return "index";
    }

    @GetMapping("/")
    public String index(Principal principal, Model model) {
        if (principal == null) {
            return REDIRECT + LOGIN_PAGE;
        }
        wishService.addAttributesForIndex(model, ListFilter.ALL, principal.getName());
        return "index";
    }

    @GetMapping("/active")
    public String indexActive(Principal principal, Model model) {
        wishService.addAttributesForIndex(model, ListFilter.AVAILABLE, principal.getName());
        return "index";
    }

    @GetMapping("/reserved")
    public String indexCompleted(Principal principal, Model model) {
        wishService.addAttributesForIndex(model, ListFilter.RESERVED, principal.getName());
        return "index";
    }


    @PostMapping
    public String addNewTodoItem(Principal principal, @Valid @ModelAttribute("item") WishItemFormData formData) {
        wishService.save(new WishItem(formData.getTitle(), false, principal.getName()));
        return "redirect:/";
    }

    @PutMapping("/{id}/toggle")
    public String toggleSelection(@PathVariable("id") Long id) {
        wishService.toggle(id);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteTodoItem(@PathVariable("id") Long id) {
        wishService.deleteById(id);
        return "redirect:/";
    }

    public enum ListFilter {
        ALL,
        AVAILABLE,
        RESERVED
    }
}
