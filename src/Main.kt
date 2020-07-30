package processor

import java.util.*

fun main() {
    val scan = Scanner(System.`in`)
    multiplicationMatrixByNumber(scan)
}

fun multiplicationMatrixByNumber(scan: Scanner) {
    val mat = readMatrix(scan)
    val whatTimes = scan.nextInt()
    println(mat * whatTimes)
}

fun sumMatrices(scan: Scanner) {
    val mat1 = readMatrix(scan)
    val mat2 = readMatrix(scan)
    if (mat1.rows != mat2.rows || mat1.columns != mat2.columns) {
        println("ERROR")
    } else {
        println(mat1 + mat2)
    }
}

fun readMatrix(scan: Scanner): Matrix {
    val dim = scan.nextLine().trim().split(" ").map(String::toInt)
    val result = Matrix(dim[0], dim[1])
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
