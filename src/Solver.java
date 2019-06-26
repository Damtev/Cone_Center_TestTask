import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Solver {

    private final int SQUARES_NUMBER = 12;
    private final int LIMIT = 10;
    private final long PERMUTATIONS_NUMBER;

    private String input;
    private Square[] squares;

    public Solver(String input) {
        this.input = input;
        squares = new Square[SQUARES_NUMBER];
        long factorial = 1;
        for (long i = 2; i <= SQUARES_NUMBER; i++) {
            factorial *= i;
        }
        PERMUTATIONS_NUMBER = factorial;
    }


    //    1  2
    // 3  4  5  6
    // 7  8  9  10
    //    11 12
    public void solve() {
        Reader reader = new Reader();
        PermutationsGenerator permutationsGenerator = new PermutationsGenerator();
        try {
            reader.read();
            if (check()) {
                printAnswer();
                System.out.println();
            }

            for (long i = 1; i < PERMUTATIONS_NUMBER; i++) {
                permutationsGenerator.genNextPermutation();
                if (check()) {
                    printAnswer();
                    System.out.println();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Wrong input file");
        } catch (IOException e) {
            System.out.println("Can't read input");
        } catch (NullPointerException e) {
            System.out.println("Unexpected end of input file");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void printAnswer() {
        for (Square square : squares) {
            System.out.println(square);
        }
    }

    private boolean check() {
        return checkTwoCorners() && checkThreeCorners() && checkFourCorners();
    }

    private boolean checkFour(int first, int second, int third, int fourth) {
        --first;
        --second;
        --third;
        --fourth;
        return squares[first].getRightBottom() +
                squares[second].getLeftBottom() +
                squares[third].getRightTop() +
                squares[fourth].getLeftTop() == LIMIT;
    }

    private boolean checkFourCorners() {
        return checkFour(1, 2, 4, 5) &&
                checkFour(3, 4, 7, 8) &&
                checkFour(4, 5, 8, 9) &&
                checkFour(5, 6, 9, 10) &&
                checkFour(8, 9, 11, 12);
    }

    private boolean checkThreeCorners() {
        return
                check_1_3_4() &&
                        check_2_5_6() &&
                        check_7_8_11() &&
                        check_9_10_12();
    }

    private boolean check_1_3_4() {
        return squares[0].getLeftBottom() +
                squares[2].getRightTop() +
                squares[3].getLeftTop() <= LIMIT;
    }

    private boolean check_2_5_6() {
        return squares[1].getRightBottom() +
                squares[4].getRightTop() +
                squares[5].getLeftTop() <= LIMIT;
    }

    private boolean check_7_8_11() {
        return squares[6].getRightBottom() +
                squares[7].getLeftBottom() +
                squares[10].getLeftTop() <= LIMIT;
    }

    private boolean check_9_10_12() {
        return squares[8].getRightBottom() +
                squares[9].getLeftBottom() +
                squares[11].getRightTop() <= LIMIT;
    }

    private boolean checkTwoCorners() {
        return
                check_1_2() &&
                        check_3_7() &&
                        check_6_10() &&
                        check_11_12();
    }

    private boolean check_1_2() {
        return squares[0].getRightTop() + squares[1].getLeftTop() <= LIMIT;
    }

    private boolean check_3_7() {
        return squares[2].getLeftBottom() + squares[6].getLeftTop() <= LIMIT;

    }

    private boolean check_6_10() {
        return squares[5].getRightBottom() + squares[9].getRightTop() <= LIMIT;
    }

    private boolean check_11_12() {
        return squares[10].getRightBottom() + squares[11].getLeftBottom() <= LIMIT;
    }

    private class PermutationsGenerator {

        private void swap(int i, int j) {
            Square temp = squares[i];
            squares[i] = squares[j];
            squares[j] = temp;
        }

        private void genNextPermutation() {
            int n = SQUARES_NUMBER;
            for (int i = n - 2; i >= 0; i--) {
                if (squares[i].getId() < squares[i + 1].getId()) {
                    int min = i + 1;
                    for (int j = i + 1; j < n; j++) {
                        if (squares[j].getId() < squares[min].getId() && squares[j].getId() > squares[i].getId()) {
                            min = j;
                        }
                    }

                    swap(i, min);
                    int l = i + 1;
                    int r = n - 1;
                    while (l < r) {
                        swap(l++, r--);
                    }
                    return;
                }
            }
        }
    }

    private class Reader {

        private final int MIN_VALUE = 0;
        private final int MAX_VALUE = 9;

        private void read() throws FileNotFoundException, IOException, IllegalArgumentException, NullPointerException {
            try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
                for (int i = 0; i < SQUARES_NUMBER; i++) {
                    String line = reader.readLine();
                    squares[i] = new Square(i + 1, validateInput(line));
                }
            }
        }

        private int[] validateInput(String line) throws IllegalArgumentException {
            int[] corners = new int[Square.CORNERS];
            String[] cornerValues = line.split(" ");
            if (cornerValues.length < 4) {
                throw new IllegalArgumentException("Wrong corners number at line " + line);
            }

            for (int i = 0; i < Square.CORNERS; i++) {
                corners[i] = Integer.parseInt(cornerValues[i]);
                if (corners[i] < MIN_VALUE || corners[i] > MAX_VALUE) {
                    throw new IllegalArgumentException("Wrong corner values at line " + line);
                }
            }

            return corners;
        }
    }

}
