package utils;


public class PayloadUtils {

    public static String getPayload(String id,String firstName,String lastName,String street,String city,String state,
                                    String zip) {
        return "{\n" +
                "    \"id\": \""+id+"\",\n" +
                "    \"name\": {\n" +
                "        \"firstName\": \""+firstName+"\",\n" +
                "        \"lastName\": \""+lastName+"\"\n" +
                "    },\n" +
                "    \"address\": {\n" +
                "        \"street\": \""+street+"\",\n" +
                "        \"city\": \""+city+"\",\n" +
                "        \"state\": \""+state+"\",\n" +
                "        \"zip\": \""+zip+"\"\n" +
                "    }\n" +
                "}";
    }


}
