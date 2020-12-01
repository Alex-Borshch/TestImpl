import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Cities {
    private static final int IMPOSSIBLE_PATH = 200001;
    private static final int CITY_NAME_LENGTH = 10;
    private int numberOfTest;
    private int numberOfCities;
    private int currentCityNumber;
    private int neighbCityNumber;
    private int transportCost;
    private int numberOfTestTravels;
    private int indexOfStartCity;
    private int indexOfEndCity;
    private int countNeighbors; //number of city neighbors
    private String name;

    private Map<String, Integer> cityNames = new HashMap<>();
    private String[] nrCost;
    private String[] costBtwCities;
    private int[][] matrix; // matrix of weight and history

    private BufferedReader r;

    public static void main(String[] args) {
        Cities cities = new Cities();
        try {
            cities.input();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][] initMatrix(int[][] matrix, int size) {
        matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = IMPOSSIBLE_PATH;// the matrix is ​​symmetric about the diagonal
                matrix[i][i] = 0; //the elements of the diagonal = 0
            }
        }
        return matrix;
    }

    public void floyd(int[][] matrix) {  //floyd-warshall algorithm
        for (int k = 0; k < matrix.length; k++) {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix.length; j++) {
                    if (matrix[i][k] != IMPOSSIBLE_PATH && matrix[k][j] != IMPOSSIBLE_PATH)
                        matrix[i][j] = min(matrix[i][k] + matrix[k][j], matrix[i][j]);
                }
            }
        }
    }

    private int min(int a, int b) {
        if (a > b) {
            return b;
        }
        return a;
    }

    public void input() throws IOException {
        r = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("Enter the number of test: ");
            try {
                numberOfTest = Integer.parseInt(r.readLine());
                if (numberOfTest <= 0 && numberOfTest > 10){
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Incorrect input. Enter integer 0<n<10 ");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("Enter the number of cities: ");
            try {
                numberOfCities = Integer.parseInt(r.readLine());
                if (numberOfCities <= 0 && numberOfCities > 10000){
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Incorrect input. Enter integer 0<n<=10000 ");
                continue;
            }
            break;
        }
        currentCityNumber = 0; // index if current city
        matrix = this.initMatrix(matrix, numberOfCities);


        for (int test = 0; test < numberOfTest; test++) { //repeat input for each test
            for (int city = 0; city < numberOfCities; ++city) { //repeat input for each city

                while (true) again:{ //repeat again if wrong chars in name
                    System.out.print("City name: ");
                    name = r.readLine();
                    if (name.length() <= CITY_NAME_LENGTH && name.length() > 0) {
                        for (int i = 0; i < name.length(); i++) {
                            if ((int) name.charAt(i) < 97 || (int) name.charAt(i) > 122) { //compare (a-z) codes in ASCII
                                System.out.println("City name must be only letters a-z. Try again.");
                                break again;
                            }
                        }
                    } else {
                        System.out.println("Сity name must not be longer than 10 chars. Try again.");
                        continue;
                    }

                    cityNames.put(name, city);

                    while (true) {
                        System.out.print("Number of neighbours in " + name + ": ");
                        try {
                            countNeighbors = Integer.parseInt(r.readLine());
                            if (countNeighbors >= numberOfCities) {
                                throw new NumberFormatException();
                            }
                        } catch (NumberFormatException ex) {
                            System.out.println("Incorrect number of neighbours. Try again.");
                            continue;
                        }
                        break;
                    }

                    for (int j = 0; j < countNeighbors; j++) {
                        while (true) {
                            System.out.print("Number of city and cost: ");
                            try {
                                nrCost = r.readLine().split(" ");
                                neighbCityNumber = Integer.parseInt(nrCost[0]) - 1;
                                transportCost = Integer.parseInt(nrCost[1]);
                                if (neighbCityNumber == currentCityNumber ||
                                        neighbCityNumber < 0 ||
                                        transportCost < 1) {
                                    throw new NumberFormatException();
                                }
                            } catch (Exception ex) {
                                System.out.println("Enter correct data. Try again");
                                continue;
                            }
                            matrix[currentCityNumber][neighbCityNumber] = transportCost;
                            break;
                        }
                    }
                    currentCityNumber += 1;
                    break;
                }
            }
        }
        floyd(matrix);

        while (true) {
            System.out.print("Number of test travels: ");
            try {
                numberOfTestTravels = Integer.parseInt(r.readLine());
                if (numberOfTestTravels >= 100 || numberOfTestTravels <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Enter correct Number of test travels. 0 < r <= 100");
                continue;
            }
            for (int i = 0; i < numberOfTestTravels; i++) {
                while (true) {

                    System.out.print("Input start and end cities: ");
                    try {
                        costBtwCities = r.readLine().split(" ");
                        indexOfStartCity = cityNames.get(costBtwCities[0]);
                        indexOfEndCity = cityNames.get(costBtwCities[1]);
                    } catch (Exception ex) {
                        System.out.println("Enter correct cities");
                        continue;
                    }
                    print(indexOfStartCity, indexOfEndCity);
                    break;
                }
            }
            r.close();
            break;
        }
    }

    private void print(int indexOfStartCity, int indexOfEndCity) {
        System.out.println("The path of minimum cost between " + costBtwCities[0] +
                " and " + costBtwCities[1] + " is " + matrix[indexOfStartCity][indexOfEndCity]);
    }
}


