package org.example.wishlist.service;

import lombok.Getter;
import org.example.wishlist.api.WishEndpoint;
import org.example.wishlist.model.WishItemDto;
import org.example.wishlist.model.WishItem;
import org.example.wishlist.repository.WishItemRepository;
import org.example.wishlist.service.exception.WishItemNotFoundException;
import org.example.wishlist.service.model.WishItemFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishService {

    @Getter
    private final WishItemRepository repository;

    @Autowired
    public WishService(WishItemRepository repository) {
        this.repository = repository;
    }

    public void save(WishItem wishItem) {
        repository.save(wishItem);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public void toggle(Long id) {
        WishItem wishItem = repository.findById(id)
                .orElseThrow(() -> new WishItemNotFoundException(id));

        wishItem.setReserved(!wishItem.isReserved());
        repository.save(wishItem);
    }

    public void addAttributesForIndex(Model model,
                                       WishEndpoint.ListFilter listFilter,
                                       String user) {
        model.addAttribute("item", new WishItemFormData());
        model.addAttribute("filter", listFilter);
        model.addAttribute("todos", getWishItems(listFilter, user));
        model.addAttribute("totalNumberOfItems", repository.count());
        model.addAttribute("numberOfActiveItems", getNumberOfActiveItems());
        model.addAttribute("numberOfCompletedItems", getNumberOfCompletedItems());
    }

    private List<WishItemDto> getWishItems(WishEndpoint.ListFilter filter, String user) {
        return switch (filter) {
            case ALL -> convertToDto(repository.findAllByUser(user));
            case AVAILABLE -> convertToDto(repository.findAllByUserAndReserved(user,false));
            case RESERVED -> convertToDto(repository.findAllByUserAndReserved(user, true));
        };
    }

    private List<WishItemDto> convertToDto(List<WishItem> wishItems) {
        return wishItems
                .stream()
                .map(wishItem -> new WishItemDto(wishItem.getId(),
                        wishItem.getTitle(),
                        wishItem.isReserved()))
                .collect(Collectors.toList());
    }

    private int getNumberOfActiveItems() {
        return repository.countAllByReserved(false);
    }

    private int getNumberOfCompletedItems() {
        return repository.countAllByReserved(true);
    }

}
