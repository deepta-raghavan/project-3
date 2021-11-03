package cs1501_p3;


import java.util.NoSuchElementException;
import java.io.*;
import java.util.Scanner;

public class CarsPQ implements CarsPQ_Inter {
    private Car[] pricepq = new Car[20];
    private Car[] milepq = new Car[20];
    private int psize = 0;
    private int msize = 0;

    public CarsPQ(String fileName) {
        try (Scanner s = new Scanner(new File(fileName))) {
            if (s.hasNextLine()) {
                String line = s.nextLine();

                while (s.hasNext()) {
                    line = s.nextLine();
                    String[] attributes = line.split(":");
                    String vin = attributes[0];
                    String make = attributes[1];
                    String model = attributes[2];
                    int price = Integer.parseInt(attributes[3]);
                    int mileage = Integer.parseInt(attributes[4]);
                    String color = attributes[5];

                    Car c = new Car(vin, make, model, price, mileage, color);
                    add(c);
                    psize++;
                    msize++;
                }
            }
        } catch (IOException e) {
            System.out.println("An Error Occurred");
            e.printStackTrace();
        }
    }


    public void add(Car c) throws IllegalStateException {
        pricepq[++psize] = c;
        milepq[++msize] = c;
        int pcurrent = psize;
        int mcurrent = msize;

        if(pricepq == null){
            for(int i = 0; i< pricepq.length; i++){
                pricepq[i] = c;
            }
        }
        while(pricepq[pcurrent].getPrice() < pricepq[parent(pcurrent)].getPrice()){
            pswap(pcurrent, parent(pcurrent));
            pcurrent = parent(pcurrent);
        }

        while(milepq[mcurrent].getMileage() < milepq[parent(mcurrent)].getMileage()){
            mswap(mcurrent, parent(mcurrent));
            mcurrent = parent(mcurrent);
        }

    }

    private int parent(int pos){
        return pos/2;
    }

    private void pswap(int fpos, int spos){
        Car temp;
        temp = pricepq[fpos];
        pricepq[fpos] = pricepq[spos];
        pricepq[spos] = temp;
    }

    private void mswap(int fpos, int spos){
        Car temp;
        temp = milepq[fpos];
        milepq[fpos] = milepq[spos];
        milepq[spos] = temp;
    }


    public Car get(String vin) throws NoSuchElementException {
        for (Car x : pricepq) {
            if (x.getVIN().equals(vin)) {
                return x;
            }
        }
        throw new NoSuchElementException("There is no car with that VIN in the PQ");
    }


    public void updatePrice(String vin, int newPrice) throws NoSuchElementException {
        for (Car x : pricepq) {
            if (x.getVIN().equals(vin)) {
                x.setPrice(newPrice);
            }
        }
        throw new NoSuchElementException("There is no car with that VIN in the PQ");
    }


    public void updateMileage(String vin, int newMileage) throws NoSuchElementException {
        for (Car x : milepq) {
            if (x.getVIN().equals(vin)) {
                x.setMileage(newMileage);
            }
        }
        throw new NoSuchElementException("There is no car with that VIN in the PQ");
    }


    public void updateColor(String vin, String newColor) throws NoSuchElementException {
        for (Car x : pricepq) {
            if (x.getVIN().equals(vin)) {
                x.setColor(newColor);
            }
        }
        throw new NoSuchElementException("There is no car with that VIN in the PQ");
    }


    public void remove(String vin) throws NoSuchElementException {
        int track = 0;
        for (Car x : pricepq) {
            if (x.getVIN().equals(vin)) {
                //pq.remove(track);
            }
            track++;
        }
        throw new NoSuchElementException("There is no car with that VIN in the PQ");

    }

    @Override
    public Car getLowPrice() {
        return null;
        /*for(int i = 0; i < pq.length; i++){
            pq[i]
        }*/
    }

    @Override
    public Car getLowPrice(String make, String model) {
        return null;
    }

    @Override
    public Car getLowMileage() {
        return null;
    }

    @Override
    public Car getLowMileage(String make, String model) {
        return null;
    }
}
