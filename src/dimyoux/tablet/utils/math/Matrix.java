package dimyoux.tablet.utils.math;

/**
 * Basic class to handle matrices of any size 
 */
public class Matrix implements Cloneable {

	// Matrix float values
	private float[][] values;
	// Number of rows
	private int nRows;
	// Number of columns
	private int nCols;
	
	/**
	 * Default matrix constructor
	 * @param nRows number of rows
	 * @param nCols number of columns
	 */
	public Matrix(int nRows, int nCols) 
	{
		this.nRows = nRows;
		this.nCols = nCols;
		
		values = new float[nRows][nCols];
	}
	
	/**
	 * Matrix constructor
	 * @param nRows number of rows
	 * @param nCols number of columns
	 * @param data data to assign to the matrix
	 */
	public Matrix(int nRows, int nCols, float[][] data)
	{
		this.nRows = nRows;
		this.nCols = nCols;
		
		values = new float[nRows][nCols];
		this.setData(data);
	}
	/**
	 * Matrix constructor
	 * @param data data to assign to the matrix
	 * The float[] array will fill a Row Matrix size(1, data.length)
	 */
	public Matrix(float[] data)
	{
		this.nRows = 1;
		this.nCols = data.length;
		
		values = new float[nRows][nCols];
		for(int i=0; i<nCols; i++)
		{
			values[0][i] = data[i];
		}
	}
	/**
	 * Matrix constructor
	 * @param data data to assign to the matrix
	 * @param isColumnMatrix If true the float[] array will fill a Column Matrix size(data.length, 1) otherwise size(1, data.length)
	 */
	public Matrix(float[] data, boolean isColumnMatrix)
	{
		if(isColumnMatrix)
		{
			this.nRows = data.length;
			this.nCols = 1;
			
			values = new float[nRows][nCols];
			for(int i=0; i<nRows; i++)
			{
				values[i][0] = data[i];
			}
		}else
		{
			this.nRows = 1;
			this.nCols = data.length;
			
			values = new float[nRows][nCols];
			for(int i=0; i<nCols; i++)
			{
				values[0][i] = data[i];
			}
		}
	}
	
	/**
	 * Returns the number of rows
	 * @return the number of rows
	 */
	public int getNumRows()
	{
		return nRows;
	}
	
	/**
	 * Returns the number of columns
	 * @return the number of columns
	 */
	public int getNumColumns()
	{
		return nCols;
	}
	/**
	 * Indicates if this matrix is a row matrix
	 * @return True or false
	 */
	public boolean isRowMatrix()
	{
		return nCols>1 && nRows==1;
	}
	/**
	 * Indicates if this matrix is a column matrix
	 * @return True or false
	 */
	public boolean isColumnMatrix()
	{
		return nRows>1 && nCols==1;
	}
	/**
	 * Sets the data of the current matrix
	 * @param data data used to replace the current data
	 * @return false if the matrix size doesn't match, true otherwise
	 */
	public boolean setData(float[][] data)
	{
		if (data.length == 0 || data.length != this.nRows || data[0].length != this.nCols)
		{
			return false;
		}
		
		for (int row = 0; row < nRows; row++)
		{
			for (int col = 0; col < nCols; col++)
			{
				values[row][col] = data[row][col];
			}
		}
		
		return true;
	}
	
	/**
	 * Returns the value at the specified row and column
	 * @param row the row of the element
	 * @param col the column of the element
	 * @return the value of the selected element
	 */
	public float get(int row, int col)
	{
		return values[row][col];
	}
	
	/**
	 * Sets the value at the specified row and column
	 * @param row the row
	 * @param col the column
	 * @param val the value to assign
	 * @return false if the row/col isn't in range, true otherwise
	 */
	public boolean set(int row, int col, float val)
	{
		if (row < 0 || row > this.getNumRows()
			|| col < 0 || col > this.getNumColumns())
		{
			return false;
		}
		
		values[row][col] = val;
		
		return true;
	}
	
	/**
	 * Clone of the current matrix
	 */
	public Object clone()
	{
		Matrix newMat  = null;
		try {
			newMat = (Matrix) super.clone();
		} catch (CloneNotSupportedException ex) {}
		
		float[][] data = (float[][]) values.clone();
		for (int row = 0; row < this.getNumRows(); row++)
		{
			data[row] = (float[]) values[row].clone();
		}
		
		newMat.values = data;
		
		return newMat;
	}
	
	/**
	 * Puts the result of the sum of the current matrix and the
	 * specified matrix into the current one
	 * @param m the matrix to add
	 * @return false if the matrix size doesn't match, true otherwise
	 */
	public boolean sum(Matrix m)
	{
		if (m.getNumRows() != this.getNumRows() || m.getNumColumns() != this.getNumColumns()) 
		{
			return false;
		}
		
		for (int i = 0; i < nRows; i++)
		{
			for (int j = 0; j < nCols; j++)
			{
				values[i][j] += m.get(i, j);
			}
		}
		
		return true;
	}
	
	/**
	 * Static function to sum 2 matrices
	 * @param m1 the first matrix
	 * @param m2 the second matrix
	 * @return the sum of the two matrices
	 */
	static public Matrix sum(Matrix m1, Matrix m2)
	{
		if (m1.getNumRows() != m2.getNumRows() || m1.getNumColumns() != m2.getNumColumns()) 
		{
			return null;
		}
		
		Matrix res = (Matrix) m1.clone();
		
		res.sum(m2);
		
		return res;
	}
	
	/**
	 * Multiplies the current matrix by a scalar
	 * @param scalar the scalar to multiply
	 */
	public void mutliply(float scalar)
	{
		for (int i = 0; i < nRows; i++)
		{
			for (int j = 0; j < nCols; j++)
			{
				values[i][j] *= scalar;
			}
		}
	}
	
	/**
	 * Static function to multiply a matrix by a scalar
	 * @param m the matrix
	 * @param scalar the scalar
	 * @return the result of the multiplication
	 */
	//static public Matrix multiply(Matrix m, float scalar)
	//{
		
	//}
	
	
	/**
	 * Multiply the current matrix by another one (and changes its size)
	 * @param m the other matrix to multiply the current one by
	 * @return false if M1.nRows != M2.nCols or M1.nCols != M2.nRows, true otherwise
	 */
	public boolean multiply(Matrix m)
	{
		if (this.getNumRows() != m.getNumColumns() || this.getNumColumns() != m.getNumRows())
		{
			return false;
		}
		
		float[][] data = new float[this.getNumRows()][m.getNumColumns()];
		
		for (int row = 0; row < this.getNumRows(); row++) {
            for (int col = 0; col < m.getNumColumns(); col++) {
                data[row][col] = 0.0f;
                for (int i = 0; i < m.getNumRows(); i++) {
                    data[row][col] += this.get(row, i) * m.get(i, col);
                }
            }
        }
		
		nRows = this.getNumRows();
		nCols = m.getNumColumns();
		
		this.setData(data);
		
		return true;
	}
	
	/**
	 * Static function to multiply a matrix by another
	 * @param m1 the first matrix
	 * @param m2 the second matrix
	 * @return a new result matrix (M1 * M2)
	 */
	static public Matrix multiply(Matrix m1, Matrix m2)
	{
		if (m1.getNumRows() != m2.getNumColumns() || m1.getNumColumns() != m2.getNumRows())
		{
			return null;
		}
		
		float[][] data = new float[m1.getNumRows()][m2.getNumColumns()];
		
		for (int row = 0; row < m1.getNumRows(); row++) {
            for (int col = 0; col < m2.getNumColumns(); col++) {
                data[row][col] = 0.0f;
                for (int i = 0; i < m2.getNumRows(); i++) {
                    data[row][col] += m1.get(row, i) * m2.get(i, col);
                }
            }
        }
		
		return new Matrix(m1.getNumRows(), m2.getNumColumns(), data);
	}
}
