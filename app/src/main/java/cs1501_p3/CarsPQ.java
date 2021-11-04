package cs1501_p3;



import java.util.NoSuchElementException;
import java.io.*;
import java.util.*;

public class CarsPQ implements CarsPQ_Inter {
    private ArrayList<Car> pricepq = new ArrayList<>();
    private ArrayList<Car> milepq = new ArrayList<>();
    private int psize = 0;
    private int msize = 0;

    public CarsPQ(){

    }
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
                }
            }
        } catch (IOException e) {
            System.out.println("An Error Occurred");
            e.printStackTrace();
        }
    }


    public void add(Car c) throws IllegalStateException {
        pricepq.add(c);
        milepq.add(c);
        psize++;
        msize++;
        int k = psize-1;
        int j = msize-1;

        while (k > 1 && (pricepq.get(k/2).getPrice() > c.getPrice())){
            pricepq.set(k, pricepq.get(k/2));
            k /= 2;
        }
        pricepq.set(k, c);

        while (j > 1 && (milepq.get(j/2).getMileage() > c.getMileage())){
            milepq.set(j, milepq.get(j/2));
            j /= 2;
        }
        milepq.set(j, c);

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

        int ptrack = 0;
        for (Car x : pricepq) {
            if (x.getVIN().equals(vin)) {
                pricepq.remove(ptrack);
            }
            ptrack++;
        }
        pheapify(pricepq, 0);
        psize--;

        int mtrack = 0;
        for (Car y : milepq) {
            if (y.getVIN().equals(vin)) {
                milepq.remove(mtrack);
            }
            mtrack++;
        }
        mheapify(milepq, 0);
        msize--;

        throw new NoSuchElementException("There is no car with that VIN in the PQ");

    }

    private void pheapify(ArrayList<Car> pq, int vroot)
    {
        Car c = pq.get(vroot);
        int child, k = vroot;
        while (2*k <= psize)
        {
            child = 2*k;
            if (child < psize && (pq.get(child).getPrice() > pq.get(child+1).getPrice()))
            {
                child++;
            }
            if (c.getPrice() <= pq.get(child).getPrice())
            {
                break;
            }
            else
            {
                pq.set(k, pq.get(child));
                k = child;
            }
        }
        pq.set(k, c);
    }

    private void mheapify(ArrayList<Car> pq, int vroot)
    {
        Car c = pq.get(vroot);
        int child, k = vroot;
        while (2*k <= msize)
        {
            child = 2*k;
            if (child < msize && (pq.get(child).getMileage() > pq.get(child+1).getMileage()))
            {
                child++;
            }
            if (c.getMileage() <= pq.get(child).getMileage())
            {
                break;
            }
            else
            {
                pq.set(k, pq.get(child));
                k = child;
            }
        }
        pq.set(k, c);
    }


    public Car getLowPrice() {
        return pricepq.get(0);
    }


    public Car getLowPrice(String make, String model) {
        ArrayList<Car> specific = new ArrayList<>();
        for(Car x:pricepq){
            if(x.getMake().equals(make) && x.getModel().equals(model)){
                specific.add(x);
            }
        }
        Car min = specific.get(0);
        for(int i = 0; i<specific.size(); i++){
            if(specific.get(i).getPrice() < min.getPrice()){
                min = specific.get(i);
            }
        }
        return min;
    }


    public Car getLowMileage() {
        return milepq.get(0);
    }


    public Car getLowMileage(String make, String model) {
        ArrayList<Car> specific = new ArrayList<>();
        for(Car x:milepq){
            if(x.getMake().equals(make) && x.getModel().equals(model)){
                specific.add(x);
            }
        }
        Car min = specific.get(0);
        for(int i = 0; i<specific.size(); i++){
            if(specific.get(i).getMileage() < min.getMileage()){
                min = specific.get(i);
            }
        }
        return min;
    }
}
