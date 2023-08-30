package org.ibs;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestClass  extends BDConnection {

    private static JdbcTemplate jdbcTemplateObject= new JdbcTemplate(dataSource);
    /**
     * Товар который планиуем добавлять:
     */
    Metod.Food testFood = new Metod.Food(0, "Citrus limon(☼) _Azerbaijan 5HPH+PPC, Unnamed Road, Pərvanlı", "FRUIT", 0);




    @Test
    public void testID_1() throws SQLException {

        /**
         * Проверить наличие  в таблице столбцов соответствующих полям "Наименование", "Тип", "Экзотический"
         */
        List<String> colomsName= Metod.getColomsName();
        List<String> colomsName0 = new ArrayList<String>();
        colomsName0.add("FOOD_NAME");
        colomsName0.add("FOOD_TYPE");
        colomsName0.add("FOOD_EXOTIC");

        Assertions.assertTrue(colomsName.containsAll(colomsName0));
        //System.out.println("проверка наличия полей -"+colomsName.containsAll(colomsName0));
        /**
         * Зафиксировать значение FOOD_ID из первой строки (максимальное значение)
         */
        int rowsStart, rowsEnd ;
        List<Metod.Food> foodMaxId= Metod.getFoodMaxId();
        rowsStart =foodMaxId.get(0).foodId();

        /**
         *  Проверка отсутсвие записи в таблице FOOD с характеристиками  testFood
         */
        List<Metod.Food> foodTest= Metod.getFoodTest();
        int foodTestSize= foodTest.size();
        Assertions.assertTrue(foodTestSize==0, "Товара который мы планируем добавть уже есть в таблице, необходимо его удалить или изменить характеристики товрат");

        /**
         * пункты 5-7 тест-кейса (добавление товара через форму на сайте с помощью selenium потому, что изначально так написан тест-кейс)
        */
        AddFood addFood = new AddFood();
        addFood.addFoodForm();
        /**
         * получение данных добавленной завписи таблицы
         */

        List<Metod.Food> foodTestRes= Metod.getFoodTest();
        rowsEnd = foodTestRes.get(0).foodId();
        /**
         * проверка: значение ID добавленного товата на 1 больше (добавился новый товар, а не изменился существующий)
         */
        Assertions.assertTrue(rowsEnd-rowsStart==1,"id добавленного не изменилось 1 - товар не добавлен");
        //проверка соответсвия данных по столбцам
        Assertions.assertAll("проверка полей добавленного товара",
                ()-> foodTestRes.get(0).foodName().equals(testFood.foodName()),
                ()-> foodTestRes.get(0).foodType().equals(testFood.foodType()),
                ()-> foodTestRes.get(0).foodExotic().equals(testFood.foodExotic()));


    }
@AfterAll
    public static void deletFood() {

        Metod.Food testFood = new Metod.Food(0, "Citrus limon(☼) _Azerbaijan 5HPH+PPC, Unnamed Road, Pərvanlı", "FRUIT", 0);
        String sql = "DELETE FROM FOOD WHERE FOOD_NAME= ? and FOOD_TYPE=? and FOOD_EXOTIC=? ";
        jdbcTemplateObject.update(sql, new Object[]{testFood.foodName(), testFood.foodType(), testFood.foodExotic()});

        System.out.println("Тестовый товар удален");


    }
}