import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    /*
    блок настроек игры
     */
    private static char[][] map; //матрица игры
    private static final int  Size = 3; //размер поля

    private static final char DOT_EMPTY = '*'; // пустой символ
    private static final char DOT_X = 'X'; //символ крестик
    private static final char DOT_O = 'O'; // символ нолик
    private static final boolean SILLY_MODE = false;
    private static  final boolean SCORING_MODE = true;

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();


    public static void main(String[] args) {
        initMap();
        printMap();

        while (true){
            humanTurn();
            if(isEndGame(DOT_X)){
                break;
            }
            computerTurn();
            if(isEndGame(DOT_O)){
                break;
            }
        }
        System.out.println("Конец игры");

    }

    //  Метод подготовтки игрового поля
    private static void initMap() {
        map = new char[Size][Size];
        for (int i = 0; i < Size; i++) {
            for (int j = 0; j < Size; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    // метод вывода игрового поля на экран
    public static void printMap() {
        //сначала циклом выводим простую линию от 0 до3 - которая будет определять номера столбцов
        for (int i = 0; i <= Size; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = 0; i < Size; i++) {
            // линия с номерами рядов
            System.out.print((i + 1) + " ");
            for (int j = 0; j < Size; j++) {
               System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // Ход человека
    private static void humanTurn() {
        int x, y;

        do {
            System.out.print("Введите координаты через пробел");
            y = scanner.nextInt() - 1; // номер строки
            x = scanner.nextInt() - 1; // номер столбца
        } while (!isCellValid(x, y)); //до тех пор пока вводимые значения некорректны, будем запрашивать координаты еще раз

        map[y][x] = DOT_X;
    }

    // ход компьютера

     private static void computerTurn() {
         int x = -1;
         int y = -1;

         if (SILLY_MODE) {
             do {
                 x = random.nextInt(Size);
                 y = random.nextInt(Size);
             } while (!isCellValid(x, y));
         }
         // комп ищет свой символ
         // комп решает можно ли продолжать последовательность для выигрыша
      else {
             //вводим маркер что ход найден
             if (!SCORING_MODE) {
                 boolean moveFound = false;
                 //упрощенный алгоритм
                 for (int i = 0; i < Size; i++) {
                     for (int j = 0; j < Size; j++) {
                         //проверяем клетки по направленяим
                         if (map[i][j] == DOT_EMPTY) {
                             if ((j - 1) >= 0 && map[i][j - 1] == DOT_O) {
                                 y = i;
                                 x = j;
                                 moveFound = true;
                                 System.out.println("Left");
                             } else if ((i - 1) >= 0 && (j - 1) >= 0 && map[i - 1][j - 1] == DOT_O) {
                                 y = i;
                                 x = j;
                                 moveFound = true;
                                 System.out.println("Left-Up");
                             } else if ((i - 1) >= 0 && map[i - 1][j] == DOT_O) {
                                 y = i;
                                 x = j;
                                 moveFound = true;
                                 System.out.println("Up");
                             } else if ((i - 1) >= 0 && (j + 1) < Size && map[i - 1][j + 1] == DOT_O) {
                                 y = i;
                                 x = j;
                                 moveFound = true;
                                 System.out.println("Right-Up");
                             } else if ((j + 1) < Size && map[i][j + 1] == DOT_O) {
                                 y = i;
                                 x = j;
                                 moveFound = true;
                                 System.out.println("Right");
                             } else if ((i + 1) < Size && (j + 1) < Size && map[i + 1][j + 1] == DOT_O) {
                                 y = i;
                                 x = j;
                                 moveFound = true;
                                 System.out.println("Right-down");
                             } else if ((i + 1) < Size && map[i + 1][j] == DOT_O) {
                                 y = i;
                                 x = j;
                                 moveFound = true;
                                 System.out.println("Down");
                             } else if ((i + 1) < Size && (j - 1) >= 0 && map[i + 1][j - 1] == DOT_O) {
                                 y = i;
                                 x = j;
                                 moveFound = true;
                                 System.out.println("Left-down");
                             }
                         }
                         //  если ход найден прерываем внутренний цикл
                         if (moveFound) {
                             break;
                         }
                     }
                     // если ход найден прерываем внешний цикл
                     if (moveFound) {
                         break;
                     }
                 }
             }
                     //алгоритм с подсчетом очков
                     else {
                         int maxScoreFieldX = -1;
                         int maxScoreFieldY = -1;
                         int maxscore = 0;

                         for(int i = 0; i < Size; i ++){
                             for(int j = 0; j< Size; j ++){
                                 int fieldScore = 0;
                                 if( map[i][j] == DOT_EMPTY){
                                     if ((j - 1) >= 0 && map[i][j - 1] == DOT_O) {
                                         // каждая возможность будет подсчитывать очки и прибавлять
                                         fieldScore++;
                                         System.out.println("Left");
                                     }
                                     if ((i - 1) >= 0 && (j - 1) >= 0 && map[i - 1][j - 1] == DOT_O) {
                                         fieldScore++;
                                         System.out.println("Left-Up");
                                     }
                                     if ((i - 1) >= 0 && map[i - 1][j] == DOT_O) {
                                         fieldScore++;
                                         System.out.println("Up");
                                     }
                                     if ((i - 1) >= 0 && (j + 1) < Size && map[i - 1][j + 1] == DOT_O) {
                                         fieldScore++;
                                         System.out.println("Right-Up");
                                     }
                                     if ((j + 1) < Size && map[i][j + 1] == DOT_O) {
                                         fieldScore++;
                                         System.out.println("Right");
                                     }
                                     if ((i + 1) < Size && (j + 1) < Size && map[i + 1][j + 1] == DOT_O) {
                                         fieldScore++;
                                         System.out.println("Right-down");
                                     }
                                     if ((i + 1) < Size && map[i + 1][j] == DOT_O) {
                                         fieldScore++;
                                         System.out.println("Down");
                                     }
                                     if ((i + 1) < Size && (j - 1) >= 0 && map[i + 1][j - 1] == DOT_O) {
                                         fieldScore++;
                                         System.out.println("Left-down");
                                     }

                                     if(fieldScore> maxscore){
                                         maxscore = fieldScore;
                                         maxScoreFieldX = i;
                                         maxScoreFieldY = j;
                                     }
                                 }
                             }
                             // если в поле найдена наилучшая клетка
                             if(maxScoreFieldX != -1) {
                                 x = maxScoreFieldX;
                                 y = maxScoreFieldY;
                             }
                         }
                     }
                     //  если ничего не найдено, комп делает глупый ход
                     if (x == -1) {
                         do {
                             x = random.nextInt(Size);
                             y = random.nextInt(Size);
                         } while (!isCellValid(x, y));
                         System.out.println("Random");
                     }

                     System.out.println("Компьютер выбрал координаты " + (y + 1) + " " + (x + 1));
                     map[y][x] = DOT_O;
                 }
             }

// метод валидации запрашиваемой ячейки на корректность
         // х - координата по горизонтале
         // у = координата по вертикали
         //boolean - признак валидности
         private static boolean isCellValid ( int x, int y){
             boolean result = true;

             //   проверка координаты
             if (x < 0 || x >= Size || y < 0 || y >= Size) {
                 result = false;
             }
             //проверка заполненности ячейки
             if (map[y][x] != DOT_EMPTY) {
                 result = false;
             }
             return result;
         }
// метод проверки игры на завершение
         //playerSymbol - символ, которым играет текущий игрок
         private static boolean isEndGame ( char playerSymbol){
             boolean result = false; //по умолчанию игра не заканчивается

             printMap();

             //проверяем необходимость следующего хода
             if (checkWin(playerSymbol)) {
                 System.out.println("Победил игорок " + playerSymbol);
                 result = true;
             }

             if (isMapFull()) {
                 System.out.print("Ничья");
                 result = true;
             }
             return result;
         }
         //проверка на 100%  заполненности поля
         private static boolean isMapFull () {
             boolean result = true;

             for (int i = 0; i < Size; i++) {
                 for (int j = 0; j < Size; j++) {
                     if (map[i][j] == DOT_EMPTY) {
                         result = false;
                     }
                 }
             }
             return result;
         }
         //метод проверки победителя

         private static boolean checkWin ( char playerSymbol){
             boolean result = false;
             if (
                     (map[0][0] == playerSymbol && map[0][1] == playerSymbol && map[0][2] == playerSymbol) ||
                             (map[1][0] == playerSymbol && map[1][1] == playerSymbol && map[1][2] == playerSymbol) ||
                             (map[2][0] == playerSymbol && map[2][1] == playerSymbol && map[2][2] == playerSymbol) ||
                             (map[0][0] == playerSymbol && map[1][0] == playerSymbol && map[2][0] == playerSymbol) ||
                             (map[0][1] == playerSymbol && map[1][1] == playerSymbol && map[2][1] == playerSymbol) ||
                             (map[0][2] == playerSymbol && map[1][2] == playerSymbol && map[2][2] == playerSymbol) ||
                             (map[0][0] == playerSymbol && map[1][1] == playerSymbol && map[2][2] == playerSymbol) ||
                             (map[0][2] == playerSymbol && map[1][2] == playerSymbol && map[2][0] == playerSymbol)) {
                 result = true;
             }
             return result;
         }


}
