import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Incremental {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("src/main/resources/matrix.csv");
//           File file = new File("src/main/resources/small-matrix.csv");
        Scanner scanner = new Scanner(file);
        List<String> lines = new ArrayList<>();
        while (scanner.hasNext()) {
            lines.add(scanner.nextLine());
        }

        final int height = lines.size();
        final int width = lines.get(0).split(",").length;
        final int[][] weights = new int[height][width];
        for (int row = 0; row < height; row++) {
            final String[] tokens = lines.get(row).split(",");
            for (int column = 0; column < width; column++) {
                weights[row][column] = Integer.parseInt(tokens[column]);
            }
        }

        final int[][] cumulativeWeights = new int[height][width];
        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                cumulativeWeights[row][column] = Integer.MAX_VALUE;
            }
        }

        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                if (row == 0 && column == 0) {
                    cumulativeWeights[row][column] = weights[row][column];
                } else {
                    if (row != 0) {
                        final int candidateWeight = weights[row][column] + cumulativeWeights[row - 1][column];
                        if (candidateWeight < cumulativeWeights[row][column]) {
                            cumulativeWeights[row][column] = candidateWeight;
                        }
                    }

                    if (column != 0) {
                        final int candidateWeight = weights[row][column] + cumulativeWeights[row][column - 1];
                        if (candidateWeight < cumulativeWeights[row][column]) {
                            cumulativeWeights[row][column] = candidateWeight;
                        }
                    }
                }
            }
        }

        System.out.println(cumulativeWeights[height - 1][width - 1]);
    }
}
