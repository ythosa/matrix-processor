package processor

import java.util.*
import kotlin.math.pow
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
        5 -> calculationDeterminant(scan)
        6 -> inverseMatrix(scan)
        0 -> exit()
    }
}

fun exit() {
    println("Bye-bye :3")
    exitProcess(1)
}

fun printInfo() {
    print("1. Add matrices\n" +
            "2. Multiply matrix to a constant\n" +
            "3. Multiply matrices\n" +
            "4. Transpose matrix\n" +
            "5. Calculate a determinant\n" +
            "6. Inverse matrix\n" +
            "0. Exit\n" +
            "Your choice: ")
}

fun inverseMatrix(scan: Scanner) {
    val (rows, cols) = readMatrixSize(scan)
    val mat = readMatrix(scan, rows, cols)
    if (mat.determinant == 0.0) {
        println("ERROR")
        return
    }

    println("The result is: ")
    println(mat.inverse())
}

fun calculationDeterminant(scan: Scanner) {
    val (rows, cols) = readMatrixSize(scan)
    if (rows != cols) {
        println("ERROR")
        return
    }

    val mat = readMatrix(scan, rows, cols)
    println("The result is: ")
    println(mat.determinant)
}

fun transposeMatrix(scan: Scanner) {
    print("1. Main diagonal\n" +
            "2. Side diagonal\n" +
            "3. Vertical line\n" +
            "4. Horizontal line\n" +
            "Your choice: ")
    val transposeType = scan.nextLine().toInt()

    val (rows, cols) = readMatrixSize(scan)
    val mat = readMatrix(scan, rows, cols)

    println("The result is: ")
    when (transposeType) {
        1 -> println(mat.transposeByMainDiagonal())
        2 -> println(mat.transposeBySideDiagonal())
        3 -> println(mat.transposeByVerticalLine())
        4 -> println(mat.transposeByHorizontalLine())
    }
}

fun multiplicationMatrices(scan: Scanner) {
    val (rows1, cols1) = readMatrixSize(scan, "first")
    val mat1 = readMatrix(scan, rows1, cols1, "first")
    val (rows2, cols2) = readMatrixSize(scan, "second")
    val mat2 = readMatrix(scan, rows2, cols2, "second")

    if (mat1.cols != mat2.rows) {
        println("ERROR")
        return
    }

    println("The result is: ")
    println(mat1 * mat2)
}

fun multiplicationMatrixToConstant(scan: Scanner) {
    val (rows, cols) = readMatrixSize(scan)
    val mat = readMatrix(scan, rows, cols)

    print("Enter the constant: ")
    val whatTimes = scan.nextLine().toDouble()

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
        return
    }

    println("The result is: ")
    println(mat1 + mat2)
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

    val determinant: Double
        get() {
            return getDeterminant(this)
        }

    private fun getDeterminant(m: Matrix): Double {
        if (m.rows == 1) {
            return m[0][0]
        }
        if (m.rows == 2) {
            return m[0][0] * m[1][1] - m[1][0] * m[0][1]
        }

        var det = 0.0
        val n = m.rows
        val subMatrix = Matrix(n - 1, n - 1)
        for (x in 0 until n) {
            for ((subi, i) in (1 until n).withIndex()) {
                var subj = 0
                for (j in 0 until n) {
                    if (j == x) continue
                    subMatrix[subi][subj] = m[i][j]
                    subj++
                }
            }
            det += (-1.0).pow(x.toDouble()) * m[0][x] * getDeterminant(subMatrix)
        }

        return det
    }

    fun fillLine(lineNumber: Int, data: DoubleArray) {
        data.copyInto(content[lineNumber])
    }

    private fun getSubMatrix(x: Int, y: Int): Matrix {
        val size = this.rows
        val subsize = this.rows - 1
        val sub = Matrix(subsize, subsize)

        var subi = 0
        var subj = 0
        for (i in 0 until size) {
            if (i == x) continue
            for (j in 0 until size) {
                if (j == y) continue
                sub[subi][subj] = if (((i + 1) + (j + 1)) % 2 == 0) this[i][j] else -this[i][j]
                subj++
            }
            subj = 0
            if (subi + 1 == subsize) {
                subi = 0
            } else {
                subi++
            }
        }

        return sub
    }

    private fun cofactorMatrix(): Matrix {
        val size = this.rows
        val cofactorMatrix = Matrix(size, size)
        for (i in 0 until size) {
            for (j in 0 until size) {
                cofactorMatrix[i][j] = getDeterminant(getSubMatrix(i, j))
            }
        }
        return cofactorMatrix
    }

    fun inverse(): Matrix {
        println(cofactorMatrix())
        println(determinant)
        return cofactorMatrix().transposeByMainDiagonal() * (1 / determinant)
    }

    fun transposeByMainDiagonal(): Matrix {
        val result = Matrix(rows = cols, cols = rows)
        for (i in 0 until this.rows) {
            for (j in 0 until this.cols) {
                result[j][i] = this[i][j]
            }
        }
        return result
    }

    fun transposeBySideDiagonal(): Matrix {
        return this.transposeByMainDiagonal().transposeByVerticalLine().transposeByHorizontalLine()
    }

    fun transposeByVerticalLine(): Matrix {
        val result = Matrix(rows, cols)
        for (i in 0 until rows) {
            for (j in cols - 1 downTo 0) {
                result[i][cols - 1 - j] = this[i][j]
            }
        }
        return result
    }

    fun transposeByHorizontalLine(): Matrix {
        val result = Matrix(rows, cols)
        for (i in rows - 1 downTo 0) {
            for (j in 0 until cols) {
                result[rows - 1 - i][j] = this[i][j]
            }
        }
        return result
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

    operator fun times(n: Double): Matrix {
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
