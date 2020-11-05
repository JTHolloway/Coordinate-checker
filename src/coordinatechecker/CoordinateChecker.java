package coordinatechecker;

import java.util.Scanner;

public class CoordinateChecker {

    public static void main(String[] args) {
        System.out.println("Letter-Number Format. Eg - F7\n*****Type 'done' When Done*****");
        String[][] Grid = new String[11][11];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                Grid[i][j] = "   ";
                Grid[0][j] = " " + j + " ";
            }
            char Uni = Unicode(i);
            Grid[i][0] = " " + Uni + " ";
        }
        Grid[0][0] = "   ";

        Print(Grid);

        do {
            Scanner s = new Scanner(System.in);
            System.out.println("Enter a Guess:");
            String Guess = s.next();
            try {
                if (Guess.equalsIgnoreCase("done")) {
                    Print(Grid);
                    break;
                } else {
                    int len = Guess.length();
                    Grid[Number(Character.toUpperCase(Guess.charAt(0)))][Integer.parseInt(Guess.substring(1, len))] = " X ";
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("******ERROR: This Coordinate does not exist - Try Again******");
            } catch (NumberFormatException e) {
                System.out.println("******ERROR: Wrong Format - Try Again******");
            }

        } while (true);
    }

    public static char Unicode(int number) {
        char Unicode = 'A' - 1;
        char Letter = (char) (Unicode + number);
        return Letter;
    }

    public static int Number(char Letter) {
        char Unicode = 'A' - 1;
        int Number = (int) (Letter - Unicode);
        return Number;
    }

    public static void Print(String[][] Grid) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                System.out.print(Grid[i][j]);
            }
            System.out.println("");
        }
    }
}
