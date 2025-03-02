@file:Suppress("UNUSED_PARAMETER")
package mmcs.assignment2

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if (height <= 0 || width <= 0) {
        throw IllegalArgumentException("Negative params (height: $height, width: $width)")
    }
    return MatrixImpl(height, width, e)
}

/**
 * Реализация интерфейса "матрица"
 */

@Suppress("EqualsOrHashCode")
class MatrixImpl<E>(
    override val height: Int,
    override val width: Int,
    initialValue: E
) : Matrix<E> {

    private val elements = MutableList(height) { MutableList(width) { initialValue } }

    private fun checkBounds(row: Int, column: Int) {
        if (row !in 0 until height || column !in 0 until width) {
            throw IllegalArgumentException("Cell ($row, $column) is out of bounds (height: $height, width: $width)")
        }
    }

    override fun get(row: Int, column: Int): E {
        checkBounds(row, column)
        return elements[row][column]
    }

    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        checkBounds(row, column)
        elements[row][column] = value
    }

    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MatrixImpl<*>) return false
        if (height != other.height || width != other.width) return false
        for (i in 0 until height) {
            for (j in 0 until width) {
                if (elements[i][j] != other.elements[i][j]) return false
            }
        }
        return true
    }

    override fun toString(): String {
        return elements.joinToString("\n") { row ->
            row.joinToString(" ")
        }
    }
}
