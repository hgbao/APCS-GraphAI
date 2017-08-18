import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {
	
	private static ArrayList<String> nodeList = new ArrayList<String>();

	public static void main(String[] args) {
		String strPath = readFile("input50a.txt");
		String strHeuristic = readFile("input50b_long_beach.txt");
		String strGoal = "Long_Beach";

		String header = "trimaplebot-cs420final";
		String nodeList = toNodeList(strHeuristic);
		String pathList = toPathList(strPath);
		String heuristic = toHeuristic(strHeuristic, strGoal);
		
		String result = header + "\n" + nodeList + "\n" + pathList + "\n" + heuristic;
		writeFile("output.maptb", result);
	}

	private static String readFile(String fileName) {
		File file = new File(System.getProperty("user.dir") + "\\" + fileName);
		String content = "";
		if (file.isFile() && file.canRead()) {
			try {
				// Read the file
				BufferedReader br = new BufferedReader(new FileReader(file));
				StringBuilder sb = new StringBuilder();

				String line = br.readLine();
				while (line != null) {
					sb.append(line);
					line = br.readLine();
					if (line != null)
						sb.append("\n");
				}
				content = sb.toString();

				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}

	private static void writeFile(String fileName, String content) {
		// Write to file
		File file = new File(System.getProperty("user.dir") + "\\" + fileName);
		try {
			FileWriter writer = new FileWriter(file.getAbsolutePath());
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String toNodeList(String content){
		String result = "";
		String[] lines = content.split("\n");
		Random rand = new Random();
		
		//Number of nodes
		int n = lines.length;
		result = result + n + "\n";
		//Node list
		for (int i = 0; i < n; i++){
			String cityName = lines[i].split("\t")[0];
			int x = rand.nextInt(500);
			int y = rand.nextInt(600);
			result = result + cityName + "\n" + x + " " + y;
			if (i < n-1)
				result += "\n";

			nodeList.add(cityName);
		}
		
		return result;
	}
	
	private static String toPathList(String content){
		int n = nodeList.size();
		//initialize
		double[][] pathList = new double[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				pathList[i][j] = 0;
		//Set data
		String[] lines = content.split("\n");
		for (int i = 0; i < lines.length; i++){
			String[] str = lines[i].split("\t");
			String start = str[0];
			String end = str[1];
			double weight = Double.parseDouble(str[2]);
			
			int indexStart = nodeList.indexOf(start);
			int indexEnd = nodeList.indexOf(end);
			pathList[indexStart][indexEnd] = weight;
			pathList[indexEnd][indexStart] = weight;
		}
		//Convert data to string
		return matrixToString(pathList);
	}
	
	private static String toHeuristic(String content, String goal){
		int n = nodeList.size();
		int indexGoal = nodeList.indexOf(goal);
		//initialize
		double[][] heuristic = new double[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				heuristic[i][j] = 0;
		//Set data
		String[] lines = content.split("\n");
		for (int i = 0; i < lines.length; i++){
			String[] str = lines[i].split("\t");
			String city = str[0];
			double value = Double.parseDouble(str[1]);
			
			int index = nodeList.indexOf(city);
			heuristic[index][indexGoal] = value;
		}
		return matrixToString(heuristic);
	}

	private static String matrixToString(double[][] matrix){
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
}
