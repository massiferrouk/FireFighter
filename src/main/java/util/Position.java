package util;

import java.util.Objects;

public class Position {
    private int row;
    private int column;

    public Position(int row, int column)
    {
        this.row=row;
        this.column=column;
    }

    public int row()
    {
        return row;
    }
    public int column()
    {
        return column;
    }


    public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Position position = (Position) obj;
            return row == position.row && column == position.column;
        }

    public String toString()
    {
        return row+" et "+ column+" ";

    }


}
