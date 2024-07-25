package com.chervonnaya.objectmappermvcproject;

import com.chervonnaya.objectmappermvcproject.model.Address;
import com.chervonnaya.objectmappermvcproject.model.Customer;
import com.chervonnaya.objectmappermvcproject.model.Order;
import com.chervonnaya.objectmappermvcproject.model.Product;

import java.math.BigDecimal;
import java.util.Arrays;

public class TestData {

    public static final String customerURL = "/api/customer";
    public static final String orderURL = "/api/order";
    public static final String productURL = "/api/product";
    public static final String customerJson1 = "{\"firstName\":\"John\",\"lastName\":\"Doe\","
        + "\"email\":\"john.doe@example.com\",\"password\":\"securePassword123\"}";
    public static final String customerJson2 = "{\"firstName\":\"Jane\",\"lastName\":\"Doe\","
        + "\"email\":\"jane.doe@example.com\",\"password\":\"securePassword123\"}";
    public static final String productJson1 = "{\"name\":\"Fan\",\"description\":\"desc\",\"price\":300,\"stock\":18}";
    public static final String productJson2 = "{\"name\":\"Iphone\",\"price\":3000,\"stock\":1}";
    public static final String orderJson1 = "{\"customer\":{\"id\":1},\"address\":{\"apartment\":18,\"home\":99,"
        + "\"street\":\"MainSt\",\"city\":\"Anytown\"},\"products\":[{\"id\":3}],"
        + "\"sum\":300,\"status\":\"IN_PROGRESS\"}";
    public static final String orderJson2 = "{\"customer\":{\"id\":2},\"address\":{\"apartment\":18,\"home\":99,"
        + "\"street\":\"MainSt\",\"city\":\"Anytown\"},\"products\":[{\"id\":3}],"
        + "\"sum\":300,\"status\":\"PAID\"}";
    public static final Customer customer1 = new Customer();
    public static final Customer customer2 = new Customer();
    public static final Product product1 = new Product();
    public static final Product product2 = new Product();
    public static final Order order1 = new Order();
    public static final Order order2 = new Order();
    public static final Address address1 = new Address();

    static {
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setEmail("john.doe@example.com");
        customer1.setPassword("securePassword123");

        customer2.setFirstName("Jane");
        customer2.setLastName("Doe");
        customer2.setEmail("jane.doe@example.com");
        customer2.setPassword("securePassword123");

        product1.setName("Fan");
        product1.setDescription("desc");
        product1.setPrice(new BigDecimal("300"));
        product1.setStock(18);

        product2.setName("Iphone");
        product2.setPrice(new BigDecimal("3000"));
        product2.setStock(1);

        address1.setCity("Anytown");
        address1.setStreet("Main St");
        address1.setHome(99);
        address1.setApartment(18);

        order1.setCustomer(customer1);
        order1.setAddress(address1);
        order1.setProducts(Arrays.asList(product1));
        order1.setSum(new BigDecimal("300"));
        order1.setStatus(Order.Status.IN_PROGRESS);

        order2.setCustomer(customer2);
        order2.setAddress(address1);
        order2.setProducts(Arrays.asList(product1));
        order2.setSum(new BigDecimal("300"));
        order2.setStatus(Order.Status.PAID);

    }
}

