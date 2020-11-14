package com.taxi.taxicontent.controller;

import com.taxi.taxicontent.dao.taxi.DriverRepository;
import com.taxi.taxicontent.dao.trucks.TruckDriverRepository;
import com.taxi.taxicontent.dao.trucks.TruckOrderRepository;
import com.taxi.taxicontent.dao.trucks.TruckRepository;
import com.taxi.taxicontent.model.trucks.OrderCostsStatistics;
import com.taxi.taxicontent.model.trucks.OrderCountStatistics;
import com.taxi.taxicontent.model.trucks.OrderLoadStatistics;
import com.taxi.taxicontent.model.trucks.OrderStatusStatistics;
import com.taxi.taxicontent.model.trucks.TruckOrder;
import com.taxi.taxicontent.type.TruckOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

@Controller
public class AdminController {

    private static final List<String> DEFAULT_HOURS = Arrays.asList(
            "06:00 - 07:00",
            "07:00 - 08:00",
            "08:00 - 09:00",
            "09:00 - 10:00",
            "10:00 - 11:00",
            "11:00 - 12:00",
            "12:00 - 13:00",
            "12:00 - 13:00",
            "13:00 - 14:00",
            "14:00 - 15:00",
            "15:00 - 16:00",
            "16:00 - 17:00",
            "18:00 - 19:00",
            "19:00 - 20:00",
            "20:00 - 21:00",
            "21:00 - 22:00"
            );
    private static final int STATISTICS_DAYS_PERIOD = 15;
    private static final int DEFAULT_WORKING_HOURS = 480;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private TruckDriverRepository truckDriverRepository;

    @Autowired
    private TruckOrderRepository truckOrderRepository;

    @Autowired
    private TruckRepository truckRepository;

    @GetMapping("/admin_drivers")
    public String loginAdmin(Model model) {
        model.addAttribute("drivers", driverRepository.findAll());
        return "taxi/admin_drivers";
    }

    @GetMapping("/admin_truck_statistics")
    public String getStatistic(Model model) {
        model.addAttribute("countTrucks", truckRepository.count());
        model.addAttribute("countOrders", truckOrderRepository.count());
        model.addAttribute("countTruckDrivers", truckDriverRepository.count());
        model.addAttribute("listCostsStatistics", getOrderCostsStatistics());
        return "trucks/admin_truck_statistics";
    }

    @GetMapping("/admin_truck_drivers")
    public String getTruckOrders(Model model) {
        model.addAttribute("truckDrivers", truckDriverRepository.findAll());
        model.addAttribute("trucks", truckRepository.findByTruckDriverIsNull());
        return "trucks/admin_truck_drivers";
    }

    @GetMapping("/admin_truck_orders")
    public String getTruckDrivers(Model model) {
        model.addAttribute("truckOrders", truckOrderRepository.findAll());
        model.addAttribute("trucks", truckRepository.findByTruckDriverIsNotNull());
        return "trucks/admin_truck_orders";
    }

    @GetMapping("/admin_trucks")
    public String getTrucks(Model model) {
        model.addAttribute("trucks", truckRepository.findAll());
        model.addAttribute("truckDrivers", truckDriverRepository.findAll());
        return "trucks/admin_trucks";
    }

    @GetMapping("/getStatisticsForMonth")
    public @ResponseBody
    List<OrderCountStatistics> getStatisticsForMonth() {
        List<OrderCountStatistics> orderCountStatistics = truckOrderRepository.collectOrderCountStatisticsForLastMonth();
        for (int i = 0; i <= 15; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            if (orderCountStatistics.stream().noneMatch(orderStatistics -> orderStatistics.getCreateDate().equals(date))) {
                orderCountStatistics.add(0, new OrderCountStatistics(date, 0));
            }
        }
        Collections.sort(orderCountStatistics);
        return orderCountStatistics;
    }

    @GetMapping("/getStatisticsForHoursPercent")
    public @ResponseBody
    Map<String, Integer> getStatisticsForHoursPercent() {
        Map<String, Integer> hoursPercentageMap = new TreeMap<>();
        DEFAULT_HOURS.forEach(defaultHour -> hoursPercentageMap.put(defaultHour, 0));
        List<TruckOrder> orderCountStatistics = truckOrderRepository.findAllByCreateTimeBetween(LocalDateTime.now().minusDays(STATISTICS_DAYS_PERIOD), LocalDateTime.now());
        orderCountStatistics.forEach(order -> {
            int orderHour = order.getCreateTime().getHour();
            String hours = orderHour + ":00 - " + (orderHour + 1) + ":00";
            hoursPercentageMap.put(hours, hoursPercentageMap.getOrDefault(hours,0) + 1);
        });
        hoursPercentageMap.entrySet().forEach(entry -> entry.setValue((int) Math.round((double) entry.getValue() * 100 / orderCountStatistics.size())));

        return hoursPercentageMap;
    }

    @GetMapping("/getStatisticsForDaysPercent")
    public @ResponseBody
    List<OrderLoadStatistics> getStatisticsForDaysPercent() {
        //need to create additional list to exclude old entries
        List<OrderLoadStatistics> resultLoadStatistics = new ArrayList<>();
        List<OrderLoadStatistics> orderLoadStatistics = truckOrderRepository.collectLoadStatisticsForLastMonth();
        orderLoadStatistics.forEach(statistics -> statistics.setLoadInMinutes((int) Math.round((double)statistics.getLoadInMinutes() * 100 / DEFAULT_WORKING_HOURS)));
        for (int i = 0; i <= 15; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            resultLoadStatistics.add(new OrderLoadStatistics(date, 0));
        }
        for (OrderLoadStatistics orderLoadStatistic : orderLoadStatistics) {
            resultLoadStatistics.stream()
                    .filter(orderStatistics -> orderStatistics.getCreateDate().equals(orderLoadStatistic.getCreateDate()))
                    .forEach(resultOrderStatistic -> resultOrderStatistic.setLoadInMinutes(orderLoadStatistic.getLoadInMinutes()));
        }
        Collections.sort(resultLoadStatistics);
        return resultLoadStatistics;
    }

    @GetMapping("/getStatusStatisticsPercent")
    public @ResponseBody
    List<OrderStatusStatistics> getStatusStatisticsPercent() {
        return truckOrderRepository.collectTruckOrderStatusStatistics(Arrays.asList(TruckOrderStatus.REJECTED, TruckOrderStatus.COMPLETED));
    }

    @GetMapping("/getStatisticsIntensity")
    public @ResponseBody
    float getStatisticsIntensity() {
        return getIntensity();
    }

    @GetMapping("/getStatisticsMinResources")
    public @ResponseBody
    double getStatisticsMinResources() {
        float intensity = getIntensity();
        float avgServiceTimeHours = (float) truckOrderRepository.countAverageServiceTime() / 60;
        return Math.ceil(intensity * avgServiceTimeHours);
    }

    private List<OrderCostsStatistics> getOrderCostsStatistics() {
        float intesivity = getIntensity();
        float avgServiceTime = (float) truckOrderRepository.countAverageServiceTime() / 60; //here we get hours

        float minResources = intesivity * avgServiceTime;
        int minResourcesCeiled = (int) Math.ceil(minResources);

        List<OrderCostsStatistics> orderCostsStatistics = new ArrayList<>();
        for (int i = minResourcesCeiled; i < minResourcesCeiled + 6; i++) {
            BigDecimal possibilityOfEmptyQueue = getPossibilityOfEmptyQueue(minResources, i);
            BigDecimal countOfClientsInQueue = getCountOfClientsInQueue(minResources, i);
            BigDecimal costs = getCosts(minResources, i);
            orderCostsStatistics.add(new OrderCostsStatistics(i, possibilityOfEmptyQueue, countOfClientsInQueue, costs));
        }

        return orderCostsStatistics;
    }

    /**
     * TEST METHOD
     */
    @GetMapping("/getPossibilityOfEmptyQueue")
    public @ResponseBody
    BigDecimal getPossibilityOfEmptyQueue(@RequestParam float minResources,
                                    @RequestParam int channels) {
        return countPossibilityOfEmptyQueue(minResources, channels);
    }

    /**
     * TEST METHOD
     */
    @GetMapping("/getPossibilityOfQueue")
    public @ResponseBody
    BigDecimal getPossibilityOfQueue(@RequestParam float minResources,
                                         @RequestParam int channels) {
        return countPossibilityOfQueue(minResources, channels, countPossibilityOfEmptyQueue(minResources, channels).floatValue());
    }

    /**
     * TEST METHOD
     */
    @GetMapping("/getCountOfClientsInQueue")
    public @ResponseBody
    BigDecimal getCountOfClientsInQueue(@RequestParam float minResources,
                                     @RequestParam int channels) {
        return countClientsInQueue(minResources, channels, countPossibilityOfEmptyQueue(minResources, channels).floatValue());
    }

    /**
     * TEST METHOD
     */
    @GetMapping("/getTimeOfWaiting")
    public @ResponseBody
    BigDecimal getTimeOfWaiting(@RequestParam float minResources,
                                @RequestParam int channels,
                                @RequestParam float intensity) {
        return countTimeOfWaiting(minResources, channels, intensity);
    }

    private BigDecimal getCosts(float minResources, int channels) {
        float intensity = getIntensity();
        return new BigDecimal(channels / intensity + channels * countTimeOfWaiting(minResources, channels, intensity).floatValue()).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal countTimeOfWaiting(float minResources, int channels, float intensity) {
        return countClientsInQueue(minResources, channels, countPossibilityOfEmptyQueue(minResources, channels).floatValue()).divide(new BigDecimal(intensity), RoundingMode.HALF_UP);
    }

    //we need to get orders per hour
    private float getIntensity() {
        return (float) truckOrderRepository.findAllByCreateTimeBetween(LocalDateTime.now().minusDays(STATISTICS_DAYS_PERIOD), LocalDateTime.now()).size() / (STATISTICS_DAYS_PERIOD * 24); //we multiply 15 days on 24 hours
    }

    private BigDecimal countPossibilityOfEmptyQueue(float minResources, int channels) {
        float result = 1;
        for (int i = 1; i <= channels; i++) {
            result += Math.pow(minResources, i) / factorialUsingStreams(i);
        }
        result += Math.pow(minResources, channels + 1) / (factorialUsingStreams(channels) * (channels - minResources));
        BigDecimal bd = new BigDecimal(Double.toString(Math.pow(result, -1)));
        bd = bd.setScale(3, RoundingMode.HALF_UP);

        return bd;
    }

    private BigDecimal countPossibilityOfQueue(float minResources, int channels, float possibilityOfEmptyQueue) {
        double result = Math.pow(minResources, channels + 1) / (factorialUsingStreams(channels) * (channels - minResources)) * possibilityOfEmptyQueue;
        BigDecimal bd = new BigDecimal(Double.toString(result));
        bd = bd.setScale(3, RoundingMode.HALF_UP);
        return bd;
    }

    private BigDecimal countClientsInQueue(float minResources, int channels, float possibilityOfEmptyQueue) {
        double result = Math.pow(minResources, channels + 1) / (channels * factorialUsingStreams(channels) * Math.pow(1 - minResources/channels, 2)) * possibilityOfEmptyQueue;
        BigDecimal bd = new BigDecimal(Double.toString(result));
        bd = bd.setScale(3, RoundingMode.HALF_UP);
        return bd;
    }

    private int factorialUsingStreams(int n) {
        return IntStream.rangeClosed(1, n).reduce(1, (int x, int y) -> x * y);
    }
}