package utils;



import java.util.Map;

public class PatientsUtils {



    public static boolean verifyID(Map<String,Object> patient) {
        Map<String, String> fullname = (Map<String, String>) patient.get("name");

            if (patient.get("id").toString()
                    .contains(fullname.get("firstName").substring(0,1)+fullname.get("lastName").substring(0,1)
            +patient.get("birthdate").toString()
                            .replace("-","")+patient.get("appointment_date")
                            .toString().replace("-",""))){
            return true;
        }
        return false;
    }
    }

