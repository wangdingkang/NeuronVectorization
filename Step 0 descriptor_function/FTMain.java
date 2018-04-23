import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class FTMain {

	private static String OUTPUT_EXTENSION = ".des";

	private static boolean checkParameter(String inputFolder, String outputFolder, int controller){
		if(inputFolder.length() == 0) return false;
		if(outputFolder.length() == 0) return false;
		if(controller < 0) return false;
		return true;
	}

	public static String getOutputFilename(String outputFolder, int i, String inputFilename) {
		int pos = inputFilename.lastIndexOf(".");
		String outputFilename = pos > 0 ? inputFilename.substring(0, pos) : inputFilename;
		String res = outputFolder + (i + 1) + "_" + outputFilename + OUTPUT_EXTENSION;
		return res;
	}

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
//		if(args.length != 3){
//			System.out.println("usage: file_transfer <original swc file folder> <output file folder> <file transfer function type>");
//			System.out.println("<file transfer function type> is a number:");
//			System.out.println("0: Geodesic distance along neuron tree edges from tree nodes to root"); //geodesic distance
//			System.out.println("1: Euclidean distance along neuron tree edges from tree nodes to root"); //Geodesic+straight line
//			System.out.println("2: Weighted Geodesic distance along neuron tree edges from tree nodes to root"); //Volume weighted
//			System.out.println("3: Euclidean distance from tree nodes to root"); //  Euclidean distance function
//			System.out.println("4: Density count at each tree node from root to farthest tree node to root");
//			System.out.println("5: Use y-coordinate as descriptor function");
//			System.exit(0);
//		}
//		String inputFolder = args[0];
//		String outputFolder = args[1];
//		int controller = Integer.valueOf(args[2]);

		String inputFolder = "Input/workflow/";
		String outputFolder = "Output/workflow/";
		int controller = 1;
		
		if(!checkParameter(inputFolder, outputFolder, controller)){
			System.out.println("error with argument");
			System.exit(0);
		}
		
		if(outputFolder.lastIndexOf("/") != outputFolder.length() - 1){
			outputFolder += "/"; // outputFolder should end with '/'
		}
		
		final File folder = new File(inputFolder); //need to change folder for new data
		List<String> fileNames = new ArrayList<String>();
		FileFunctions.listFilesForFolder(folder,fileNames);
		FileTransfer fileTransfer;
		if(controller == 0){ // Geodesic distance along neuron tree edges from tree nodes to root
			System.out.println("Geodesic distance along neuron tree edges from tree nodes to root");
			for(int i=0;i<fileNames.size();i++){
				String inputFile = fileNames.get(i);
				File next = new File(inputFile);
				String outputFile = getOutputFilename(outputFolder, i, next.getName());
				fileTransfer = new GeodesicFileTransfer(inputFile);
				fileTransfer.ChangeFileFormatWithFuncVal(outputFile);
			}
		}else if(controller == 1){ // Euclidean distance along neuron tree edges from tree nodes to root
			System.out.println("Euclidean distance along neuron tree edges from tree nodes to root");
			for(int i=0;i<fileNames.size();i++){
				String inputFile = fileNames.get(i);
				File next = new File(inputFile);
				String outputFile = getOutputFilename(outputFolder, i, next.getName());
				fileTransfer = new GeodesicIgnoreEdgePointsFileTransfer(inputFile);
				//** Change from ChangeFileFormat to ChangeFileFormatWithFuncVal
				fileTransfer.ChangeFileFormatWithFuncVal(outputFile);
			}
		}else if(controller == 2){ // Weighted Geodesic distance along neuron tree edges from tree nodes to root
			System.out.println("Weighted Geodesic distance along neuron tree edges from tree nodes to root");
			for(int i=0;i<fileNames.size();i++){
				String inputFile = fileNames.get(i);
				File next = new File(inputFile);
				String outputFile = getOutputFilename(outputFolder, i, next.getName());
				fileTransfer = new WeightedGeodesicFileTransfer(inputFile);
				fileTransfer.ChangeFileFormat(outputFile);
			}
		}else if(controller == 3){ // Euclidean distance from tree nodes to root
			System.out.println("Euclidean distance from tree nodes to root");
			for(int i=0;i<fileNames.size();i++){
				String inputFile = fileNames.get(i);
				File next = new File(inputFile);
				String outputFile = getOutputFilename(outputFolder, i, next.getName());
				fileTransfer = new EuclideanFuncValFileTransfer(inputFile);
				fileTransfer.ChangeFileFormatWithFuncVal(outputFile);
			}
		}else if(controller == 4){ // Density count at each tree node from root to farthest tree node to root
			System.out.println("Density count at each tree node from root to farthest tree node to root");
			for(int i=0;i<fileNames.size();i++){
				String inputFile = fileNames.get(i);
				File next = new File(inputFile);
				String outputFile = getOutputFilename(outputFolder, i, next.getName());
				fileTransfer = new GeodesicFileTransfer(inputFile); // Any file transfer can do the density count job.
				fileTransfer.printDensityCount(outputFile);
			}
		}else if(controller == 5){
			System.out.println("5: Use y-coordinate as descriptor function");
			for(int i=0;i<fileNames.size();i++){
				String inputFile = fileNames.get(i);
				File next = new File(inputFile);
				String outputFile = getOutputFilename(outputFolder, i, next.getName());
				fileTransfer = new YCoordinateFuncValFileTransfer(inputFile); // Any file transfer can do the density count job.
				fileTransfer.ChangeFileFormatWithFuncVal(outputFile);
			}
		}else{
			System.out.println("<file transfer function type> should be in {0,1,2,3,4,5}");
			System.exit(0);
		}
	}
}
