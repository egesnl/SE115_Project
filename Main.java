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
    if (month<0 || month>= MONTHS ) return "Invalid month";
    int bestCommodity = 0;
    int bestSum=0;

    for (int i = 0; i < DAYS; i++) {
        bestSum += profits[month][i][0];
    }
    for (int j = 0; j<COMMS; j++){
        int sum = 0;
        for (int i = 0; i < DAYS; i++) {
            bestSum += profits[month][i][j];
        }
        if (sum > bestSum){
            bestSum = sum;
            bestCommodity = j;
        }
    }
        return commodities[bestCommodity] + " " + bestSum;
    }

    public static int totalProfitOnDay(int month, int day) {
     if (month <0 || month >= MONTHS) return -99999;
     if (day < 1 || day > DAYS)  return -99999;
     int dIndex = day - 1;
     int sum = 0;
     for (int i = 0; i < COMMS; i++){
         sum += profits [month][dIndex][i];
     }
    return sum;
    }

    public static int commodityProfitInRange(String commodity, int from, int to) {
    if (commodity == null) return -99999;
    int cIndex = -1;
    for (int i=0; i< COMMS; i++ ){
        if (commodities[i].equals (commodity)){
            cIndex = i;
            break;
        }
    }
    if (cIndex == -1) return -99999;
    if (from < 1 || from > DAYS || to < 1 || to> DAYS) return -99999;
    if (from > to) return -99999;
    int sum = 0;
    for (int i = 0; i < MONTHS; i++){
        for (int j=from; j<=to - 1; j++){
            sum += profits[i][j][cIndex];
        }
    }
        return sum;
    }

    public static int bestDayOfMonth(int month) {
    if (month < 0 || month >= MONTHS) return -1;
    int bestDay = -1;
    int bestTotal = totalProfitOnDay(month, 1);
    for (int day=2; day<= DAYS; day++){
        int t= totalProfitOnDay(month, day);
        if (t > bestTotal){
            bestTotal = t;
            bestDay = day;
        }
    }
        return bestDay;
    }
    
    public static String bestMonthForCommodity(String comm) {
    if (comm == null) return "Invalid commodity";
    int cIndex = -1;
    for (int i= 0; i<COMMS; i++){
        if (commodities[i].equals(comm)){
            cIndex = i;
            break;
        }
    }
    if (cIndex == -1) return "Invalid commodity";
    int bestMonth = 0;
    int bestSum = 0;
    for (int i=0; i<DAYS; i++){
    bestSum += profits[0][i][cIndex];
    }
    for (int j=1; j<MONTHS ; j++){
        int sum = 0;
        for (int i=0; i<DAYS; i++){
            sum += profits[j][i][cIndex];
        }
        if (sum > bestSum){
            bestSum = sum;
            bestMonth = j;
        }
    }

        return months[bestMonth];
    }

    public static int consecutiveLossDays(String comm) {
    if (comm == null) return -1;
    int cIndex = -1;
    for (int i=0; i<COMMS; i++){
        if (commodities[i].equals(comm)){
            cIndex = i;
            break;
        }
    }
    if (cIndex == -1) return -1;
    int maxStreak = 0;
    int cur = 0;
    for (int i = 0; i<MONTHS; i++){
        for (int j = 0; j<DAYS; j++){
            if (profits[i][j][cIndex] < 0){
                cur++;
                if (cur > maxStreak) maxStreak = cur;
            } else {
                cur = 0;
            }
        }
    }
        return maxStreak;
    }
    
    public static int daysAboveThreshold(String comm, int threshold) {
    if (comm == null) return -1;
    int cIndex = -1;
    for (int i=0; i<COMMS; i++){
        if (commodities[i].equals(comm)){
            cIndex = i;
            break;
        }
    }
    if (cIndex == -1) return -1;
    int count = 0;
    for (int i = 0; i<MONTHS; i++){
        for (int j = 0; j<DAYS; j++){
            if (profits[i][j][cIndex] > threshold) count++;
        }
    }
        return count;
    }

    public static int biggestDailySwing(int month) {
    if (month<0 || month>=MONTHS) return -99999;
    int maxSwing = 0;
    int previous =totalProfitOnDay(month, 1);
    for (int day = 2; day<=DAYS; day++){
        int current = totalProfitOnDay(month, day);
        int diff = current-previous;
        if (diff < 0) diff = -diff;
        if (diff > maxSwing) maxSwing = diff;
        previous = current;
    }
        return maxSwing;
    }
    
    public static String compareTwoCommodities(String c1, String c2) {
    if (c1 == null || c2 == null) return "Invalid commodity";
    int i1= -1;
    int i2= -1;
    for (int i=0; i<COMMS; i++){
        if (commodities[i].equals(c1)) i1 = i;
        if (commodities[i].equals(c2)) i2 = i;
    }
    if (i1 == -1 || i2 == -1) return "Invalid commodity";
    int s1=0;
    int s2=0;
    for (int i=0;i<MONTHS; i++){
     for (int j=0; j<DAYS;j++){
         s1 += profits[i][j][i1];
         s2 += profits[i][j][i2];
     }
}
    if (s1 == s2) return "Equal";

    int diff = s1 - s2;
    if (diff < 0) diff = -diff;

    if (s1>s2) return c1 + "is better by" + diff;
        return c2 + "is better by" + diff; 
    }
    
    public static String bestWeekOfMonth(int month) {
    if (month<0 || month >= MONTHS) return "Invalid month";
    int bestWeek = 0;
    int bestSum = 0;

    for (int i=0; i<=6;i++){
        for (int j=0; j<=COMMS;j++){
            bestSum += profits[month][i][j]
        }
    }
    for (int i=2; i<=4; i++){
        int start=(i-1) * 7;
        int end= start+6;
        int sum = 0;
        for (int ii=start; ii<=end; ii++){
            for (int j=0; j<=COMMS; j++){
                sum += profits[month][ii][j]
            }
        }
        if (sum > bestSum){
            bestSum = sum;
            bestWeek = i;
        }
    }

        return "Week" + bestWeek; 
    }

    public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}