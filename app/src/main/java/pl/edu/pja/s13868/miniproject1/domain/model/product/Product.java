package pl.edu.pja.s13868.miniproject1.domain.model.product;

import java.io.Serializable;

/**
 * Represents a product on a shopping list.
 *
 * @author Krzysztof Dzido <s13868@pjwstka.edu.pl>
 */
public class Product implements Serializable{

    private String id;
    private String name;
    private boolean bought;

    /**
     *
     * @param id the unique product ID, once assigned it cannot be changed
     * @param name the product name
     * @param bought the flag for marking the product on the shopping list as bought
     */
    public Product(final String id, final String name, boolean bought) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Valid product ID is required");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Valid product name is required");
        }
        // TODO validate

        this.id = id;
        this.name = name;
        this.bought = bought;
    }

// Mutators

    /**
     * Changes the name of product.
     * @param name the new product name, cannot be null or emptys
     */
    public void changeName(final String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Valid product name is required");
        }
        this.name = name;
    }

    /**
     * Marks the product on the list as bought.
     */
    public void markAsBought() {
        this.bought = true;
    }

    /**
     * Marks the product on the list as NOT bought.
     */
    public void markAsNotBought() {
        this.bought = false;
    }


    // Accessors


    /**
     * @return the product ID as string
     */
    public String getId() {
        return id;
    }

    /**
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * @return true when product is marked as bought on the shopping list
     */
    public boolean isBought() {
        return bought;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", bought=" + bought +
                '}';
    }
}
