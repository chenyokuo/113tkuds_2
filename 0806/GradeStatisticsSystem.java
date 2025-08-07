
public class GradeStatisticsSystem {

    public static void main(String[] args) {
        int[] scores = {85, 92, 78, 96, 87, 73, 89, 94, 82, 90};

        // 計算總分、平均、最高與最低
        int sum = 0, max = scores[0], min = scores[0];
        for (int score : scores) {
            sum += score;
            if (score > max) max = score;
            if (score < min) min = score;
        }
        double average = (double) sum / scores.length;

        // 等第統計
        int countA = 0, countB = 0, countC = 0, countD = 0, countF = 0;
        for (int score : scores) {
            if (score >= 90) countA++;
            else if (score >= 80) countB++;
            else if (score >= 70) countC++;
            else if (score >= 60) countD++;
            else countF++;
        }

        // 高於平均的學生數
        int aboveAverageCount = 0;
        for (int score : scores) {
            if (score > average) aboveAverageCount++;
        }

        // 列印成績報表
        System.out.println("=== 成績報表 ===");
        System.out.printf("平均成績：%.2f\n", average);
        System.out.println("最高成績：" + max);
        System.out.println("最低成績：" + min);
        System.out.println("等第分佈：");
        System.out.println("A (90~100)： " + countA + " 人");
        System.out.println("B (80~89)：  " + countB + " 人");
        System.out.println("C (70~79)：  " + countC + " 人");
        System.out.println("D (60~69)：  " + countD + " 人");
        System.out.println("F (<60)：    " + countF + " 人");
        System.out.println("高於平均成績者： " + aboveAverageCount + " 人");
    }
}

