import api.OrderApi;
import api.OrderCreationData;
import api.Specifications;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationTest {
    private final List<String> colors;
    private int trackId;

    public OrderCreationTest(List<String> colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        return Arrays.asList(new Object[][]{
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {null}
        });
    }

    @Test
    public void createOrderWithDifferentColorsTest(){
        Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec201Created());
        ArrayList<String> colorsList;
        if (colors!=null) {
            colorsList = new ArrayList<>(colors);
        } else {
            colorsList = null;
        }


        OrderCreationData order = new OrderCreationData(
                "Zoro",
                "Roronoa",
                "GrandLine, 4",
                4,
                "88005553535",
                3,
                "2025-05-05",
                "I'm lost",
                colorsList
        );

        OrderApi.createOrder(order)
                .then()
                .log().all()
                .body("track", notNullValue());
    }

    @After // в postman отмена заказа так же не работает: создаю заказ, копирую track, вставляю в тело запроса put на ручку /api/v1/orders/cancel, получаю ответ 400 Bad Request "Недостаточно данных для поиска"
    public void tearDown() {
        if (trackId != 0) {
            Specifications.installSpecification(Specifications.requestSpec(), Specifications.responseSpec200Ok());

            OrderApi.cancelOrder(trackId);
        }
    }
}