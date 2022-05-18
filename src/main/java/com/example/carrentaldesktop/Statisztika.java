package com.example.carrentaldesktop;

import java.sql.SQLException;
import java.util.*;

public class Statisztika {
    public static List<Car> carList;

    public static void start(String[] args) {
        try {
            Beolvas();
            huszEzernelOlcsobb();
            VanEDragabb();
            LegdragabbAuto();
            MarkaCsoportositas();
            Bekeros();
            if (carList.isEmpty()){
                throw new Exception("Nincs könyv az adatbázisban!");
            }
        } catch (SQLException e) {
            System.out.println("Nem sikerült csatlakozni az adatbázishoz!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void Bekeros() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Adjon meg egy rendszámot: ");
        String rendszam = sc.next();

        int i = 0;
        while (i < carList.size() && !Objects.equals(carList.get(i).getLicense_plate_number(), rendszam)) {
            i++;
        }

        if (i < carList.size()) {
            System.out.printf("\tA megadott autó napidíja%snagyobb mint 25.000 Ft\n",
                    carList.get(i).getDaily_cost() > 25000 ? " " : " nem ");
        } else {
            System.out.println("Nincs ilyen autó");
        }
    }

    private static void MarkaCsoportositas() {
        Map<String, List<Car>> map = new HashMap<String, List<Car>>();

        for (Car car : carList) {
            String key  = car.getBrand();
            if(map.containsKey(key)){
                List<Car> list = map.get(key);
                list.add(car);
            }else {
                List<Car> list = new ArrayList<Car>();
                list.add(car);
                map.put(key, list);
            }
        }
        System.out.println("\nAutók száma:");
        for (Map.Entry<String, List<Car>> entry : map.entrySet()) {
            System.out.println("\t" + entry.getKey() + " " + entry.getValue().stream().count());
        }
    }

    private static void LegdragabbAuto() {
        Car longest = carList.get(0);
        for (int i = 0; i < carList.size(); i++) {
            if(carList.get(i).getDaily_cost() > longest.getDaily_cost()){
                longest = carList.get(i);
            }
        }
        System.out.printf("\nLegdrágább napidíjú autó: %s - %s - %s - %d", longest.getLicense_plate_number(),
                longest.getBrand(), longest.getModel(), longest.getDaily_cost());
    }

    private static void VanEDragabb() {
        int count = 0;
        while (count < carList.size() && carList.get(count).getDaily_cost() >= 26000) {
            count++;
        }
        boolean yes = count < carList.size();
        System.out.printf("%s az adatok között 26.000 Ft-nál drágább napidíjú autó", yes ? "Van" : "Nincs");
    }

    private static void huszEzernelOlcsobb() {
        int count = 0;
        for (Car car: carList) {
            if (car.getDaily_cost() > 20000) {
                count++;
            }
        }
        System.out.println("20.000 Ft-nál olcsóbb napidíjú autók száma:" + count);
    }


    private static void Beolvas() throws SQLException {
        CarDB db = new CarDB();
        carList = db.getCar();
    }
}
