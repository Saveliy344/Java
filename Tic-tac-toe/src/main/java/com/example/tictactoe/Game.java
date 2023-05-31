package com.example.tictactoe;

public class Game {
    //Счётчик ходов
    int counter;
    //Очередь крестика (синего)
    boolean turn = true;
    String winner = null;
    boolean isEnded = false;
    Field field;

    public Game(int size) {
        field = new Field(size);
    }

    public void checkEnd(int row, int col) {
        //Если игра уже завершилась
        if (isEnded) return;
        //Если в данной клетке ничего не установлено
        if (field.getCell(row, col).getCode().equals(CellType.Nullity.getCode())) return;
        //Проверяем строку, соответствующую данной клетке
        for (int i = 0; i < field.getSize() - 1; i++) {
            //Если какой-то элемент данной строки не равен следующему, то проверка завершается
            if (!field.getCell(row, i).getCode().equals(field.getCell(row, i + 1).getCode())) break;
            //Если дошли до финальной клетки (все проверки прошли), то игра заканчивается
            if (i == field.getSize() - 2) {
                winner = field.getCell(row, col).getCode();
                isEnded = true;
                return;
            }
        }
        //Проверяем столбец, соответствующий данной клетке
        for (int i = 0; i < field.getSize() - 1; i++) {
            //Если какой-то элемент данного столбца не равен следующему, то проверка завершается
            if (!field.getCell(i, col).getCode().equals(field.getCell(i + 1, col).getCode())) break;
            //Все проверки прошли
            if (i == field.getSize() - 2) {
                winner = field.getCell(row, col).getCode();
                isEnded = true;
                return;
            }
        }
        //Если элемент расположен на левой диагонали (левая верхняя клетка лежит на ней)
        if (row == col){
            for (int i = 0; i < field.getSize() - 1; i++) {
                //Если какой-то элемент левой диагонали не равен следующему, то проверка завершается
                if (!field.getCell(i, i).getCode().equals(field.getCell(i + 1, i + 1).getCode())) break;
                if (i == field.getSize() - 2) {
                    winner = field.getCell(row, col).getCode();
                    isEnded = true;
                }
            }
        }
        //Если клетка лежит на другой диагонали
        if (row == field.getSize() - col - 1){
            for (int i = 0; i < field.getSize() - 1; i++) {
                //Если какой-то элемент другой диагонали не равен следующему, то проверка завершается
                if (!field.getCell(i, field.getSize() - i - 1).getCode().equals(field.getCell(i + 1, field.getSize() - i - 2).getCode())) break;
                if (i == field.getSize() - 2) {
                    winner = field.getCell(row, col).getCode();
                    isEnded = true;
                }
            }
        }
        //Если не осталось свободных мест
        if (counter == Math.pow(field.getSize(), 2)) {
            isEnded = true;
        }
    }


    //передаётся аргумент, означающий тип клетки и координаты клетки
    public String makeStep(int x, int y) {
        if (isEnded) return null;
        //Очередь сменяется
        CellType type = turn ? CellType.Blue : CellType.Red;
        if (field.setCell(type, x, y)) {
            counter++;
            turn = !turn;
            return type.getCode();
        }
        return null;
    }
}

class Field {
    //Размер игрового поля
    private final int size;
    private final CellType[][] field;

    /* Каждая ячейка поля имеет 3 значения:
     крестик, пустота, нолик
    */
    public int getSize() {
        return size;
    }

    public Field(int size) {
        this.size = size;
        field = new CellType[size][size];
        //Инициализация игрового поля
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = CellType.Nullity;
            }
        }
    }

    //Устанавливает в определённую клетку крестик, нолик или пустоту
    public boolean setCell(CellType cellType, int i, int j) {
        if (field[i][j] == CellType.Nullity) {
            field[i][j] = cellType;
            return true;
        } else {
            return false;
        }
    }

    public CellType getCell(int i, int j) {
        return field[i][j];
    }
}

enum CellType {
    Red("Red"), Blue("Blue"), Nullity("");
    private final String code;

    CellType(String code) {
        this.code = code;
    }

    //Получить символ клетки
    public String getCode() {
        return this.code;
    }
}