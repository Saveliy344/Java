package com.example.tictactoe;

public class Game {
    //Счётчик ходов
    int counter;
    //Очередь крестика
    boolean turn = true;
    String winner = null;
    boolean isEnded = false;
    Field field;
    public Game(int size) {
        field = new Field(size);
    }
    public void checkEnd() {
        //Если не осталось свободных мест
        if (counter == Math.pow(field.getSize(), 2)) isEnded = true;
        if (isEnded) return;
        for (int i = 0; i < field.getSize(); i++){
            //Проверка горизонтали
            if (field.getCell(i, 0) != CellType.Nullity) {
                for (int j = 1; j < field.getSize(); j++) {
                    // Если текущая клетка горизонтали не равна предыдущей
                    if (!field.getCell(i, j).equals(field.getCell(i, j - 1))) {
                        break;
                    }
                    if (j == field.getSize() - 1){
                        winner = field.getCell(i, 0).getCode();
                        isEnded = true;
                        return;
                    }
                }
            }
            //Проверка по вертикали
            if (field.getCell(0, i) != CellType.Nullity) {
                for (int j = 1; j < field.getSize(); j++) {
                    // Если текущая клетка вертикали не равна предыдущей
                    if (!field.getCell(j, i).equals(field.getCell(j - 1, i))) {
                        break;
                    }
                    if (j == field.getSize() - 1){
                        winner = field.getCell(0, i).getCode();
                        isEnded = true;
                        return;
                    }
                }
            }
        }
    }


    //передаётся аргумент, означающий тип клетки и координаты клетки
    public String makeStep(int x, int y) {
        if (isEnded) return null;
        CellType type = turn ? CellType.Blue : CellType.Red;
        if (field.setCell(type, x, y)){
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
    public int getSize(){
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

    public CellType getCell(int i, int j){
        return field[i][j];
    }
}

enum CellType {
    Red("red"), Blue("blue"), Nullity("");
    private final String code;

    CellType(String code) {
        this.code = code;
    }

    //Получить символ клетки
    public String getCode() {
        return this.code;
    }
}