package com.example.modabba;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Menu {

    private Map<String,ArrayList<String>> menu_sub_item_veg_lunch = new HashMap<>();
    private Map<String,ArrayList<String>> menu_sub_item_veg_dinner = new HashMap<>();
    private Map<String,ArrayList<String>> menu_sub_item_nonveg_lunch = new HashMap<>();
    private Map<String,ArrayList<String>> menu_sub_item_nonveg_dinner = new HashMap<>();

    public Map<String, ArrayList<String>> getMenu_sub_item_nonveg_lunch() {

        menu_sub_item_nonveg_lunch.put("Rice",new ArrayList<String>(){{
            add("Plain Rice");
            add("Zira Rice");
            add("Khichdi");
            add("Curd Rice");
            add("Veg Pulao");
            add("Fried Rice");
            add("Chicken Biryani");
            add("Egg Biryani");
        }});
        menu_sub_item_nonveg_lunch.put("Dal",new ArrayList<String>(){{
            add("Mug Dal");
            add("Harad Dal");
            add("Masur Dal");
            add("Dal Fry");
        }});
        menu_sub_item_nonveg_lunch.put("Curry",new ArrayList<String>(){{
            add("Fish Curry");
            add("Fish Fry/Kalia");
            add("Fish Besara Masala");
            add("Chicken Masala");
            add("Chicken Butter Masala");
            add("Egg Fry");
            add("Egg Omlet Curry");
        }});
        menu_sub_item_nonveg_lunch.put("Bhaji/Khata",new ArrayList<String>(){{
            add("Mix Bhaji");
            add("Saga Bhaji");
            add("Baigana");
            add("Alu Bhaji Chutney");
            add("Mango Khata");
            add("Plastic Chutney");
            add("Tomato Chutney");
        }});
        menu_sub_item_nonveg_lunch.put("Salad",new ArrayList<String>(){{
            add("Veg Salad");
            add("Fruit Salad");
        }});
        menu_sub_item_nonveg_lunch.put("Raita",new ArrayList<String>(){{
            add("Dahi Bundi");
            add("Dahi Alu");
            add("Dahi Baigan");
        }});
        return menu_sub_item_nonveg_lunch;
    }

    public Map<String, ArrayList<String>> getMenu_sub_item_nonveg_dinner() {
        menu_sub_item_nonveg_dinner.put("Roti",new ArrayList<String>(){{
            add("Puri");
            add("Paratha");
            add("Naan");
        }});
        menu_sub_item_nonveg_dinner.put("Curry",new ArrayList<String>(){{
            add("Egg Bhujia");
            add("Chicken Masala");
            add("Chicken Kasa");
            add("Chilli Chicken");
            add("Chicken Mughalai");
            add("Egg Tadaka");
        }});
        menu_sub_item_nonveg_dinner.put("Dessert",new ArrayList<String>(){{
            add("Kheeri");
            add("Sweets");
        }});
        return menu_sub_item_nonveg_dinner;
    }

    public Map<String, ArrayList<String>> getMenu_sub_item_veg_dinner() {

        menu_sub_item_veg_dinner.put("Roti",new ArrayList<String>(){{
            add("Puri");
            add("Paratha");
            add("Fried Naan");
            add("Thunka Puri");
        }});
        menu_sub_item_veg_dinner.put("Bhaji",new ArrayList<String>(){{
            add("Plain Bhaji");
            add("Aloo Bhaji");
            add("Vendi Bhaji");
        }});
        menu_sub_item_veg_dinner.put("Curry",new ArrayList<String>(){{
            add("Dal Fry");
            add("Dal Tadaka");
            add("Chana Masala");
            add("Matar Paneer");
            add("Palang Paneer");
            add("Mix Veg");
            add("Soya Chilly");
        }});
        menu_sub_item_veg_dinner.put("Dessert/Sweets",new ArrayList<String>(){{
            add("Rice Kheer");
            add("Semiya");
            add("Custard");
            add("Sweets");
        }});
        return menu_sub_item_veg_dinner;
    }

    public Map<String, ArrayList<String>> getMenu_sub_item_veg_lunch() {
        menu_sub_item_veg_lunch.put("Rice",new ArrayList<String>(){{
            add("Plain Rice");
            add("Zira Rice");
            add("Khichdi");
            add("Curd Rice");
            add("Veg Pulao");
            add("Fried Rice");
            add("Veg Biryani");
        }});
        menu_sub_item_veg_lunch.put("Dal",new ArrayList<String>(){{
            add("Mug Dal");
            add("Harad Dal");
            add("Masur Dal");
            add("Dal Fry");
            add("Punjabi Dal");
            add("Dalma");
        }});
        menu_sub_item_veg_lunch.put("Curry",new ArrayList<String>(){{
            add("Plain Paneer");
            add("Paneer Butter");
            add("Soya Curry");
            add("Dhoka Curry");
            add("Palang Paneer");
        }});
        menu_sub_item_veg_lunch.put("Veg Curry",new ArrayList<String>(){{
            add("Mix Veg");
            add("Kobi Fry");
            add("Kobi Masala");
            add("Veg Santula Masala");
            add("Rasa Items");
            add("Baigan Masala");

        }});
        menu_sub_item_veg_lunch.put("Bhaji/Khata",new ArrayList<String>(){{
            add("Mix Bhaji");
            add("Saga Bhaji");
            add("Alu Bhaji Chutney");
            add("Chifs");
            add("Mango Khata");
            add("Plastic Chutney");
            add("Tomato Chutney");
        }});
        menu_sub_item_veg_lunch.put("Salad",new ArrayList<String>(){{
            add("Veg Salad");
            add("Fruit Salad");
            add("Dahi Bundi");
            add("Dahi Alu");
            add("Dahi Baigan");
        }});
        menu_sub_item_veg_lunch.put("Raita",new ArrayList<String>(){{
            add("Dahi Bundi");
            add("Dahi Alu");
            add("Dahi Baigan");
        }});
        return menu_sub_item_veg_lunch;
    }
}
