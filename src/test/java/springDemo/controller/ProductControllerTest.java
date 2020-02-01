package springDemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import springDemo.model.Product;
import springDemo.repository.RepositoryException;
import springDemo.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    private Product product;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService service;

    @Before
    public void setUp() {
         product = Product.builder()
                 .sku("mypr-100-100")
                .brand("myprotein")
                .name("name")
                .price(1000)
                .build();
    }

    @Test
    public void testGetProduct() throws Exception {
        when(service.getProduct(product.getSku())).thenReturn(product);

        mvc.perform(get("/products/" + product.getSku())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sku").value(product.getSku()))
                .andExpect(jsonPath("$.brand").value(product.getBrand()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.price").value(product.getPrice()));
    }

    @Test
    public void testGetInvalidProductReturnsBadRequest() throws Exception {
        when(service.getProduct("invalid-sku")).thenThrow(new RepositoryException("Does not exist"));

        mvc.perform(get("/products/invalid-sku"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mvc.perform(delete("/products/sku"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteInvalidRequest() throws Exception {
        doThrow(new RepositoryException("invalid")).when(service).deleteProduct("nonexistent-sku");

        mvc.perform(delete("/products/nonexistent-sku"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateProduct() throws Exception {
        String productJson = mapper.writeValueAsString(product);

        String returnedJson = mvc.perform(post("/products")
                .contentType(APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(product, mapper.readValue(returnedJson, Product.class));
    }

    @Test
    public void testCreateProductThrowsExceptionIfExists() throws Exception {
        doThrow(new RepositoryException("invalid")).when(service).createProduct(product);

        String productJson = mapper.writeValueAsString(product);

        mvc.perform(post("/products")
                .contentType(APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findProducts() throws Exception {
        List<Product> searchResults = new ArrayList<>();
        searchResults.add(product);

        when(service.searchProducts("whey")).thenReturn(searchResults);

        mvc.perform(get("/products/search?query=whey")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sku").value(product.getSku()))
                .andExpect(jsonPath("$[0].brand").value(product.getBrand()))
                .andExpect(jsonPath("$[0].name").value(product.getName()))
                .andExpect(jsonPath("$[0].price").value(product.getPrice()));
    }

    @Test
    public void updateProductCreatesProductIfNonexistent() throws Exception {
        when(service.getProduct(product.getSku())).thenReturn(null);

        String productJson = mapper.writeValueAsString(product);

        mvc.perform(put("/products")
                .contentType(APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateProductReturnsOkIfExists() throws Exception {
        when(service.getProduct(product.getSku())).thenReturn(product);

        String productJson = mapper.writeValueAsString(product);

        mvc.perform(put("/products")
                .contentType(APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isNoContent());
    }
}
