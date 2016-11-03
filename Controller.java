package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import static org.apache.commons.lang3.CharEncoding.UTF_8;

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
                UTF_8,
                true);

        return newVehicle;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    @RequestMapping(value = "/getVehicle/{id}", method = RequestMethod.GET)
    public Vehicle getVehicle(@PathVariable("id") int id) throws IOException {

        File file = new File ("/Users/Gabe/Desktop/inventory.txt");
        LineIterator it = FileUtils.lineIterator(file, UTF_8);
        ObjectMapper mapper = new ObjectMapper();

            while (it.hasNext()) {
                String line = it.nextLine();

                Vehicle vehicle = mapper.readValue(line, Vehicle.class);
                 if(vehicle.getId()== id){
                     System.out.println(line);
                     return vehicle;
                 }else{
                     //Empty loop to facilitate single get method.
                 }


                 }
        LineIterator.closeQuietly(it);
        return null; //System.out.println("Vehicle not found.");

            }





    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    @RequestMapping(value = "/updateVehicle", method = RequestMethod.PUT)
    public Vehicle updateVehicle(@RequestBody Vehicle newVehicle) throws IOException {

        ArrayList<Vehicle> vehicles1 = new ArrayList<Vehicle>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            String message = FileUtils.readFileToString(new File("/Users/Gabe/Desktop/inventory.txt"), CharEncoding.UTF_8);
            String [] lines = StringUtils.split(message, "\n");
            for ( int i = 0 ; i < lines.length; i ++){
                Vehicle vehicle1 = mapper.readValue(lines[i],Vehicle.class);
                vehicles1.add(vehicle1);
               if( vehicles1.get(i).getId() == newVehicle.getId()){
                   vehicles1.get(i).setMakeModel(newVehicle.getMakeModel());
                   vehicles1.get(i).setRetailPrice(newVehicle.getRetailPrice());
                   vehicles1.get(i).setYear(newVehicle.getYear());
               }
            }
            PrintWriter pw = new PrintWriter("/Users/Gabe/Desktop/inventory.txt");
            pw.close();

            for(int i =0; i < vehicles1.size(); i++){
                ObjectMapper mapper2 = new ObjectMapper();
                FileWriter output = new FileWriter("/Users/Gabe/Desktop/inventory.txt", true);
                mapper2.writeValue(output,vehicles1.get(i));
                FileUtils.writeStringToFile(new File("/Users/Gabe/Desktop/inventory.txt"),System.lineSeparator(),CharEncoding.UTF_8,true);
            }

        }
        catch (IOException e){
            e.printStackTrace();
        }
        return newVehicle;
    }



    //                                              IGNORE                                              //




//        File updateVehicleUrl = new File ("/Users/Gabe/Desktop/inventory.txt");
//        LineIterator updateVehicleIterator= FileUtils.lineIterator(updateVehicleUrl);
//        ObjectMapper mapper = new ObjectMapper();   // main mapper
//
//
//
//            while (updateVehicleIterator.hasNext()) {       //iterate through while there are still lines
//                String line = updateVehicleIterator.nextLine(); //saved as string in line
//                Vehicle vehicleUpdate = mapper.readValue(line,Vehicle.class);
//                if (newVehicle.getId() == vehicleUpdate.getId()){
//
//                    //FileUtils.writeStringToFile(updateVehicleUrl, "", CharEncoding.UTF_8);
//                    mapper.writeValue(updateVehicleUrl, newVehicle);
//                return null;
//                } else {
//                    //yay
//                }
//            }
//
//            LineIterator.closeQuietly(updateVehicleIterator);
//         return null;
 //   }





    //                                          IGNORE ABOVE                                    //
    ///////////////////////////////////////////////////////////////////////////////////////////////////////


    @RequestMapping(value = "/deleteVehicle/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteVehicle(@PathVariable("id") int id) throws IOException {
    ArrayList<Vehicle> vehicles = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try{
            String message = FileUtils.readFileToString(new File("/Users/Gabe/Desktop/inventory.txt"), UTF_8);
            String[]lines = StringUtils.split(message, '\n');
            for(int i = 0; i <lines.length; i++){
                Vehicle vehicle = mapper.readValue(lines[i],Vehicle.class);
                //vehicles.add(vehicle);
                if(vehicle.getId() != id){
                    vehicles.add(vehicle); /// use for update
                }
            }
            PrintWriter deleteVehiclePrintWriter = new PrintWriter("/Users/Gabe/Desktop/inventory.txt");
            deleteVehiclePrintWriter.close();

            for(int i=0; i<vehicles.size();i++){
                ObjectMapper deleteVehicleMapper = new ObjectMapper();
                FileWriter finalOutput= new FileWriter("/Users/Gabe/Desktop/inventory.txt", true);
                deleteVehicleMapper.writeValue(finalOutput,vehicles.get(i));

                FileUtils.writeStringToFile(new File ("/Users/Gabe/Desktop/inventory.txt"),System.lineSeparator(), UTF_8,true);
            }

        } catch (IOException e){ e.printStackTrace();
    }
    return null;}}









//                                      IGNORE                                  //



    //ResponseEntity<String> responseEntity = new ResponseEntity<>("Vehicle successfully deleted", HttpStatus.OK);
        //return responseEntity;}}


//        File filetoDeleteFrom = new File("/Users/Gabe/Desktop/inventory.txt");
//        ObjectMapper mapper = new ObjectMapper();
//        ArrayList<String> list = new ArrayList<>();
//        String[] lines = StringUtils.split("\r\n");
//        for(int i=0; i<lines.length; i++)
//            Vehicle car1 = mapper.ReadValue(lines[i],Vehicle.class);
//        if(cars.get(i).getId() == id)
//
//
//        String str = FileUtils.readFileToString(filetoDeleteFrom, "UTF-8");
//        String file = "/Users/Gabe/Desktop/inventory.txt";
//
//        LineIterator deleteIterator = FileUtils.lineIterator(filetoDeleteFrom, "UTF-8");
//        ObjectMapper deleteMapper = new ObjectMapper();
//
//            while (deleteIterator.hasNext()) {
//                String line = deleteIterator.nextLine();
//                Vehicle deleteVehicle = deleteMapper.readValue(line, Vehicle.class);
//                if (deleteVehicle.getId() == id) {
//
//                    int lineNumber = deleteVehicle.getId();
//                    int lineToBeEdited = lineNumber;
//                    System.out.println(lineToBeEdited);
//                    ChangeLineInFile changeFile = new ChangeLineInFile();
//                 changeFile.changeALineInATextFile(file, "", lineToBeEdited);
//
//                    JFileChooser chooser = new JFileChooser();
//
//
//
//                    //FileWriter deleteAppend = new FileWriter(filetoDeleteFrom,true);
//                    //deleteAppend.write("THINGS ARE NOT HAPPENING");
//                    // FileUtils.writeStringToFile(filetoDeleteFrom, "", "UTF-8");//CharEncoding.UTF_8);
//                }
//            }
//
//            LineIterator.closeQuietly(deleteIterator);
//            return null;
//        }
//
//        }

