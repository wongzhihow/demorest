package com.example.demorest;

import com.example.demorest.dao.Product;
import com.example.demorest.dao.ProductInventory;
import com.example.demorest.pagination.Page;
import com.example.demorest.repository.ProductRepository;
import com.example.demorest.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import com.example.demorest.pagination.PaginationResponse;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
class DemoRestApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ProductRepository productRepository;

	@MockitoBean
	private ProductService productService;

	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");


	@Test
	void testGetProductById() throws Exception {

		LocalDateTime now = LocalDateTime.now();
		String formattedDateTime = now.format(formatter);

		Product productA = new Product("productA", "productADesc",
				new BigDecimal("2.00"), new ProductInventory(10), now, now);

		Mockito.when(this.productService.getProduct(1L)).thenReturn(Optional.of(productA));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.content().json(String.format("""
                         {
                                    "id": 0,
                                    "name": "productA",
                                    "description": "productADesc",
                                    "price": 2.00,
                                    "productInventory": {
                                        "quantity": 10
                                    },
                                    createdDateTime: "%s",
                                    modifiedDateTime: "%s"
                         }
                        """, formattedDateTime, formattedDateTime)));

	}

	@Test
	void testGetProductList() throws Exception {
		LocalDateTime now = LocalDateTime.now();

		String formattedDateTime = now.format(formatter);

		Product productA = new Product("productA", "productADesc",
				new BigDecimal("2.00"), new ProductInventory(10), now, now);
		Product productB = new Product("productB", "productBDesc",
				new BigDecimal("3.00"), new ProductInventory(20), now, now);
		Product productC = new Product("productC", "productCDesc",
				new BigDecimal("4.00"), new ProductInventory(30), now, now);

		List<Product> products = new ArrayList<>(List.of(productA, productB, productC));

		PaginationResponse<Product> mockResponse = new PaginationResponse<>(products,
				new Page(1, 10, 3, 1));

		Mockito.when(this.productService.getProducts(any(Pageable.class))).thenReturn(
				mockResponse);


		var response = this.mockMvc.perform(MockMvcRequestBuilders.get("/products"))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.content().json(String.format("""
                        {
                            "item": [
                                      {
                                          "id": 0,
                                          "name": "productA",
                                          "description": "productADesc",
                                          "price": 2.00,
                                          "productInventory": {
                                              "quantity": 10
                                          },
                                          createdDateTime: "%s",
                                          modifiedDateTime: "%s"
                                      },
                                      {
                                          "id": 0,
                                          "name": "productB",
                                          "description": "productBDesc",
                                          "price": 3.00,
                                          "productInventory": {
                                              "quantity": 20
                                          },
                                          createdDateTime: "%s",
                                          modifiedDateTime: "%s"
                                      },
                                      {
                                          "id": 0,
                                          "name": "productC",
                                          "description": "productCDesc",
                                          "price": 4.00,
                                          "productInventory": {
                                              "quantity": 30
                                          },
                                          createdDateTime: "%s",
                                          modifiedDateTime: "%s"
                                      }
                            ],
                            "page": {
                                "page": 1,
                                "pageSize": 10,
                                "totalItem": 3,
                                "totalPage": 1
                            }
                        }
                        """, formattedDateTime , formattedDateTime,
						formattedDateTime,formattedDateTime,formattedDateTime,formattedDateTime)));
	}

	@Test
	void testNestedThirdPartyApi() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/currency-exchange-rate"))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void testCreateProduct() throws Exception {

		Product mockProduct = new Product("productA", "productADesc",
				new BigDecimal("1.00"), new ProductInventory(10), LocalDateTime.now(), LocalDateTime.now());

		mockProduct.setId(1);

		Mockito.when(this.productService.createProduct(any(Product.class))).thenReturn(
				mockProduct);

		this.mockMvc.perform(MockMvcRequestBuilders.post("/products")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("""
                                {
                                    "name": "productA",
                                    "description": "productADesc",
                                    "price": 1.00,
                                    "productInventory": {
                                        "quantity": 10
                                    }
                                }
                                
                                """))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(mockProduct.getName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value(mockProduct.getDescription()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(mockProduct.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.productInventory.quantity").
						value(mockProduct.getProductInventory().getQuantity()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price").
						value(mockProduct.getPrice().doubleValue()));
	}

	@Test
	void testUpdateProduct() throws Exception {

		Product mockUpdateProduct = new Product("productAUpdate", "productADescUpdate",
				new BigDecimal("1.00"), new ProductInventory(10), LocalDateTime.now(), LocalDateTime.now());

		mockUpdateProduct.setId(1);

		Mockito.when(this.productService.updateProduct(any(Long.class), any(Product.class))).thenReturn(
				mockUpdateProduct);

		this.mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content("""
                                {
                                    "name": "productA",
                                    "description": "productADesc",
                                    "price": 1.00,
                                    "productInventory": {
                                        "quantity": 10
                                    }
                                }
                                
                                """))
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value(mockUpdateProduct.getName()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value(mockUpdateProduct.getDescription()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(mockUpdateProduct.getId()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.productInventory.quantity").
						value(mockUpdateProduct.getProductInventory().getQuantity()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.price").
						value(mockUpdateProduct.getPrice().doubleValue()));

	}

	@Test
	void testDeleteProduct() throws Exception {
		Mockito.doNothing().when(productRepository).deleteById(anyLong());

		this.mockMvc.perform(MockMvcRequestBuilders.delete("/products/1")
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(MockMvcResultMatchers.status().isOk());

		Mockito.verify(productService,Mockito.times(1)).deleteProduct(1L);
	}


}

