package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Gabe on 10/31/2016.
 */

@Component
public class MyTasks {


    RestTemplate restTemplate = new RestTemplate();

    @Scheduled(cron = "*/1 * * * * *")
    @Scheduled(fixedRate = 50)
    public void addVehicle() {
        int id = greatestVehicle()+1;
        int randomVyear = randVehicleYr();
        int randRetailPrice =randRetail();
        String randLetter = randAlphaNumeric();
        String url = "http://localhost:8080/addVehicle";

        Vehicle testVehicle = new Vehicle(id,randLetter,randomVyear,randRetailPrice);
        restTemplate.postForObject(url,testVehicle,Vehicle.class);
        System.out.println("created" +testVehicle.getId());                        //Test

            }

    @Scheduled(cron = "* * * 3 * * ")
    public void getVehicle() {
        int randOneHundred1 = randOneHundred();
        int finalNumber = randOneHundred1;
        String url = "http://localhost:8080/getVehicle/"+ finalNumber;  //This will get vehicle between 1-100
        Vehicle fillVehicle = restTemplate.getForObject(url, Vehicle.class);
        System.out.println("got" + fillVehicle);                                        //Test

    }

    @Scheduled(cron = "* * 3 * * *")
    public void deleteVehicle(){
    int randOneHundred1 = randOneHundred();
    int randOneHundredFinal = randOneHundred1;
        String url = "http://localhost:8080/deleteVehicle/" +randOneHundredFinal;
        restTemplate.delete(url);
        System.out.println("deleted"+ randOneHundredFinal);
                                                                        //Will return null as test
    }


    @Scheduled(cron = "*/1 * * * * *")
        public void updateVehicle(){
        int randOneHundred1 = randOneHundred();
        int finalRandomHundred1 = randOneHundred1;
        String url = "http://localhost:8080/updateVehicle";
        Vehicle updateVehicle = new Vehicle(finalRandomHundred1,"1111",1111,11111);  // Fix this
        restTemplate.put(url, updateVehicle );
        String getUrl = "http://localhost:8080/getVehicle/"+finalRandomHundred1;
        Vehicle mrUpdateVehicle = restTemplate.getForObject(getUrl,Vehicle.class);
        System.out.println("updated" + mrUpdateVehicle);

    }


public int greatestVehicle() {
    int greatestId = 0;
    try {
        File file = new File("/Users/Gabe/Desktop/inventory.txt");
        LineIterator it = FileUtils.lineIterator(file, CharEncoding.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        while (it.hasNext()) {
            String line = it.nextLine();

            Vehicle vehicle = mapper.readValue(line, Vehicle.class);
            if (vehicle.getId() > greatestId)
               greatestId = vehicle.getId();
        }
    } catch (IOException e) {

    }
    return greatestId;
}

public int randVehicleYr() {
    // Vehicle year should be a random number between 1986-2016.
    //Vehicle retailPrice should be a random number between 15000-45000.
    int randomVYear = 1986;
    Random random = new Random();
    randomVYear = random.nextInt(2016 - 1986 + 1) + 1986;
    return randomVYear;
}

public int randRetail(){
    int randomRetailPrice = 2;
    Random random = new Random();
    randomRetailPrice = random.nextInt(45000-15000 + 1) +15000;
    return randomRetailPrice;
}

public String randAlphaNumeric(){
    String randLetter = "a";
    Random random = new Random();
    randLetter = RandomStringUtils.randomAlphabetic(10);
    return randLetter;
}

public int randOneHundred(){
    int randOneHundred1;
    Random random = new Random();
    randOneHundred1 = random.nextInt(100-2+2)+ 2;
    return randOneHundred1;
}



}



