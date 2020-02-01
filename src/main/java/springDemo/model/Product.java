package springDemo.model;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @NonNull
    private String sku;
    private String brand;
    private String name;
    private int price;

}
