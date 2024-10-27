package org.example.wishlist.service.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WishItemFormData {
    @NotBlank
    private String title;
}
