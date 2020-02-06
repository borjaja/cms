package es.udc.paproject.backend.test.model.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import es.udc.paproject.backend.model.entities.CategoryDao;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.services.CatalogService;
import es.udc.paproject.backend.model.services.UserService;
import es.udc.paproject.backend.model.common.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.Category;
import es.udc.paproject.backend.model.entities.Product;
import static org.junit.Assert.assertEquals;
import java.math.BigDecimal;
import java.util.*;
import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CatalogServiceTest {
    private final static Long NON_EXISTENT_ID = -1L;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserService userService;

    @Autowired
    private CatalogService catalogService;

    private Product createProduct(Long userId, String name, Long category) throws InstanceNotFoundException {
    	Long productId = catalogService.advertise(userId, name, "description", 100, new BigDecimal(1),
    			category, "deliverInfo");
    	return catalogService.findProductById(productId);
    	
    }

    private Category createCategoryAndSave(String name){
        Category c = new Category(name);
        categoryDao.save(c);
        return c;
    }

    private User createUserAndSignUp(String login, String password) throws DuplicateInstanceException{
        User user = new User(login, password, "name", "lastName", "email");
        userService.signUp(user);
        return user;
    }

    @Test(expected = InstanceNotFoundException.class)
    public void testCreateProductByNonExistenUser() throws InstanceNotFoundException {
        Category category1 = createCategoryAndSave("category");
        createProduct(NON_EXISTENT_ID,"asd",category1.getId());
    }

    @Test(expected = InstanceNotFoundException.class)
    public void testCreateProductByNonExistenCategory() throws InstanceNotFoundException,DuplicateInstanceException{
        User user = createUserAndSignUp("name", "passwd");
        createProduct(user.getId(),"asd",NON_EXISTENT_ID);
    }

    @Test(expected = InstanceNotFoundException.class)
    public void testFindProductByNonExistentId() throws InstanceNotFoundException {
        catalogService.findProductById(NON_EXISTENT_ID);
    }

    @Test(expected = InstanceNotFoundException.class)
    public void testShowUserProductByNonExistentUser() throws InstanceNotFoundException {
        catalogService.showUserProducts(NON_EXISTENT_ID,0,2);
    }

    @Test
    public void testFindAllCategories() {
        Category category1 = createCategoryAndSave("category1");
        Category category2 = createCategoryAndSave("category2");

        List<Category> expected = catalogService.findAllCategories();
        assertEquals(expected, Arrays.asList(category1, category2));
    }

    @Test
    public void testFindProductById() throws InstanceNotFoundException, DuplicateInstanceException {
        User user = createUserAndSignUp("name", "passwd");
        Category category = createCategoryAndSave("category");
        Product product = createProduct(user.getId(), "product", category.getId());

        Product expected = catalogService.findProductById(product.getId());
        assertEquals(expected, product);
    }

    @Test
    public void testFindProductsByKeywords() throws DuplicateInstanceException, InstanceNotFoundException {
        User user = createUserAndSignUp("name", "passwd");
        Category category = createCategoryAndSave("category");

        Product product1 = createProduct(user.getId(), "one product", category.getId());
        Product product2 = createProduct(user.getId(), "Two Products", category.getId());
        Product product3 = createProduct(user.getId(), "Three", category.getId());

        List<Product> expected = catalogService.findProducts(null, "PrOd", 0, 2).getItems();
        assertEquals(expected, Arrays.asList(product1, product2));
    }

    @Test
    public void testFindProductsByCategory() throws DuplicateInstanceException, InstanceNotFoundException {
        User user = createUserAndSignUp("name", "passwd");
        Category category1 = createCategoryAndSave("category1");
        Category category2 = createCategoryAndSave("category2");

        Product product1 = createProduct(user.getId(), "product1", category1.getId());
        Product product2 = createProduct(user.getId(), "product2", category2.getId());

        List<Product> expected = catalogService.findProducts(category1.getId(), null, 0, 2).getItems();
        assertEquals(expected, Collections.singletonList(product1));
    }


    @Test
    public void testFindProductsByAll() throws DuplicateInstanceException, InstanceNotFoundException {
        User user = createUserAndSignUp("name", "passwd");
        Category category1 = createCategoryAndSave("category1");
        Category category2 = createCategoryAndSave("category2");

        Product product1 = createProduct(user.getId(), "product 1", category1.getId());
        Product product2 = createProduct(user.getId(), "another", category1.getId());
        Product product3 = createProduct(user.getId(), "product 3", category2.getId());

        List<Product> expected = catalogService.findProducts(category1.getId(), "product", 0, 2).getItems();
        assertEquals(expected, Collections.singletonList(product1));
    }


    @Test
    public void testFindAllProducts() throws DuplicateInstanceException, InstanceNotFoundException {
        User user = createUserAndSignUp("name", "passwd");
        Category category1 = createCategoryAndSave("category1");
        Category category2 = createCategoryAndSave("category2");

        Product product1 = createProduct(user.getId(), "product 1", category1.getId());
        Product product2 = createProduct(user.getId(), "product 2", category2.getId());

        List<Product> expected1 = catalogService.findProducts(null, "", 0, 2).getItems();
        List<Product> expected2 = catalogService.findProducts(null, null, 0, 2).getItems();
        assertEquals(expected1, Arrays.asList(product1, product2));
        assertEquals(expected2, Arrays.asList(product1, product2));
    }

    /*
    * Ordenar primero los que les falta más tiempo para que concluya su periodo de puja
    */
    @Test
    public void testFindProductsOrder() throws DuplicateInstanceException, InstanceNotFoundException {
        User user = createUserAndSignUp("name", "passwd");
        Category category = createCategoryAndSave("category");

        Product product1 = createProduct(user.getId(), "product 1", category.getId());
        Product product2 = createProduct(user.getId(), "product 2", category.getId());

        List<Product> expected = catalogService.findProducts(null, null, 0, 2).getItems();
        assertEquals(expected, Arrays.asList(product1, product2));
    }


    @Test
    public void testFindNoProducts() {
        List<Product> expected = catalogService.findProducts(null, "non-existent", 0, 1).getItems();
        assertEquals(expected, new ArrayList<Product>());
    }

    @Test
    public void testFindProductsByPages() throws DuplicateInstanceException, InstanceNotFoundException {
        User user = createUserAndSignUp("name", "passwd");
        Category category = createCategoryAndSave("category");

        Product product1 = createProduct(user.getId(), "product 1", category.getId());
        Product product2 = createProduct(user.getId(), "product 2", category.getId());
        Product product3 = createProduct(user.getId(), "product 3", category.getId());

        List<Product> expected1 = catalogService.findProducts(null, null, 0, 2).getItems();
        List<Product> expected2 = catalogService.findProducts(null, null, 1, 2).getItems();
        List<Product> expected3 = catalogService.findProducts(null, null, 2, 2).getItems();
        assertEquals(expected1, Arrays.asList(product1, product2));
        assertEquals(expected2, Collections.singletonList(product3));
        assertEquals(expected3, new ArrayList<Product>());
    }


    @Test
    public void testShowUserProducts() throws DuplicateInstanceException, InstanceNotFoundException {
        User user1 = createUserAndSignUp("name1", "passwd1");
        User user2 = createUserAndSignUp("name2", "passwd2");

        Category category = createCategoryAndSave("category");

        Product product1 = createProduct(user1.getId(), "product 1", category.getId());
        Product product2 = createProduct(user2.getId(), "product 2", category.getId());
        Product product3 = catalogService.findProductById(catalogService.advertise(user1.getId(), "product 3", "description",
                150, new BigDecimal(1), category.getId(), "deliverInfo"));

        List<Product> expected1 = catalogService.showUserProducts(user1.getId(), 0, 2).getItems();
        List<Product> expected2 = catalogService.showUserProducts(user2.getId(), 0, 1).getItems();
        assertEquals(expected1, Arrays.asList(product3, product1));
        assertEquals(expected2, Collections.singletonList(product2));
    }
}
