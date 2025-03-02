@file:Suppress("UNUSED_PARAMETER")
package mmcs.assignment2

import mmcs.assignment2.Matrix
import mmcs.assignment2.createMatrix

/**
 * Пример
 *
 * Транспонировать заданную матрицу matrix.
 */
fun <E> transpose(matrix: Matrix<E>): Matrix<E> {
    if (matrix.width < 1 || matrix.height < 1) return matrix
    val result = createMatrix(height = matrix.width, width = matrix.height, e = matrix[0, 0])
    for (i in 0 until matrix.width) {
        for (j in 0 until matrix.height) {
            result[i, j] = matrix[j, i]
        }
    }
    return result
}

fun <E> rotate(matrix: Matrix<E>): Matrix<E> {
    val result = createMatrix(height = matrix.width, width = matrix.height, e = matrix[0, 0])
    for (i in 0 until matrix.height) {
        for (j in 0 until matrix.width) {
            result[j, matrix.height - 1 - i] = matrix[i, j]
        }
    }
    return result
}

/**
 * Сложить две заданные матрицы друг с другом.
 * Складывать можно только матрицы совпадающего размера -- в противном случае бросить IllegalArgumentException.
 * При сложении попарно складываются соответствующие элементы матриц
 */
operator fun Matrix<Int>.plus(other: Matrix<Int>): Matrix<Int> {
    if (this.height != other.height || this.width != other.width) {
        throw IllegalArgumentException("Matrices must have the same dimensions")
    }
    val result = createMatrix(height = this.height, width = this.width, e = 0)
    for (i in 0 until this.height) {
        for (j in 0 until this.width) {
            result[i, j] = this[i, j] + other[i, j]
        }
    }
    return result
}

/**
 * Инвертировать заданную матрицу.
 * При инвертировании знак каждого элемента матрицы следует заменить на обратный
 */
operator fun Matrix<Int>.unaryMinus(): Matrix<Int> {
    val result = createMatrix(height = this.height, width = this.width, e = 0)
    for (i in 0 until this.height) {
        for (j in 0 until this.width) {
            result[i, j] = -this[i, j]
        }
    }
    return result
}

/**
 * Перемножить две заданные матрицы друг с другом.
 * Матрицы можно умножать, только если ширина первой матрицы совпадает с высотой второй матрицы.
 * В противном случае бросить IllegalArgumentException.
 * Подробно про порядок умножения см. статью Википедии "Умножение матриц".
 */
operator fun Matrix<Int>.times(other: Matrix<Int>): Matrix<Int> {
    if (this.width != other.height) {
        throw IllegalArgumentException("The number of columns in the first matrix must be equal to the number of rows in the second matrix")
    }
    val result = createMatrix(height = this.height, width = other.width, e = 0)
    for (i in 0 until this.height) {
        for (j in 0 until other.width) {
            for (k in 0 until this.width) {
                result[i, j] += this[i, k] * other[k, j]
            }
        }
    }
    return result
}

/**
 * Целочисленная матрица matrix состоит из "дырок" (на их месте стоит 0) и "кирпичей" (на их месте стоит 1).
 * Найти в этой матрице все ряды и колонки, целиком состоящие из "дырок".
 * Результат вернуть в виде Holes(rows = список дырчатых рядов, columns = список дырчатых колонок).
 * Ряды и колонки нумеруются с нуля. Любой из спискоов rows / columns может оказаться пустым.
 *
 * Пример для матрицы 5 х 4:
 * 1 0 1 0
 * 0 0 1 0
 * 1 0 0 0 ==> результат: Holes(rows = listOf(4), columns = listOf(1, 3)): 4-й ряд, 1-я и 3-я колонки
 * 0 0 1 0
 * 0 0 0 0
 */
fun findHoles(matrix: Matrix<Int>): Holes {
    val rows = mutableListOf<Int>()
    val columns = mutableListOf<Int>()

    for (i in 0 until matrix.height) {
        var isRowHole = true
        for (j in 0 until matrix.width) {
            if (matrix[i, j] != 0) {
                isRowHole = false
                break
            }
        }
        if (isRowHole) rows.add(i)
    }

    for (j in 0 until matrix.width) {
        var isColumnHole = true
        for (i in 0 until matrix.height) {
            if (matrix[i, j] != 0) {
                isColumnHole = false
                break
            }
        }
        if (isColumnHole) columns.add(j)
    }

    return Holes(rows, columns)
}

/**
 * Класс для описания местонахождения "дырок" в матрице
 */
data class Holes(val rows: List<Int>, val columns: List<Int>)

/**
 * Даны мозаичные изображения замочной скважины и ключа. Пройдет ли ключ в скважину?
 * То есть даны две матрицы key и lock, key.height <= lock.height, key.width <= lock.width, состоящие из нулей и единиц.
 *
 * Проверить, можно ли наложить матрицу key на матрицу lock (без поворота, разрешается только сдвиг) так,
 * чтобы каждой единице в матрице lock (штырь) соответствовал ноль в матрице key (прорезь),
 * а каждому нулю в матрице lock (дырка) соответствовала, наоборот, единица в матрице key (штырь).
 * Ключ при сдвиге не может выходить за пределы замка.
 *
 * Пример: ключ подойдёт, если его сдвинуть на 1 по ширине
 * lock    key
 * 1 0 1   1 0
 * 0 1 0   0 1
 * 1 1 1
 *
 * Вернуть тройку (Triple) -- (да/нет, требуемый сдвиг по высоте, требуемый сдвиг по ширине).
 * Если наложение невозможно, то первый элемент тройки "нет" и сдвиги могут быть любыми.
 */
fun canOpenLock(key: Matrix<Int>, lock: Matrix<Int>): Triple<Boolean, Int, Int> {
    for (shiftRow in 0..lock.height - key.height) {
        for (shiftCol in 0..lock.width - key.width) {
            var isMatch = true
            for (i in 0 until key.height) {
                for (j in 0 until key.width) {
                    if (lock[shiftRow + i, shiftCol + j] == key[i, j]) {
                        isMatch = false
                        break
                    }
                }
                if (!isMatch) break
            }
            if (isMatch) return Triple(true, shiftRow, shiftCol)
        }
    }
    return Triple(false, -1, -1)
}