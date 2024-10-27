package org.example.wishlist.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
public class WishItem {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String title;

    private boolean reserved;

    private String user;

    public WishItem(String title, boolean reserved, String user) {
        this.title = title;
        this.reserved = reserved;
        this.user = user;
    }
}
