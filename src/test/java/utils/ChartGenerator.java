package utils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.io.File;

/**
 * Утилита для генерации pie-chart (диаграммы) по результатам тестов.
 * Используется в Jenkins pipeline для отправки отчёта в Telegram.
 * <p>
 * Аргументы:
 * 0 - total
 * 1 - passed
 * 2 - failed
 * 3 - broken
 * 4 - skipped
 * 5 - output file path (например, chart.png)
 * <p>
 * Пример запуска:
 * java -cp build/classes/java/test:~/.gradle/caches/modules-2/files-2.1/* utils.ChartGenerator \
 * 50 45 2 1 2 chart.png
 */
public class ChartGenerator {

    public static void main(String[] args) throws Exception {
        if (args.length < 6) {
            System.out.println("Usage: java utils.ChartGenerator <total> <passed> <failed> <broken> <skipped> <output>");
            return;
        }

        int total = Integer.parseInt(args[0]);
        int passed = Integer.parseInt(args[1]);
        int failed = Integer.parseInt(args[2]);
        int broken = Integer.parseInt(args[3]);
        int skipped = Integer.parseInt(args[4]);
        String outputPath = args[5];

        generateChart(total, passed, failed, broken, skipped, outputPath);
    }

    public static void generateChart(int total, int passed, int failed, int broken, int skipped, String outputPath) throws Exception {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("✅ Passed " + passed, passed);
        dataset.setValue("❌ Failed " + failed, failed);
        dataset.setValue("💥 Broken " + broken, broken);
        dataset.setValue("⚠️ Skipped " + skipped, skipped);

        JFreeChart chart = ChartFactory.createPieChart(
                "WebTests: " + total + " scenarios",
                dataset,
                true,
                true,
                false
        );

        ChartUtils.saveChartAsPNG(new File(outputPath), chart, 500, 500);
        System.out.println("Chart saved to " + outputPath);
    }
}
