// Main.java — Students version

import java.nio.file.*;
import java.io.*;
import java.util.*;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};


    static int[][][] profitdata = new int[MONTHS][DAYS][COMMS];

    public static void loadData() {
        Scanner sc = null;
        for (int i = 0; i < MONTHS; i++) {

            try {
                sc = new Scanner(Paths.get("Data_Files/" + months[i] + ".txt"));
                sc.nextLine();
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] parts = line.split(",");
                    int day = Integer.parseInt(parts[0]) - 1;
                    String commodity = parts[1];
                    int profit = Integer.parseInt(parts[2]);
                    int commind = -1;
                    for (int b = 0; b < COMMS; b++) {
                        if (commodities[b].equals(commodity)) {
                            commind = b;
                            break;
                        }
                    }
                    if (commind != -1) {
                        profitdata[i][day][commind] = profit;
                    }
                }
                sc.close();
            } catch (IOException e) {
                System.out.println("File not here: " + months[i] + ".txt");

            }
        }
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        if (month < 0 || month > 11) {
            return "Invalid_month";
        }
        int bestCommodityIndex = 0;
        int maxProfit = 0;
        for (int i = 0; i < 28; i++) {
            maxProfit += profitdata[month][i][0];
        }
        for (int i = 1; i < COMMS; i++) {
            int sum = 0;
            for (int a = 0; a < 28; a++) {
                sum += profitdata[month][a][i];
            }
            if (maxProfit < sum) {
                sum = maxProfit;
                bestCommodityIndex = i;
            }
        }
        return commodities[bestCommodityIndex] + " " + maxProfit;
    }

    public static int totalProfitOnDay(int month, int day) {
        if (month < 0 || month > 11 || day < 1 || day > 28) {
            return -9999;
        }
        int total = 0;
        for (int i = 0; i < commodities.length; i++) {
            total += profitdata[month][day - 1][i];

        }
        return total;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        if (from < 0 || to > 28 || from > to) {
            return -99999;
        }
        int commoditynum;
        switch (commodity) {
            case "Gold":
                commoditynum = 0;
                break;
            case "Oil":
                commoditynum = 1;
                break;
            case "Silver":
                commoditynum = 2;
                break;
            case "Wheat":
                commoditynum = 3;
                break;
            case "Copper":
                commoditynum = 4;
                break;
            default:
                return -99999;
        }
        int total = 0;
        for (int m = 0; m < 12; m++) {
            for (int i = from - 1; i <= to - 1; i++) {
                total += profitdata[m][i][commoditynum];
            }

        }
        return total;
    }

    public static int bestDayOfMonth(int month) {

        if (month < 0 || month > 11) {
            return -1;
        }
        int bestDay = 1;
        int bestProfit = 0;

        for (int a = 0; a < COMMS; a++) {
            bestProfit += profitdata[month][0][a];
        }
        for (int i = 1; i < DAYS; i++) {

            int sum = 0;

            for (int c = 0; c < COMMS; c++) {
                sum += profitdata[month][i][c];
            }

            if (sum > bestProfit) {
                bestProfit = sum;
                bestDay = i + 1;
            }
        }

        return bestDay;
    }


    public static String bestMonthForCommodity(String comm) {
        int a = -1;

        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                a = i;
                break;
            }
        }

        if (a == -1) {
            return "INVALID_COMMODITY";
        }

        int best = 0;
        int max = 0;

        for (int i = 0; i < DAYS; i++) {
            max += profitdata[0][i][a];
        }

        for (int m = 1; m < MONTHS; m++) {

            int total = 0;

            for (int i = 0; i < DAYS; i++) {
                total += profitdata[m][i][a];
            }

            if (total > max) {
                max = total;
                best = m;
            }
        }

        return months[best];
    }


    public static int consecutiveLossDays(String comm) {
        int a = -1;

        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                a = i;
                break;
            }
        }

        if (a == -1) {
            return -1;
        }

        int cur = 0;
        int max = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int i = 0; i < DAYS; i++) {

                if (profitdata[m][i][a] < 0) {
                    cur++;
                    if (cur > max) {
                        max = cur;
                    }
                } else {
                    cur = 0;
                }
            }
        }

        return max;


    }

    public static int daysAboveThreshold(String comm, int threshold) {
        int c = -1;


        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                c = i;
                break;
            }
        }

        if (c == -1) {
            return -1;
        }

        int total = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {

                if (profitdata[m][d][c] > threshold) {
                    total++;
                }
            }
        }

        return total;
    }


    public static int biggestDailySwing(int month) {

        if (month < 0 || month >11 ) {
            return -99999;
        }

        int max = 0;

        for (int d = 0; d < DAYS - 1; d++) {

            int today = 0;
            int tomorrow = 0;

            for (int c = 0; c < COMMS; c++) {
                today += profitdata[month][d][c];
            }

            for (int c = 0; c < COMMS; c++) {
                tomorrow += profitdata[month][d + 1][c];
            }

            int fark = today - tomorrow;
            if (fark < 0) {
                fark = -fark;
            }

            if (fark > max) {
                max = fark;
            }
        }

        return max;
    }

    public static String compareTwoCommodities(String c1, String c2) {
            int a = -1;
            int b = -1;

            for (int i = 0; i < COMMS; i++) {
                if (commodities[i].equals(c1)) {
                    a = i;
                }
                if (commodities[i].equals(c2)) {
                    b = i;
                }
            }

            if (a == -1 || b == -1) {
                return "INVALID_COMMODITY";
            }

            int t1 = 0;
            int t2 = 0;

            for (int m = 0; m < MONTHS; m++) {
                for (int d = 0; d < DAYS; d++) {
                    t1 += profitdata[m][d][a];
                    t2 += profitdata[m][d][b];
                }
            }

            if (t1 > t2) {
                return c1 + " is better by " + (t1 - t2);
            } else if (t2 > t1) {
                return c2 + " is better by " + (t2 - t1);
            } else {
                return "Equal";
            }
        }


        public static String bestWeekOfMonth(int month) {

            if (month < 0 || month >11) {
                return "INVALID_MONTH";
            }

            int total = 0;

            for (int a = 0; a < 7; a++) {
                for (int b = 0; b < COMMS; b++) {
                    total += profitdata[month][a][b];
                }
            }

            int max = total;
            int week = 1;

            for (int i = 1; i < 4; i++) {

                total = 0;

                int start = i * 7;
                int end = start + 7;

                for (int a = start; a < end; a++) {
                    for (int b = 0; b < COMMS; b++) {
                        total += profitdata[month][a][b];
                    }
                }

                if (total > max) {
                    max = total;
                    week = i + 1;
                }
            }

            return "Week " + week;
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");

    }
}



