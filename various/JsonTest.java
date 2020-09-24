package various;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonTest {

    public static void main(String[] args) {
        jacksonObjToString();
        jacksonStringToObj() ;
    }

    public static void jacksonObjToString() {
        SimpleBeans beans = new SimpleBeans();
        beans.setName("Jack");
        beans.setSet("FM");
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(beans));
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

    }

    public static void jacksonStringToObj() {
        String json = "{\"UserName\":\"Jack\",\"UserAges\":\"20\",\"sex\":\"M\"}";
        ObjectMapper mapper = new ObjectMapper();
        try {
            SimpleBeans bean = mapper.readValue(json, SimpleBeans.class);
            System.out.println(bean.toString());
        } catch (IOException e) {
             e.printStackTrace();
        }
    }
    public static void jsonToMap(){
        String json = "{\"UserName\":\"Jack\",\"UserAges\":\"20\",\"sex\":\"M\"}";
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map map = mapper.readValue(json, new TypeReference<Map<String,?>>(){}) ;
            map.forEach((k,v)-> System.out.println(k + ":" + v));
        } catch (IOException e) {
             e.printStackTrace();
        }
    }
}

class SimpleBeans{

    @JsonProperty("UserName")
    @JsonInclude(Include.NON_NULL)
    public String name ;

    @JsonProperty(value="UserAges")
    @JsonInclude(Include.NON_EMPTY)
    public String ages = "";


    public String sex = "M" ;

    @JsonIgnore
    public String getSchool(){
        return this.name ;
    }
    public String getName(){
        return this.name ;
    }
    public void setName(String name){
        this.name = name ;
    }

    public String getAges(){
        return this.ages ;
    }

    public void setAges(String ages){
        this.ages = ages ;
    }

    public String getSex(){
        return this.sex ;
    }
    public void setSet(String sex ){
        this.sex = sex ;
    }

    public String toString(){
        return this.getName() + this.getAges() + this.getSex() + this.getSchool() ;
    }

}
