import mmcs.assignment2.*

fun main() {
    // transpose
    val matrix1 = createMatrix(2, 3, 1)
    matrix1[0, 0] = 1
    matrix1[0, 1] = 2
    matrix1[0, 2] = 3
    matrix1[1, 0] = 4
    matrix1[1, 1] = 5
    matrix1[1, 2] = 6

    println("Исходная матрица для transpose:")
    println(matrix1)

    val transposedMatrix = transpose(matrix1)
    println("Транспонированная матрица:")
    println(transposedMatrix)

    // Пример для rotate
    val matrix2 = createMatrix(3, 3, 0)
    matrix2[0, 0] = 1
    matrix2[0, 1] = 2
    matrix2[0, 2] = 3
    matrix2[1, 0] = 4
    matrix2[1, 1] = 5
    matrix2[1, 2] = 6
    matrix2[2, 0] = 7
    matrix2[2, 1] = 8
    matrix2[2, 2] = 9

    println("\nИсходная матрица для rotate:")
    println(matrix2)

    val rotatedMatrix = rotate(matrix2)
    println("Повернутая матрица:")
    println(rotatedMatrix)

    // plus
    val matrix3 = createMatrix(2, 2, 1)
    val matrix4 = createMatrix(2, 2, 2)

    println("\nМатрица 1 для сложения:")
    println(matrix3)
    println("Матрица 2 для сложения:")
    println(matrix4)

    val sumMatrix = matrix3 + matrix4
    println("Результат сложения:")
    println(sumMatrix)

    // unaryMinus
    val matrix5 = createMatrix(2, 2, 1)
    matrix5[0, 1] = -2
    matrix5[1, 0] = 3

    println("\nИсходная матрица для unaryMinus:")
    println(matrix5)

    val invertedMatrix = -matrix5
    println("Инвертированная матрица:")
    println(invertedMatrix)

    // times
    val matrix6 = createMatrix(2, 3, 0)
    matrix6[0, 0] = 1
    matrix6[0, 1] = 2
    matrix6[0, 2] = 3
    matrix6[1, 0] = 4
    matrix6[1, 1] = 5
    matrix6[1, 2] = 6

    val matrix7 = createMatrix(3, 2, 0)
    matrix7[0, 0] = 7
    matrix7[0, 1] = 8
    matrix7[1, 0] = 9
    matrix7[1, 1] = 10
    matrix7[2, 0] = 11
    matrix7[2, 1] = 12

    println("\nМатрица 1 для умножения:")
    println(matrix6)
    println("Матрица 2 для умножения:")
    println(matrix7)

    val productMatrix = matrix6 * matrix7
    println("Результат умножения:")
    println(productMatrix)

    // findHoles
    val matrix8 = createMatrix(3, 3, 1)
    matrix8[1, 0] = 0
    matrix8[1, 1] = 0
    matrix8[1, 2] = 0
    matrix8[2, 1] = 0

    println("\nИсходная матрица для findHoles:")
    println(matrix8)

    val holes = findHoles(matrix8)
    println("Дырчатые строки и столбцы: $holes")

    // canOpenLock
    val key = createMatrix(2, 2, 0)
    key[0, 0] = 1
    key[1, 1] = 1

    val lock = createMatrix(3, 3, 1)
    lock[0, 1] = 0
    lock[1, 0] = 0
    lock[1, 2] = 0
    lock[2, 1] = 0

    println("\nКлюч:")
    println(key)
    println("Замок:")
    println(lock)

    val result = canOpenLock(key, lock)
    println(result)
}