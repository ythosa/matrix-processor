package processor

import java.util.*
import kotlin.system.exitProcess

fun main() {
    val scan = Scanner(System.`in`)

    while (true) {
        printInfo()
        selectAction(scan)
    }
}

fun selectAction(scan: Scanner) {
    when (scan.nextLine().toInt()) {
        1 -> addMatrices(scan)
        2 -> multiplicationMatrixToConstant(scan)
        0 -> exitProcess(1)
    }
}

fun printInfo() {
    print("1. Add matrices\n" +
            "2. Multiply matrix to a constant\n" +
            "3. Multiply matrices\n" +
            "0. Exit\n" +
            "Your choice: ")
}

fun multiplicationMatrixToConstant(scan: Scanner) {
    val (rows, cols) = readMatrixSize(scan)
    val mat = readMatrix(scan, rows, cols)

    print("Enter the constant: ")
    val whatTimes = scan.nextLine().toInt()

    println("The result is:")
    println(mat * whatTimes)
}

fun addMatrices(scan: Scanner) {
    val (rows1, cols1) = readMatrixSize(scan, "first")
    val mat1 = readMatrix(scan, rows1, cols1, "first")
    val (rows2, cols2) = readMatrixSize(scan, "second")
    val mat2 = readMatrix(scan, rows2, cols2, "second")
    if (mat1.rows != mat2.rows || mat1.columns != mat2.columns) {
        println("ERROR")
    } else {
        println("The result is: ")
        println(mat1 + mat2)
    }
}

fun readMatrixSize(scan: Scanner, n: String = ""): Pair<Int, Int> {
    if (n.isNotEmpty()) {
        print("Enter the size of $n matrix: ")
    } else {
        print("Enter the size of the matrix: ")
    }
    val dim = scan.nextLine().trim().split(" ").map(String::toInt)
    return Pair(dim[0], dim[1])
}

fun readMatrix(scan: Scanner, rows: Int, cols: Int, n: String = ""): Matrix {
    if (n.isNotEmpty()) {
        println("Enter $n matrix: ")
    } else {
        println("Enter the matrix: ")
    }
    val result = Matrix(rows, cols)
    for (i in 0 until result.rows) {
        val row = scan.nextLine().trim().split(" ").map(String::toInt).toIntArray()
        result.fillLine(i, row)
    }
    return result
}


class Matrix(val rows: Int, val columns: Int) {
    private val content = Array(rows) { IntArray(columns) }

    fun fillLine(lineNumber: Int, data: IntArray) {
        data.copyInto(content[lineNumber])
    }

    override fun toString(): String {
        val result = StringBuilder()
        for (row in content) {
            result.appendln(row.joinToString(" "))
        }
        return result.toString()
    }

    operator fun plus(mat2: Matrix): Matrix {
        val result = Matrix(rows, columns)
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                result[i][j] = this[i][j] + mat2[i][j]
            }
        }
        return result
    }

    operator fun times(n: Int): Matrix {
        val result = Matrix(rows, columns)
        for (i in 0 until rows) {
            for (j in 0 until columns) {
                result[i][j] = this[i][j] * n
            }
        }
        return result
    }


    operator fun get(i: Int): IntArray {
        return content[i]
    }
}
