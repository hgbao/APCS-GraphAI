package com.trimaplebot.support;

public class MatrixSupport {
	/**
	 * Add a row and a column to a square matrix
	 * @param origin
	 * @return
	 */
	public static double[][] addElement(double[][] origin) {
		int n = origin.length + 1;
		double[][] result = new double[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (i < n - 1 && j < n - 1)
					result[i][j] = origin[i][j];
				else
					result[i][j] = 0.0;
			}
		return result;
	}

	/**
	 * Remove a row and a column in a square matrix
	 * @param origin
	 * @param remove
	 * @return
	 */
	public static double[][] removeElement(double[][] origin, int remove) {
		int n = origin.length - 1;
		double[][] result = new double[n][n];

		int p = 0;
		for (int i = 0; i < n; i++) {
			if (i == remove)
				continue;

			int q = 0;
			for (int j = 0; j < n; ++j) {
				if (j == remove)
					continue;

				result[p][q] = origin[i][j];
				q++;
			}
			p++;
		}
		return result;
	}
	
	/**
	 * Create a string of the matrix
	 * @param matrix
	 * @return
	 */
	public static String toString(double[][] matrix){
		String result = "";
		int n = matrix.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				result += matrix[i][j];
				if (j < n - 1)
					result += " ";
			}
			if (i < n - 1)
				result += "\n";
		}
		return result;
	}
	
	/**
	 * Create a double matrix from the string content
	 * @param content
	 * @param n
	 * @return
	 */
	public static double[][] toMatrix(String content, int n){
		double[][] result = new double[n][n];
		String[] lines = content.split("\n");
		
		for (int i = 0; i < n; i++) {
			String[] tmp = lines[i].split(" ");
			for (int j = 0; j < n; j++) {
				result[i][j] = Double.parseDouble(tmp[j]);
			}
		}
		
		return result;
	}
}
