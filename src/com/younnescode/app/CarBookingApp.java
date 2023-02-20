package com.younnescode.app;

import com.younnescode.booking.Booking;
import com.younnescode.booking.BookingDAO;
import com.younnescode.booking.BookingFileDataAccessService;
import com.younnescode.booking.BookingService;
import com.younnescode.car.Car;
import com.younnescode.car.CarDAO;
import com.younnescode.car.CarFileDataAccessService;
import com.younnescode.car.CarService;
import com.younnescode.user.UserDAO;
import com.younnescode.user.UserFileDataAccessService;
import com.younnescode.user.UserService;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class CarBookingApp {

    private static Scanner scan = new Scanner(System.in);

    public static UserDAO userFileDataAccessService = new UserFileDataAccessService();
    public static UserService userService = new UserService(userFileDataAccessService);
    public static CarDAO carFileDataAccessService = new CarFileDataAccessService();
    public static CarService carService = new CarService(carFileDataAccessService);
    public static BookingDAO bookingFileDataAccessService = new BookingFileDataAccessService();
    private static BookingService bookingService = new BookingService(bookingFileDataAccessService, carService);

    public static void exit() {
        System.out.println("\uD83D\uDC4B Bye Bye \uD83D\uDE04");
        System.exit(0);
    }

    private static void notValidOption() {
        System.out.println("⛔ Not a valid option\n");
        System.out.println();
        showMenu();
    }

    private static void noAvailableCars() {
        System.out.println("❌ No Available Cars\n");
        System.out.println();
        showMenu();
    }

    private static void noAvailableElectricCars() {
        System.out.println("❌ No Available Electric Cars\n");
        System.out.println();
        showMenu();
    }

    private static void noBookingsAvailable() {
        System.out.println("❌ No Bookings Available\n");
        System.out.println();
        showMenu();
    }

    private static void noCarsBooked() {
        System.out.println("❌ This user has no cars Booked");
        System.out.println();
        showMenu();
    }

    private static void noMatchingBook() {
        System.out.println("⛔ Not a valid option\n");
        System.out.println();
        bookCar();
    }

    private static void noMatchingViewBookings() {
        System.out.println("⛔ Not a valid option\n");
        System.out.println();
        viewAllUsersBookedCars();
    }

    private static void success(Booking booking) {
        System.out.println("\uD83D\uDD11 You Successfully Booked A Car! \uD83C\uDF89 \uD83C\uDF89 \uD83C\uDF89\n");
        System.out.println("\uD83D\uDD11 " + booking);
        System.out.println();
        showMenu();
    }

    private static void showUsers() {
        var users = userService.getUsers();

        for (var user : users) {
            if(user != null) {
                System.out.println("\uD83D\uDE09 " + user);
            }
        }
    }

    private static void showAvailableCars() {
        var availableCars = carService.getAvailableCars();

        if(!Arrays.stream(availableCars).allMatch(Objects::isNull)) {
            for (Car availableCar : availableCars) {
                if(availableCar != null) {
                    System.out.println("\uD83D\uDE97 " + availableCar);
                }
            }
        } else {
            noAvailableCars();
        }
    }

    private static void showAvailableElectricCars() {
        var availableElectricCars = carService.getAvailableElectricCars();

        if(!Arrays.stream(availableElectricCars).allMatch(Objects::isNull)) {
            for (var availableElectricCar : availableElectricCars) {
                if(availableElectricCar != null) {
                    System.out.println("\uD83D\uDE97 " + availableElectricCar);
                }
            }
        } else {
            noAvailableElectricCars();
        }
    }

    private static void showBookings(Booking[] bookings) {
        for (var booking : bookings) {
            if(booking != null) {
                System.out.println("\uD83D\uDD11 " + booking);
            }
        }
        System.out.println();
        showMenu();
    }

    private static String askForUserId() {
        System.out.print("\uD83D\uDD0D Select User ID: ");
        String userId = scan.nextLine();
        return userId;
    }

    private static String askForRegNumber() {
        System.out.print("\uD83D\uDD0D Select Car REG NUMBER: ");
        String regNumber = scan.nextLine();
        return regNumber;
    }

    private static void bookCar() {
        showAvailableCars();
        System.out.println();
        String regNumber = askForRegNumber();
        System.out.println();
        if(carService.getAvailableCarByRegNumber(regNumber) != null) {
            showUsers();
            System.out.println();
            String userId = askForUserId();
            System.out.println();
            if(userService.getUserById(userId) != null) {
                var user = userService.getUserById(userId);
                var car = carService.getAvailableCarByRegNumber(regNumber);
                var booking = bookingService.addBooking(user, car);
                success(booking);
            } else {
                noMatchingBook();
            }
        } else {
            noMatchingBook();
        }
    }

    private static void viewAllUsersBookedCars() {
        showUsers();
        System.out.println();
        var userId = askForUserId();
        if(userService.getUserById(userId) != null) {
            var user = userService.getUserById(userId);
            var bookingsByUser = bookingService.getBookingsByUser(user);
            if(!Arrays.stream(bookingsByUser).allMatch(Objects::isNull)) {
                showBookings(bookingsByUser);
            } else {
                noCarsBooked();
            }
        } else {
            noMatchingViewBookings();
        }
    }

    private static void viewAllBookings() {
        var bookings = bookingService.getBookings();
        if(!Arrays.stream(bookings).allMatch(Objects::isNull)) {
            noBookingsAvailable();
        } else {
            showBookings(bookings);
        }
        System.out.println();
        showMenu();
    }

    private static void viewAvailableCars() {
        showAvailableCars();
        System.out.println();
        showMenu();
    }

    private static void viewAvailableElectricCars() {
        showAvailableElectricCars();
        System.out.println();
        showMenu();
    }

    private static void viewAllUsers() {
        showUsers();
        System.out.println();
        showMenu();
    }

    static void showMenu() {
        System.out.println (
                "\uD83E\uDD35 \n" +
                        "1️⃣ Book Car\n" +
                        "2️⃣ View All Users Booked Cars\n" +
                        "3️⃣ View All Bookings\n" +
                        "4️⃣ View Available Cars\n" +
                        "5️⃣ View Available Electric Cars\n" +
                        "6️⃣ View All Users\n" +
                        "7️⃣ Exit\n"
        );
        var option = scan.nextLine();
        switch (option) {
            case "1":
                bookCar();
                break;
            case "2":
                viewAllUsersBookedCars();
                break;
            case "3":
                viewAllBookings();
                break;
            case "4":
                viewAvailableCars();
                break;
            case "5":
                viewAvailableElectricCars();
                break;
            case "6":
                viewAllUsers();
                break;
            case "7":
                exit();
                break;
            default:
                notValidOption();
        }
    }

    public static void main(String[] args) {
        showMenu();
        }
    }
