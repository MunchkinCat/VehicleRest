package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Gabe on 10/28/2016.
 */


///////////////////////////////////////////////////////////////////////////////////////////////////////

@RestController
public class Controller {

    @RequestMapping(value = "/addVehicle", method = RequestMethod.POST)
    public Vehicle addVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        FileWriter output = new FileWriter("/Users/Gabe/Desktop/inventory.txt", true);
        mapper.writeValue(output, newVehicle);
        FileUtils.writeStringToFile(new File("/Users/Gabe/Desktop/inventory.txt"),
                System.lineSeparator(),
                CharEncoding.UTF_8,
                true);

        return newVehicle;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    @RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) throws IOException {

        File file = new File ("/Users/Gabe/Desktop/inventory.txt");
        LineIterator it = FileUtils.lineIterator(file, CharEncoding.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        try {
            while (it.hasNext()) {
                String line = it.nextLine();

                Vehicle vehicle = mapper.readValue(line, Vehicle.class);
                 if (vehicle.getId()== id){
                     return vehicle;
                 }else{
                     System.out.println("Vehicle not found.");
                 }

            System.out.println(line);


            }
        } finally {
            LineIterator.closeQuietly(it);
        }
        return null;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    @RequestMapping(value = "/updateVehicle", method = RequestMethod.PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle newVehicle) throws IOException {
        File updateVehicleUrl = new File ("/Users/Gabe/Desktop/inventory.txt");
        LineIterator updateVehicleIterator= FileUtils.lineIterator(updateVehicleUrl);
        ObjectMapper mapper = new ObjectMapper();   // main mapper

        try {

            while (updateVehicleIterator.hasNext()) {       //iterate through while there are still lines
                String line = updateVehicleIterator.nextLine(); //saved as string in line
                Vehicle vehicleUpdate = mapper.readValue(line,Vehicle.class);
                if (newVehicle.getId() == vehicleUpdate.getId()){

                    FileUtils.writeStringToFile(updateVehicleUrl, "", CharEncoding.UTF_8);
                    mapper.writeValue(updateVehicleUrl, newVehicle);

                } else {
                    System.out.println("ID invalid or not found.");
                }
            }
        } finally {
            LineIterator.closeQuietly(updateVehicleIterator);
        }
        return null;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    @RequestMapping(value = "/deleteVehicle/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") int id) throws IOException {
        File deleteVehicleFromFile = new File("/Users/Gabe/Desktop/inventory.txt");
        LineIterator deleteIterator = FileUtils.lineIterator(deleteVehicleFromFile, "UTF-8");
        ObjectMapper deleteMapper = new ObjectMapper();

        try {
            while (deleteIterator.hasNext()) {
                String line = deleteIterator.nextLine();
                Vehicle deleteVehicle = deleteMapper.readValue(line, Vehicle.class);
                if (deleteVehicle.getId() == id) {
                    FileUtils.writeStringToFile(deleteVehicleFromFile, "", "UTF-8");//CharEncoding.UTF_8);
                }
            }
        } finally {
            LineIterator.closeQuietly(deleteIterator);
        }
        return null;
        }}

