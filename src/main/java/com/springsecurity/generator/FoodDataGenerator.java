package com.springsecurity.generator;

import com.springsecurity.demo.entities.Food;
import com.springsecurity.parser.WikipediaParser;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Component
public class FoodDataGenerator {

    @PersistenceContext
    private EntityManager entityManager;
    private static final String URL = "https://en.wikipedia.org/wiki/List_of_desserts";
    private static final String TAG = "a";

    @Transactional
    public void persistData(Boolean doExecute) {
        if(doExecute) {
            WikipediaParser parser = null;
            try {
                parser = new WikipediaParser(URL, TAG);
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<String> data = Objects.requireNonNull(parser).parse();
            List<String> cut = data.subList(151, data.size());

            for (String datum : cut) {
                Food food = new Food();
                food.setCategory("sweets");
                food.setIsImported(1);
                food.setIsStock(1);
                food.setMaxStock(generateStock());
                food.setName(datum);
                food.setPrice(BigDecimal.valueOf(generatePrice()));

                entityManager.persist(food);
            }
        }
    }

    private Integer generateStock() {
        return new Random().nextInt(1000);
    }

    private Double generatePrice() {
        Random random = new Random();
        int beforePoint = random.nextInt(10);
        int afterPoint = random.nextInt(100);
        String price = String.valueOf(beforePoint) + "." + String.valueOf(afterPoint);

        return Double.valueOf(price);
    }
}
