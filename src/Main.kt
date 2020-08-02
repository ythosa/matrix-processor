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
