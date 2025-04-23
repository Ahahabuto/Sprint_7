import api.OrderApi;
import api.OrderCreationData;
import api.Specifications;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class GetOrderListTest {
    private int trackId;

    @Before
    public void setUp(){
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec201Created());

        OrderCreationData order = new OrderCreationData(
                "Luffy",
                "Monkey D",
                "GrandLine, 12",
                3,
                "88005553535",
                2,
                "2025-10-10",
                "Я стану королём пиратов!",
                new ArrayList<>(List.of("BLACK"))
        );

        Response response = OrderApi.createOrder(order);
        this.trackId = response.path("track");
    }

    @Test
    public void testOrderListHasReturned(){
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec200Ok());

        OrderApi.getList()
                .then()
                .log().all()
                .body("orders", is(not(empty())));
    }
    @After
    public void tearDown() {
        if (trackId != 0) {
            try {
                RestAssured.reset();

                System.out.println("Отмена заказа: " + trackId);

                OrderApi.cancelOrder(trackId)
                        .then()
                        .log().all();
            } catch (Exception e) {
                System.err.println("Отменить заказ не удалось " + e.getMessage());
            }
        }
    }
}
