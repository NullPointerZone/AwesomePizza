package awesomepizzasrl.eventmodel.model;

import java.util.List;
import java.util.UUID;

public class CreateOrder {

    private UUID idOrder;
    private String username;
    private List<Pizza> pizzas;

    public CreateOrder(UUID idOrder, String username, List<Pizza> pizzas) {
        this.idOrder = idOrder;
        this.username = username;
        this.pizzas = pizzas;
    }

    public UUID getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(UUID idOrder) {
        this.idOrder = idOrder;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }
}
