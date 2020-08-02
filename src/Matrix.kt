package processor

import kotlin.math.pow

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
