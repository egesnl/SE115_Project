// Main.java — Student version

import java.io.*;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;

    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};


    static int[][][] profits = new int[MONTHS][DAYS][COMMS];

    // ======== REQUIRED METHOD LOAD DATA ========
    public static void loadData() {
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                for (int c = 0; c < COMMS; c++) {
                    profits[m][d][c] = 0;
                }
            }
        }

        for (int m = 0; m < MONTHS; m++) {
            try {
                File file = new File("Data_Files/" + months[m] + ".txt");
                Scanner sc = new Scanner(file);

                if (sc.hasNextLine()) sc.nextLine();

                while (sc.hasNextLine()) {
                    String line = sc.nextLine().trim();
                    if (line.length() == 0) continue;

                    String[] parts = line.split(",");
                    if (parts.length < 3) continue;

                    int day = Integer.parseInt(parts[0].trim()) - 1;
                    String commodity = parts[1].trim();
                    int profitVal = Integer.parseInt(parts[2].trim());

                    int cIndex = commodityIndex(commodity);
                    if (day >= 0 && day < DAYS && cIndex != -1) {
                        profits[m][day][cIndex] = profitVal;
                    }
                }

                sc.close();
            } catch (Exception ex) {

            }
        }
    }

   //helper
    public static int commodityIndex(String commodity) {
        if (commodity == null) return -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(commodity)) return i;
        }
        return -1;
    }

    // ======== 10 REQUIRED METHODS ========

    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month >= MONTHS) return "INVALID_MONTH";

        int bestIndex = 0;
        int bestSum = 0;

        for (int d = 0; d < DAYS; d++) bestSum += profits[month][d][0];

        for (int c = 1; c < COMMS; c++) {
            int sum = 0;
            for (int d = 0; d < DAYS; d++) sum += profits[month][d][c];
            if (sum > bestSum) {
                bestSum = sum;
                bestIndex = c;
            }
        }
        return commodities[bestIndex] + " " + bestSum;
    }

    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month >= MONTHS) return -99999;
        if (day < 1 || day > DAYS) return -99999;

        int dIndex = day - 1;
        int sum = 0;
        for (int c = 0; c < COMMS; c++) sum += profits[month][dIndex][c];
        return sum;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        int cIndex = commodityIndex(commodity);
        if (cIndex == -1) return -99999;
        if (from < 1 || from > DAYS || to < 1 || to > DAYS) return -99999;
        if (from > to) return -99999;

        int sum = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = from - 1; d <= to - 1; d++) {
                sum += profits[m][d][cIndex];
            }
        }
        return sum;
    }

    public static int bestDayOfMonth(int month) {
        if (month < 0 || month >= MONTHS) return -1;

        int bestDay = 1;
        int bestTotal = totalProfitOnDay(month, 1);

        for (int day = 2; day <= DAYS; day++) {
            int t = totalProfitOnDay(month, day);
            if (t > bestTotal) {
                bestTotal = t;
                bestDay = day;
            }
        }
        return bestDay;
    }

    public static String bestMonthForCommodity(String comm) {
        int cIndex = commodityIndex(comm);
        if (cIndex == -1) return "INVALID_COMMODITY";

        int bestMonth = 0;
        int bestSum = 0;
        for (int d = 0; d < DAYS; d++) bestSum += profits[0][d][cIndex];

        for (int m = 1; m < MONTHS; m++) {
            int sum = 0;
            for (int d = 0; d < DAYS; d++) sum += profits[m][d][cIndex];
            if (sum > bestSum) {
                bestSum = sum;
                bestMonth = m;
            }
        }
        return months[bestMonth];
    }

    public static int consecutiveLossDays(String comm) {
        int cIndex = commodityIndex(comm);
        if (cIndex == -1) return -1;

        int maxStreak = 0;
        int current = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profits[m][d][cIndex] < 0) {
                    current++;
                    if (current > maxStreak) maxStreak = current;
                } else {
                    current = 0;
                }
            }
        }
        return maxStreak;
    }

    public static int daysAboveThreshold(String comm, int threshold) {
        int cIndex = commodityIndex(comm);
        if (cIndex == -1) return -1;

        int count = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profits[m][d][cIndex] > threshold) count++;
            }
        }
        return count;
    }

    public static int biggestDailySwing(int month) {
        if (month < 0 || month >= MONTHS) return -99999;

        int maxSwing = 0;
        int prev = totalProfitOnDay(month, 1);

        for (int day = 2; day <= DAYS; day++) {
            int current = totalProfitOnDay(month, day);
            int diff = current - prev;
            if (diff < 0) diff = -diff;
            if (diff > maxSwing) maxSwing = diff;
            prev = current;
        }
        return maxSwing;
    }

    public static String compareTwoCommodities(String c1, String c2) {
        int i1 = commodityIndex(c1);
        int i2 = commodityIndex(c2);
        if (i1 == -1 || i2 == -1) return "INVALID_COMMODITY";

        int sum1 = 0;
        int sum2 = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                sum1 += profits[m][d][i1];
                sum2 += profits[m][d][i2];
            }
        }

        if (sum1 > sum2) return c1 + " is better by " + (sum1 - sum2);
        if (sum2 > sum1) return c2 + " is better by " + (sum2 - sum1);
        return "Equal";
    }

    public static String bestWeekOfMonth(int month) {
        if (month < 0 || month >= MONTHS) return "INVALID_MONTH";

        int bestWeek = 1;
        int bestSum = 0;

        for (int d = 0; d < 7; d++) {
            for (int c = 0; c < COMMS; c++) bestSum += profits[month][d][c];
        }

        for (int w = 2; w <= 4; w++) {
            int start = (w - 1) * 7;
            int end = start + 7;
            int sum = 0;

            for (int d = start; d < end; d++) {
                for (int c = 0; c < COMMS; c++) sum += profits[month][d][c];
            }

            if (sum > bestSum) {
                bestSum = sum;
                bestWeek = w;
            }
        }

        return "Week " + bestWeek;
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}
