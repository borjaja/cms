package es.udc.paproject.backend.test.model.services;

import es.udc.paproject.backend.model.common.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.common.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.services.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class BidServiceTest {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    UserService userService;

    @Autowired
    CatalogService catalogService;

    @Autowired
    BidService bidService;

    private Product createProduct(Long userId, String name, Long valor, Long category) throws InstanceNotFoundException {
    	Long productId = catalogService.advertise(userId, name, "description", 100, new BigDecimal(valor),
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

    @Test(expected = BidAfterLimitDateException.class)
    public void testCreateBidBeforeEndDate() throws InstanceNotFoundException, BidAfterLimitDateException,
            PriceUnderActualPriceException, DuplicateInstanceException, BidForOwnProductException
    {
        User owner = createUserAndSignUp("owner", "passwd");
        User user = createUserAndSignUp("nombre", "passwd");
        Category category = createCategoryAndSave("category");
        Product product = createProduct(owner.getId(), "one product",12L ,category.getId());
        product.setEndDate(product.getEndDate().minusDays(5));
        bidService.makeBid(user.getId(),product.getId(),new BigDecimal(13));
    }

    @Test(expected = PriceUnderActualPriceException.class)
    public void testCreateBidUnderActualPrice() throws InstanceNotFoundException, BidAfterLimitDateException,
            PriceUnderActualPriceException, DuplicateInstanceException, BidForOwnProductException
    {
        User vendedor = createUserAndSignUp("vendedor", "passwd1");
        User compradorA = createUserAndSignUp("compradorA", "passwd2");
        User compradorB = createUserAndSignUp("compradorB", "passwd2");

        Category category = createCategoryAndSave("category1");
        Product product = createProduct(vendedor.getId(), "product",10L, category.getId());
        Bid bidA = bidService.makeBid(compradorA.getId(),product.getId(),new BigDecimal(12));
        Bid bidB = bidService.makeBid(compradorB.getId(),product.getId(),new BigDecimal(10));
    }

    @Test
    public void testMakeBidAndFindBid()
            throws InstanceNotFoundException, BidAfterLimitDateException,
            PriceUnderActualPriceException, DuplicateInstanceException, BidForOwnProductException
    {
        User owner = createUserAndSignUp("owner", "passwd");
        User user = createUserAndSignUp("nombre", "passwd");
        Category category = createCategoryAndSave("category");
        Product product = createProduct(owner.getId(), "one product",12L ,category.getId());
        Bid bid = bidService.makeBid(user.getId(),product.getId(),new BigDecimal(13));

        List<Bid> expected = bidService.findBids(user.getId(),0,1).getItems();
        assertEquals(expected, Collections.singletonList(bid));
        assertEquals(product.getWinner(),expected.get(0));
    }
    
    @Test
    public void testUpdatePriceByBid() 
    		throws DuplicateInstanceException, InstanceNotFoundException, BidAfterLimitDateException,
    		PriceUnderActualPriceException, BidForOwnProductException 
    {
    	User owner = createUserAndSignUp("owner", "passwd");
        User user = createUserAndSignUp("nombre", "passwd");
        Category category = createCategoryAndSave("category");
        Product product = createProduct(owner.getId(), "one product",10L ,category.getId());
        Bid bid = new Bid(product, user, LocalDateTime.now(), new BigDecimal(12));
        Bid bid2 = new Bid(product, user, LocalDateTime.now(), new BigDecimal(14));
        
        product.updatePriceByBid(bid);       
        assertEquals(product.getPrice(),product.getActualPrice());       
        product.updatePriceByBid(bid2);        
        assertEquals(new BigDecimal(12.5),product.getActualPrice());         
    }

    @Test
    public void testExample1()
            throws InstanceNotFoundException, BidAfterLimitDateException,
            PriceUnderActualPriceException, DuplicateInstanceException, BidForOwnProductException
    {
        User vendedor = createUserAndSignUp("vendedor", "passwd1");
        User compradorA = createUserAndSignUp("compradorA", "passwd2");
        Category category = createCategoryAndSave("category1");
        Product product = createProduct(vendedor.getId(), "product",10L, category.getId());
        Bid bidA = bidService.makeBid(compradorA.getId(),product.getId(),new BigDecimal(12));

        Bid expected = catalogService.findProductById(product.getId()).getWinner();
        BigDecimal expectedPrice = catalogService.findProductById(product.getId()).getActualPrice();
        assertEquals(expected, bidA);
        assertEquals(expectedPrice, new BigDecimal(10));
    }

    @Test
    public void testExample2()
            throws InstanceNotFoundException, BidAfterLimitDateException,
            PriceUnderActualPriceException, DuplicateInstanceException, BidForOwnProductException
    {
        User vendedor = createUserAndSignUp("vendedor", "passwd1");
        User compradorA = createUserAndSignUp("compradorA", "passwd2");
        User compradorB = createUserAndSignUp("compradorB", "passwd2");

        Category category = createCategoryAndSave("category1");
        Product product = createProduct(vendedor.getId(), "product",10L, category.getId());
        Bid bidA = bidService.makeBid(compradorA.getId(),product.getId(),new BigDecimal(12));
        Bid bidB = bidService.makeBid(compradorB.getId(),product.getId(),new BigDecimal(11));

        Bid expectedWinner = catalogService.findProductById(product.getId()).getWinner();
        BigDecimal expectedPrice = catalogService.findProductById(product.getId()).getActualPrice();
        assertEquals(expectedWinner, bidA);
        assertEquals(expectedPrice, new BigDecimal(11.5));
    }

    @Test
    public void testExample3()
            throws InstanceNotFoundException, BidAfterLimitDateException,
            PriceUnderActualPriceException, DuplicateInstanceException, BidForOwnProductException
    {
        User vendedor = createUserAndSignUp("vendedor", "passwd1");
        User compradorA = createUserAndSignUp("compradorA", "passwd2");
        User compradorB = createUserAndSignUp("compradorB", "passwd2");

        Category category = createCategoryAndSave("category1");
        Product product = createProduct(vendedor.getId(), "product",10L, category.getId());
        Bid bidA = bidService.makeBid(compradorA.getId(),product.getId(),new BigDecimal(12));
        Bid bidB = bidService.makeBid(compradorB.getId(),product.getId(),new BigDecimal(14));

        Bid expectedWinner = catalogService.findProductById(product.getId()).getWinner();
        BigDecimal expectedPrice = catalogService.findProductById(product.getId()).getActualPrice();
        assertEquals(expectedWinner, bidB);
        assertEquals(expectedPrice, new BigDecimal(12.5));
    }

    @Test
    public void testTwoBidsWithSamePrice()
            throws InstanceNotFoundException, BidAfterLimitDateException,
            PriceUnderActualPriceException, DuplicateInstanceException, BidForOwnProductException
    {
        User vendedor = createUserAndSignUp("vendedor", "passwd1");
        User compradorA = createUserAndSignUp("compradorA", "passwd2");
        User compradorB = createUserAndSignUp("compradorB", "passwd2");

        Category category = createCategoryAndSave("category1");
        Product product = createProduct(vendedor.getId(), "product",10L, category.getId());
        Bid bidA = bidService.makeBid(compradorA.getId(),product.getId(),new BigDecimal(12));
        Bid bidB = bidService.makeBid(compradorB.getId(),product.getId(),new BigDecimal(12));

        Bid expectedWinner = catalogService.findProductById(product.getId()).getWinner();
        BigDecimal expectedPrice = catalogService.findProductById(product.getId()).getActualPrice();
        assertEquals(expectedWinner, bidA);
        assertEquals(expectedPrice, new BigDecimal(12));
    }

    @Test
    public void testFindBids()
            throws InstanceNotFoundException, BidAfterLimitDateException,
            PriceUnderActualPriceException, DuplicateInstanceException, InterruptedException, BidForOwnProductException
    {
        User vendedor = createUserAndSignUp("vendedor", "passwd1");
        User compradorA = createUserAndSignUp("compradorA", "passwd2");

        Category category = createCategoryAndSave("category1");
        Product product = createProduct(vendedor.getId(), "product",10L, category.getId());
        Bid bidA = bidService.makeBid(compradorA.getId(),product.getId(),new BigDecimal(12));
        new Thread().sleep(2000);
        Bid bidB = bidService.makeBid(compradorA.getId(),product.getId(),new BigDecimal(14));

        List<Bid> expectedWinner = bidService.findBids(compradorA.getId(),0,2).getItems();
        assertEquals(Arrays.asList(bidB,bidA),expectedWinner);
    }
}
