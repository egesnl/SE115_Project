// Main.java — Students version
import java.io.*;
import java.util.;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January","February","March","April","May","June",
                              "July","August","September","October","November","December"};
    
static int [][][] profits = new int[MONTHS][DAYS][COMMS];
static boolean dataLoaded = false;

    // ======== REQUIRED METHOD LOAD DATA (Students fill this) ========
    public static void loadData() {
        for (int i = 0; i < MONTHS; i++) {
            for (int j = 0; j < DAYS; j++) {
                for (int k = 0; k < COMMS; k++) {
                    profits[i][j][k] =0;
                }
            }
        }
    }
    for(int i=0; i<MONTHS; m++){
        String filePath= "Data_Files" + File.separator + months[i] + ".txt";
        Scanner sc= null;
        try {
            sc = new Scanner(new File(filePath));
            if (sc.hasNextLine()) {
                sc.NextLine();
            }
            while (sc.hasNextLine()) {
                String line=sc.nextLine().trim();
                if (line.lenght()==0)continue;
                int [] parsed=parseLineForLoadData(line);
                if(parsed==null)continue;
                int day = parsed[0];
                int cIndex = parsed[1];
                int profitVal = parsed[2];
                profits[m][day-1][cIndex]=profitVal;
            }
            catch(Exception ex){}
            finally{
                if (sc!=null) {
                    try {
                        sc.close();
                    } catch (Exception ex) {
                    }
                }
            }
        }
    }
    dataLoaded = true;
    }

    // ======== 10 REQUIRED METHODS (Students fill these) ========

    public static String mostProfitableCommodityInMonth(int month) {
        return "DUMMY"; 
    }

    public static int totalProfitOnDay(int month, int day) {
        return 1234;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
        return 1234;
    }

    public static int bestDayOfMonth(int month) { 
        return 1234; 
    }
    
    public static String bestMonthForCommodity(String comm) { 
        return "DUMMY"; 
    }

    public static int consecutiveLossDays(String comm) { 
        return 1234; 
    }
    
    public static int daysAboveThreshold(String comm, int threshold) { 
        return 1234; 
    }

    public static int biggestDailySwing(int month) { 
        return 1234; 
    }
    
    public static String compareTwoCommodities(String c1, String c2) { 
        return "DUMMY is better by 1234"; 
    }
    
    public static String bestWeekOfMonth(int month) { 
        return "DUMMY"; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}