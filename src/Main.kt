package processor

import java.util.Scanner

fun initMatrix(scanner: Scanner, rows: Int, cols: Int): Array<Array<Int>> {
    var matrix = arrayOf<Array<Int>>()
    for (i in 0 until rows) {
        var arr = arrayOf<Int>()
        for (j in 0 until cols) {
            arr += scanner.nextInt()
        }
        matrix += arr
    }
    return matrix
}

fun main() {
    val scanner = Scanner(System.`in`)

    val rowsMatrix1 = scanner.nextInt()
    val colsMatrix1 = scanner.nextInt()
    val matrix1 = initMatrix(scanner, rowsMatrix1, colsMatrix1)

    val rowsMatrix2 = scanner.nextInt()
    val colsMatrix2 = scanner.nextInt()
    val matrix2 = initMatrix(scanner, rowsMatrix2, colsMatrix2)

    if (rowsMatrix1 != rowsMatrix2 || colsMatrix1 != colsMatrix2) {
        println("ERROR")
        return
    }

    for (i in 0 until rowsMatrix1) {
        for (j in 0 until colsMatrix1) {
            print("${matrix1[i][j] + matrix2[i][j]} ")
        }
        println()
    }
}
