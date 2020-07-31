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
        3 -> multiplicationMatrices(scan)
        4 -> transposeMatrix(scan)
        0 -> exitProcess(1)
    }
}

fun printInfo() {
    print("1. Add matrices\n" +
            "2. Multiply matrix to a constant\n" +
            "3. Multiply matrices\n" +
            "4. Transpose matrix\n" +
            "0. Exit\n" +
            "Your choice: ")
}

fun transposeMatrix(scan: Scanner) {
    print("1. Main diagonal\n" +
            "2. Side diagonal\n" +
            "3. Vertical line\n" +
            "4. Horizontal line\n" +
            "Your choice: ")
    when (scan.nextLine().toInt()) {

    }
}

fun multiplicationMatrices(scan: Scanner) {
    val (rows1, cols1) = readMatrixSize(scan, "first")
    val mat1 = readMatrix(scan, rows1, cols1, "first")
    val (rows2, cols2) = readMatrixSize(scan, "second")
    val mat2 = readMatrix(scan, rows2, cols2, "second")
    if (mat1.cols != mat2.rows) {
        println("ERROR")
    } else {
        println("The result is: ")
        println(mat1 * mat2)
    }
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
    if (mat1.rows != mat2.rows || mat1.cols != mat2.cols) {
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
        val row = scan.nextLine().trim().split(" ").map(String::toDouble).toDoubleArray()
        result.fillLine(i, row)
    }
    return result
}


class Matrix(val rows: Int, val cols: Int) {
    private val content = Array(rows) { DoubleArray(cols) }

    fun fillLine(lineNumber: Int, data: DoubleArray) {
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
        val result = Matrix(rows, cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result[i][j] = this[i][j] + mat2[i][j]
            }
        }
        return result
    }

    operator fun times(n: Int): Matrix {
        val result = Matrix(rows, cols)
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                result[i][j] = this[i][j] * n
            }
        }
        return result
    }

    operator fun times(mat2: Matrix): Matrix {
        val result = Matrix(rows, mat2.cols)
        for (i in 0 until rows) {
            for (j in 0 until mat2.cols) {
                val rowMatrix1 = this[i]
                val colMatrix2 = DoubleArray(cols)
                for (r in 0 until mat2.rows) {
                    colMatrix2[r] = mat2[r][j]
                }
                var sum = 0.0
                for (l in rowMatrix1.indices) {
                    sum += rowMatrix1[l] * colMatrix2[l]
                }
                result[i][j] = sum
            }
        }

        return result
    }

    operator fun get(i: Int): DoubleArray {
        return content[i]
    }
}
